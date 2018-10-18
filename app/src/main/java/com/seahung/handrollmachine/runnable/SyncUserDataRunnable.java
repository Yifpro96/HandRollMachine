package com.seahung.handrollmachine.runnable;

import com.seahung.handrollmachine.bean.database.User;
import com.seahung.handrollmachine.constant.FileConstant;
import com.seahung.handrollmachine.helper.AsyncTaskHelper;
import com.seahung.handrollmachine.helper.CsvHelper;
import com.seahung.handrollmachine.helper.RealmHelper;
import com.seahung.handrollmachine.manager.ConfigManager;
import com.seahung.handrollmachine.request.SyncUserDataRequest;
import com.seahung.handrollmachine.response.SyncUserDataResponse;
import com.seahung.handrollmachine.util.HttpUtils;
import com.seahung.handrollmachine.util.XmlHandlerUtils;
import com.seahung.handrollmachine.widget.CircleProgressView;
import com.unengchan.sdk.util.LogUtils;
import com.unengchan.sdk.util.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by unengchan on 2018/7/17
 * 同步用户数据的线程
 */
public class SyncUserDataRunnable implements Runnable {

    private static final String ACTION_ADD = "ADD";
    private static final String ACTION_UPDATE = "UPDATE";
    private static final String ACTION_DELETE = "DELETE";
    private static final String ACTION_NONE = "NONE";    // 什么都不做
    // 取消同步
    private volatile boolean cancel;
    // 同步方式
    private volatile String syncType;
    // 进度条
    private CircleProgressView progressView;
    // 监听
    private SyncUserDataListener listener;

    public SyncUserDataRunnable() {
    }

    @Override
    public void run() {

        ConfigManager configManager = ConfigManager.getInstance();
        String schoolId = configManager.getSchoolId();
        String terminalId = configManager.getTerminalId();
        String userType = configManager.getUserType();
        SyncUserDataRequest request = new SyncUserDataRequest();
        request.setSchool_id(schoolId);
        request.setTerminal_id(terminalId);
        request.setUser_type(userType);

        if (schoolId.length() <= 4) {
            if (listener != null) {
                listener.onResult(-1, "请配置组织/机构id");
            }
            return;
        }
        if (StringUtils.isEmpty(terminalId)) {
            if (listener != null) {
                listener.onResult(-1, "请配置终端id");
            }
            return;
        }
        // 初始化进度为0
        if (progressView != null) {
            progressView.setProgress(0f);
        }
        String xmlResponse = AsyncTaskHelper.getXmlResponse(request);
        if (StringUtils.isEmpty(xmlResponse) || cancel) {
            //获取数据失败 或者 取消同步
            if (listener != null) {
                listener.onResult(-1, "无法连接服务器");
            }
            return;
        }
        SyncUserDataResponse response = XmlHandlerUtils.getSyncUserDataResponse(xmlResponse);
        String filePath = HttpUtils.doGet(response.getUser_csv_url(), FileConstant.USER_ROSTER_FILE_PATH);
        if (cancel || StringUtils.isEmpty(filePath)) {
            //获取数据失败 或者 取消同步
            if (listener != null) {
                listener.onResult(-1, "无法连接服务器，请检查id配置");
            }
            return;
        }
        // TODO: 2018/10/17 保存学生信息
        List<User> users = CsvHelper.getUserData(filePath);
        if (users == null || users.size() == 0) {
            // 后台没有录入用户信息
            if (listener != null) {
                listener.onResult(-1, "没有用户数据");
            }
            return;
        }

        // 数据库操作
        Realm realm = RealmHelper.getUserInfoRealm();
        try {
            // 用户数量
            int size = users.size();

            HashMap<String, User> usersMap = RealmHelper.getAllUsersHashMap(realm);
//            LogUtils.d(SyncUserDataRunnable.class.getSimpleName(), "------------最大的id-------" + faceIdMax);
            float currentProgress = 0f;
            for (int i = 0; i < size; i++) {
                if (cancel) {
                    // 主动取消下载
                    break;
                }
                User user = users.get(i);
                String accountUid = user.getAccountUid();
                String photoUrl = user.getPhotoUrl();
                String userPhotoPath = user.getPhotoPath();
                File photoFile = new File(userPhotoPath);

                // 判断数据是否有减少
                if (usersMap.containsKey(accountUid)) {
                    usersMap.remove(accountUid);
                }

                // 是否需要更新数据
                String faceAction = ACTION_NONE;
                // 本地数据
                User realmUser = RealmHelper.getUserByUid(realm, accountUid);
                if (realmUser == null) {
                    // 本地数据库没有这个人
                    if (StringUtils.isEmpty(photoUrl)) {
                        faceAction = ACTION_NONE;
                    } else {
                        faceAction = ACTION_ADD;
                    }
                } else {
                    String realmUserPhotoUrl = realmUser.getPhotoUrl();
                    if (StringUtils.isEmpty(photoUrl)) {
                        // 删除本地图片和人脸模板
                        if (photoFile.exists()) {
                            photoFile.delete();
                            faceAction = ACTION_DELETE;
                        } else {
                            // 最新的用户头像是空的
                            faceAction = ACTION_NONE;
                        }
                    } else {
                        if (photoUrl.equals(realmUserPhotoUrl)) {
                            // 如果数据库的信息跟最新的url一样
                            if (photoFile.exists()) {
                                faceAction = ACTION_NONE;
                            } else {
                                faceAction = ACTION_ADD;
                            }
                        } else {
                            // 删除本地图片
                            if (photoFile.exists()) {
                                photoFile.delete();
                                faceAction = ACTION_UPDATE;
                            } else {
                                faceAction = ACTION_ADD;
                            }
                        }
                    }
                }
                // 设置头像路径和人脸uid，不管数据是否有变动
                user.setPhotoPath(userPhotoPath);
//                // 数据有变动的情况下，重新下载头像
//                LogUtils.d(SyncUserDataThread.class.getSimpleName(), "--------是否更新数据-------" + refreshUserPhoto);
                if (faceAction.equals(ACTION_ADD) || faceAction.equals(ACTION_UPDATE)) {
                    // 下载头像
                    HttpUtils.doGet(photoUrl, userPhotoPath);
                    LogUtils.d(SyncUserDataRunnable.class.getSimpleName(), "-------注册人脸的用户------" + user.getName());
                }

                // 进度的计算，去掉相同进度的绘制，减少消耗
                if (progressView != null) {
                    float progress = (int) ((i + 1) * 1000f / size) / 10f;
                    if (currentProgress != progress) {
                        progressView.setProgress(progress);
                        currentProgress = progress;
                    }
                }
            }

            // 开启事务
            realm.beginTransaction();
            for (Map.Entry<String, User> entry : usersMap.entrySet()) {
                User user = RealmHelper.getUserByUid(realm, entry.getKey());
                if (user == null) {
                    continue;
                }
                File file = new File(user.getPhotoPath());
                // 删除头像文件和人脸信息
                if (file.exists()) {
                    file.delete();
                }
                // 删除用户
                user.deleteFromRealm();
            }
            // 插入数据库
            realm.insertOrUpdate(users);
            realm.commitTransaction();

//            if (!realm.isAutoRefresh()) {
//                // 如果数据库没有主动刷新
//                realm.refresh();
//            }
        } catch (Exception e) {
            LogUtils.d(SyncUserDataRunnable.class.getSimpleName(), e.getMessage());
        } finally {
            realm.close();
            if (listener != null) {
                listener.onResult(0, "同步完成或取消同步");
            }
        }
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    public void setProgressView(CircleProgressView progressView) {
        this.progressView = progressView;
    }

    public void setListener(SyncUserDataListener listener) {
        this.listener = listener;
    }

    /**
     * 同步数据的监听接口
     */
    public interface SyncUserDataListener {
        void onResult(int errorCode, String errorStr);
    }
}
