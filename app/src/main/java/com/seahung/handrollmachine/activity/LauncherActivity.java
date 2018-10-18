package com.seahung.handrollmachine.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.constant.FileConstant;
import com.seahung.handrollmachine.global.GlobalApplication;
import com.seahung.handrollmachine.helper.AsyncTaskHelper;
import com.seahung.handrollmachine.request.UpdateAppRequest;
import com.seahung.handrollmachine.response.UpdateAppResponse;
import com.seahung.handrollmachine.util.HttpUtils;
import com.seahung.handrollmachine.util.XmlHandlerUtils;
import com.seahung.handrollmachine.widget.CircleProgressView;
import com.unengchan.sdk.base.BaseActivity;
import com.unengchan.sdk.base.BaseApplication;
import com.unengchan.sdk.base.BaseHandler;
import com.unengchan.sdk.manager.AppManager;
import com.unengchan.sdk.util.AppUtils;
import com.unengchan.sdk.util.DensityUtils;
import com.unengchan.sdk.util.NetworkUtils;
import com.unengchan.sdk.util.StringUtils;

import java.io.File;

import butterknife.BindView;

public class LauncherActivity extends BaseActivity implements NetworkUtils.NetworkCallbackListener, HttpUtils.HttpCallbackListener {

    public BaseApplication mApplication;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;

    private static final int LOAD_FACE = 0;
    private static final int CHECK_UPDATE = 1;
    private static final int NEED_UPDATE = 2;
    private static final int INSTALL_APK = 3;

    private TextView tvDialogTitle;
    private CircleProgressView circleProgressView;
    private TextView tvDialogContentDesc;
    private LinearLayout llDialogButton;
    private AlertDialog mAlertDialog;

    public int mLoadFaceLibTimes;
    public LauncherHandler mLauncherHandler;
    public boolean checkUpdateCompleted;

    @Override
    protected int getLayoutId() {
        if (!isTaskRoot()) {
            // 防止多次启动，多次退出的问题
            finish();
        }
        return R.layout.launcher_activity;
    }

    @Override
    protected void initData() {
        super.initData();

        // 安装成功后，删除安装包。
        File file = new File(FileConstant.APK_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }

//               // 将485切换到发送模式
//        PosUtil.setRs485Status(1);
//        // 启动传感器的service
//        startService(new Intent(this, ProximityService.class));

        mApplication = GlobalApplication.getInstance();
        mLauncherHandler = new LauncherHandler(this);
        mLoadFaceLibTimes = 0;
//        LogUtils.d(LauncherActivity.class.getSimpleName(), result);

        // 检查更新是否完成
        checkUpdateCompleted = false;

        checkAppUpdate();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 版本号
        tvVersionName.setText(AppUtils.getVersionName());

//        // 检测网络
////        NetworkUtils.isAvailable(this);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.circle_progress_altert_dialog, null);
        tvDialogTitle = ((TextView) dialogView.findViewById(R.id.tv_dialog_title));
        circleProgressView = ((CircleProgressView) dialogView.findViewById(R.id.circle_progress_view));
        tvDialogContentDesc = ((TextView) dialogView.findViewById(R.id.tv_dialog_content_desc));
        llDialogButton = ((LinearLayout) dialogView.findViewById(R.id.ll_dialog_button));

        // 创建dialog，有需要的的时候显示出来
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setNegativeButton("", null)
                .setPositiveButton("", null)
                .setCancelable(false)
                .setView(dialogView);
        mAlertDialog = builder.create();

//        if (mAlertDialog.isShowing()) {
//            mAlertDialog.dismiss();
//        }

        // 升级下载框，隐藏按钮
        llDialogButton.setVisibility(View.GONE);
        tvDialogContentDesc.setVisibility(View.GONE);
        tvDialogTitle.setText(mContext.getString(R.string.launcher_download_app_new_version));
        circleProgressView.setProgress(0f);
    }


    /**
     * 新版本下载的进度回调
     *
     * @param progress
     */
    @Override
    public void onHttpProgress(float progress) {
        // 采用postInvalidate更新，所以可以直接在线程里面设置数据
//        LogUtils.d(tag,"--------------下载apk的进度-------------"+progress);
        circleProgressView.setProgress(progress);
    }

    /**
     * 下载结果回调
     *
     * @param success
     */
    @Override
    public void onHttpResult(boolean success) {
        if (success) {
            // 安装apk
            mLauncherHandler.sendEmptyMessage(INSTALL_APK);
        } else {
            // 打开首页
            mLauncherHandler.sendEmptyMessage(CHECK_UPDATE);
        }
//        AppUtils.getHandler().post(new Runnable() {
//            @Override
//            public void run() {
//                mAlertDialog.dismiss();
//                if (success) {
////                    AppUtils.installApk(FileConstant.APK_FILE_PATH);
//                    startActivity(MainActivity.class);
//                } else {
//                    // 下载失败，打开首页
//                    startActivity(MainActivity.class);
//                }
//            }
//        });
    }

    @Override
    public void onNetworkResponse(boolean response) {
//        if (response) {
//            // 网络连接成功
//            mLauncherHandler.sendEmptyMessage(AppConstant.SUCCESS);
//        } else {
//            mLauncherHandler.sendEmptyMessageDelayed(AppConstant.FAILURE, 5000);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
        if (mLauncherHandler != null) {
            mLauncherHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 启动页的handler
     */
    private static class LauncherHandler extends BaseHandler<LauncherActivity> {
        public LauncherHandler(LauncherActivity activity) {
            super(activity);
        }

        @Override
        protected void onHandleMessage(LauncherActivity acivity, Message msg) {
            switch (msg.what) {
                case CHECK_UPDATE:
                    acivity.checkUpdateCompleted = true;
                    break;
                case NEED_UPDATE:
                    acivity.showDownloadDialog();
                    return;
                case INSTALL_APK:
                    AppUtils.installApk(FileConstant.APK_FILE_PATH);
                    return;
            }
            // 两个都完成以后，跳转到activity
            if (acivity.checkUpdateCompleted) {
                acivity.startActivity(MainActivity.class);
                AppManager.getInstance().removeActivity(acivity);
            }
        }
    }


    /**
     * 下载app
     */
    private void showDownloadDialog() {
        // 首先显示下载框
        // 设置宽高，必须先显示后在设置才生效
        mAlertDialog.show();
        mAlertDialog.getWindow().setLayout(DensityUtils.dp2px(280), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 检测升级,如果有新版本，启动asynctask
     */
    private void checkAppUpdate() {
        AppUtils.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 如果安装包已经存在，不用下载
                File file = new File(FileConstant.APK_FILE_PATH);
                if (file.exists()) {
                    mLauncherHandler.sendEmptyMessage(INSTALL_APK);
                    return;
                }
                UpdateAppRequest request = new UpdateAppRequest();
                String xmlStr = AsyncTaskHelper.getXmlResponse(request);
                UpdateAppResponse response = XmlHandlerUtils.getUpdateAppResponse(xmlStr);
                if (response == null) {
                    // 不用升级app
                    mLauncherHandler.sendEmptyMessage(CHECK_UPDATE);
                    return;
                }
                String upgrade = response.getUpgrade();
                if (StringUtils.isEmpty(upgrade) || "none".equals(upgrade)) {
                    // 不用升级app
                    mLauncherHandler.sendEmptyMessage(CHECK_UPDATE);
                    return;
                }
                Runtime chg = Runtime.getRuntime();
                try {
                    chg.exec("chmod 777" + " " + FileConstant.APK_FILE_PATH).waitFor();
                } catch (Exception e) {

                }
                // 发送消息，显示弹窗
                mLauncherHandler.sendEmptyMessage(NEED_UPDATE);
                // 获取app的下载地址。并启动升级
                String appUrl = response.getApp_url();
                HttpUtils.doGet(appUrl, FileConstant.APK_FILE_PATH, LauncherActivity.this);
            }
        });
    }
}
