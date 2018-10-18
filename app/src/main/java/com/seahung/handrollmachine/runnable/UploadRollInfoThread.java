package com.seahung.handrollmachine.runnable;

import android.os.SystemClock;

import com.seahung.handrollmachine.bean.database.SwipeCard;
import com.seahung.handrollmachine.bean.database.User;
import com.seahung.handrollmachine.constant.FileConstant;
import com.seahung.handrollmachine.helper.RealmHelper;
import com.seahung.handrollmachine.manager.ConfigManager;
import com.seahung.handrollmachine.request.UploadRollInfoRequest;
import com.seahung.handrollmachine.response.BaseResponse;
import com.seahung.handrollmachine.response.UploadFileResponse;
import com.seahung.handrollmachine.response.UploadRollInfoResponse;
import com.seahung.handrollmachine.util.HttpUtils;
import com.seahung.handrollmachine.util.TimeUtils;
import com.seahung.handrollmachine.util.XmlHandlerUtils;
import com.unengchan.sdk.util.LogUtils;
import com.unengchan.sdk.util.StringUtils;

import java.io.File;

import io.realm.Realm;

/**
 * Created by unengchan on 2018/7/17
 * 上传用户考勤信息
 */
public class UploadRollInfoThread implements Runnable {

    /**
     * 上传考勤信息的接口
     */
    public interface UploadRollInfoListener {

        void onUploadSuccess(int notUploadCount);

        void onUploadFailure(int errorCode, String errorStr);
    }

    private volatile boolean stop = false;
    private volatile long nextDelayMillis = 3000;  // 默认是3秒钟
    private UploadRollInfoListener listener;
    //    private boolean uploadSuccess = true;
    private String errorStr = "";

    @Override
    public void run() {

        while (!stop) {
            Realm cardInfoRealm = RealmHelper.getSwipeCardInfoRealm();
            Realm userInfoRealm = RealmHelper.getUserInfoRealm();
            try {
//                LogUtils.d(UploadRollInfoThread.class.getSimpleName(), "-----------上传考勤信息的线程-------");
                if (listener != null && StringUtils.isNotEmpty(errorStr)) {
                    listener.onUploadFailure(-1, errorStr);
                }
                SystemClock.sleep(nextDelayMillis);
                SwipeCard swipeCard = RealmHelper.getNotUploadLastData(cardInfoRealm);
                if (swipeCard == null) {
                    // 不存在未上传的数据
                    errorStr = null;
                    nextDelayMillis = 3000;
                    continue;
                }


                String cardId = swipeCard.getCardId();
                User user = RealmHelper.getUserByCardId(userInfoRealm, cardId);
                if (user == null) {
                    user = new User();
                }
                LogUtils.d(UploadRollInfoThread.class.getSimpleName(), "---------------开始上传-------------"+cardId);
                ConfigManager configManager = ConfigManager.getInstance();
                // 上传请求
                UploadRollInfoRequest request = new UploadRollInfoRequest();
                // 设置里面配置的参数
                request.setKqTerminalId(configManager.getTerminalId());
                request.setSchoolId(configManager.getSchoolId());
                request.setTouchType(configManager.getInoutSchool());
                request.setUserType(configManager.getUserType());
                request.setPlatformId(configManager.getPlatformId());

                // 测试卡号
//                request.setCardId("2430911373");

                request.setCardId(swipeCard.getCardId());
//                request.setFaceUid(String.valueOf(faceUid));
                request.setTouchTime(swipeCard.getTouchTime());

                request.setUserAccountUid(user.getAccountUid());
                request.setUserDeptId(user.getDeptId());
                request.setUserRole(user.getRole());
                request.setUserPhotoUrl(user.getPhotoUrl());
                request.setUserCode(user.getCode());
                request.setUserDeptName(user.getDeptName());
                request.setUserName(user.getName());
                request.setLiveType(user.getLiveType());

                // gps定位
                request.setUpdownDirection(swipeCard.getUpdownDirection());
                request.setAddress(swipeCard.getAddress());
                request.setAltitude(swipeCard.getAltitude());
                request.setLongitude(swipeCard.getLongitude());
                request.setBearing(swipeCard.getBearing());
                request.setLatitude(swipeCard.getLatitude());
                request.setUpdownDirection(swipeCard.getUpdownDirection());
                request.setSpeed(swipeCard.getSpeed());

                // 上传时间
                request.setReportTime(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));

                // 开始上传数据
                String serviceUrl = HttpUtils.getServiceUrl(request.getTrans_code(), request.getAction());
                if (StringUtils.isEmpty(serviceUrl)) {
                    errorStr = "无法连接服务器";
                    continue;
                }
                String xmlStr = HttpUtils.doPost(serviceUrl, request.toXMLString());
                BaseResponse baseResponse = XmlHandlerUtils.getBaseResponse(xmlStr);
                if (baseResponse == null) {
                    errorStr = "无法连接服务器";
//                    errorStr = baseResponse == null ? "无法连接服务器" : baseResponse.getError_string();
                    continue;
                }
                errorStr = baseResponse.getError_string();

                // 重定向地址、下一次上传延迟时间
                String delayMillis = baseResponse.getDelayMillis();
                String redirectUrl = baseResponse.getRedirect_url();
                if (StringUtils.isEmpty(redirectUrl)) {
                    redirectUrl = serviceUrl;
                }
                // 上传图片
                int dot = redirectUrl.lastIndexOf(".");
                if (dot != -1) {
                    String photoPath = swipeCard.getPhotoPath();
                    if (StringUtils.isNotEmpty(photoPath) && new File(photoPath).exists()) {
                        String imgUrl = redirectUrl.substring(0, dot) + "_upload." + redirectUrl.substring(dot + 1);
                        File file = new File(photoPath);
                        String sXml = HttpUtils.doPost(imgUrl, file);
                        UploadFileResponse uploadFileResponse = XmlHandlerUtils.getUploadFileResponse(sXml);
                        if (uploadFileResponse == null) {
                            continue;
                        }
                        String uploadReceipt = uploadFileResponse.getUploadReceipt();
                        request.setUploadPhotoFile(photoPath);
                        request.setUploadPhotoReceipt(uploadReceipt);
                    }
                }
                // 发送上传通知
                xmlStr = HttpUtils.doPost(redirectUrl, request.toXMLString());
                UploadRollInfoResponse response = XmlHandlerUtils.getUploadRollInfoResponse(xmlStr);
                if (response == null) {
                    // 上传失败
                    errorStr = "无法连接服务器";
//                    errorStr = response == null ? "无法连接服务器" : response.getError_string();
                    continue;
                }
                errorStr = response.getError_string();

                // 上传成功
                // 删除拍照图片，修改刷卡数据的上传状态
                String photoPath = swipeCard.getPhotoPath();
                if (StringUtils.isNotEmpty(photoPath)) {
                    File file = new File(photoPath);
                    if (file.exists()) {
                        file.delete();
                    }
                }

                cardInfoRealm.beginTransaction();
                swipeCard.setIsUpload("Y");
                cardInfoRealm.commitTransaction();
                // 下一次的延时时间
                if (StringUtils.isNotEmpty(delayMillis)) {
                    nextDelayMillis = Long.parseLong(delayMillis) + 200;
                }
                // 获取没有上传的数量
                if (!cardInfoRealm.isAutoRefresh()) {
                    cardInfoRealm.refresh();
                }
                int notUploadCount = RealmHelper.getNotUploadCount(cardInfoRealm);
                if (listener != null) {
                    listener.onUploadSuccess(notUploadCount);
                }
                // 是否更新用户数据
                String needUpdateUserInfo = response.getNeedUpdateUserInfo();
                if ("N".equals(needUpdateUserInfo)) {
                    // 不需要更新用户数据
                    continue;
                }
                // 判断是否下载用户头像
                String userPhotoUrl = response.getUserPhotoUrl();
                if (StringUtils.isNotEmpty(userPhotoUrl) && !userPhotoUrl.equals(user.getPhotoUrl())) {
                    String userPhotoPath = FileConstant.USER_PHOTO_DIR + File.separator + response.getUserAccountUid() + ".jpg";
                    HttpUtils.doGet(userPhotoUrl, userPhotoPath);
                }
                // 更新用户数据
                RealmHelper.inserOrUpdateUser(userInfoRealm, response);
            } finally {
                cardInfoRealm.close();
                userInfoRealm.close();
            }
        }

    }

    /**
     * 停止上传
     */
    public void stop() {
        stop = true;
    }

    /**
     * 线程是否停止
     *
     * @return
     */
    public boolean isStop() {
        return stop;
    }

    public void setListener(UploadRollInfoListener listener) {
        this.listener = listener;
    }
}
