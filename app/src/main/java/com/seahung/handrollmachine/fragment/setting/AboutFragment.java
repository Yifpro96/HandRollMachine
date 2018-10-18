package com.seahung.handrollmachine.fragment.setting;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.constant.FileConstant;
import com.seahung.handrollmachine.constant.FragmentConstant;
import com.seahung.handrollmachine.global.GlobalSettingFragment;
import com.seahung.handrollmachine.helper.AsyncTaskHelper;
import com.seahung.handrollmachine.request.UpdateAppRequest;
import com.seahung.handrollmachine.response.UpdateAppResponse;
import com.seahung.handrollmachine.util.HttpUtils;
import com.seahung.handrollmachine.util.XmlHandlerUtils;
import com.seahung.handrollmachine.widget.CircleProgressView;
import com.unengchan.sdk.util.AppUtils;
import com.unengchan.sdk.util.DensityUtils;
import com.unengchan.sdk.util.StringUtils;
import com.unengchan.sdk.util.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by unengchan on 2018/6/5
 * 检查新版本
 */
public class AboutFragment extends GlobalSettingFragment implements HttpUtils.HttpCallbackListener {
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;

    private static final int NOT_UPDATE = 0;
    private static final int NEED_UPDATE = 1;
    private static final int INSTALL_APK = 2;
    private static final int DOWNLOAD_FAIL = 3;

    private TextView tvDialogTitle;
    private CircleProgressView circleProgressView;
    private TextView tvDialogContentDesc;
    private LinearLayout llDialogButton;
    private AlertDialog mAlertDialog;
    public Myhandler mHandler;

    @Override
    protected void initData() {
        super.initData();
        mHandler = new Myhandler();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvTitleName.setText(mContext.getString(R.string.setting_check_update));
        tvVersionName.setText(AppUtils.getVersionName());

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

        if (mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }

        // 升级下载框，隐藏按钮
        llDialogButton.setVisibility(View.GONE);
        tvDialogContentDesc.setVisibility(View.GONE);
        tvDialogTitle.setText(mContext.getString(R.string.launcher_download_app_new_version));
        circleProgressView.setProgress(0f);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.about_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_ABOUT_FRAGMENT;
    }

    @OnClick({R.id.iv_title_back, R.id.tv_check_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                mFragmentListener.onSwitchFragment(FragmentConstant.SETTING_SETTING_LIST_FRAGMENT);
                break;
            case R.id.tv_check_update:
                checkAppUpdate();
                break;
        }
    }

    @Override
    public void onHttpProgress(float progress) {
        circleProgressView.setProgress(progress);
    }

    @Override
    public void onHttpResult(boolean success) {
        if (success) {
            // 安装apk
            mHandler.sendEmptyMessage(INSTALL_APK);
        } else {
            // 下载失败
            File file = new File(FileConstant.APK_FILE_PATH);
            if (file.exists()) {
                file.delete();
            }
            mHandler.sendEmptyMessage(DOWNLOAD_FAIL);
        }
    }


    private class Myhandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NOT_UPDATE:
                    ToastUtils.show("当前已是最新版本");
                    break;
                case NEED_UPDATE:
                    showDownloadDialog();
                    break;
                case INSTALL_APK:
                    AppUtils.installApk(FileConstant.APK_FILE_PATH);
                    break;
                case DOWNLOAD_FAIL:
                    ToastUtils.show("下载失败，请重试");
                    break;
            }
        }
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
                    mHandler.sendEmptyMessage(INSTALL_APK);
                    return;
                }
                UpdateAppRequest request = new UpdateAppRequest();
                String xmlStr = AsyncTaskHelper.getXmlResponse(request);
                UpdateAppResponse response = XmlHandlerUtils.getUpdateAppResponse(xmlStr);
                if (response == null) {
                    // 不用升级app
                    mHandler.sendEmptyMessage(DOWNLOAD_FAIL);
                    return;
                }
                String upgrade = response.getUpgrade();
                if ("none".equals(upgrade)|| StringUtils.isEmpty(upgrade)) {
                    // 不用升级app
                    mHandler.sendEmptyMessage(NOT_UPDATE);
                    return;
                }
                // 修改文件权限，可以使用系统安装
                Runtime chg = Runtime.getRuntime();
                try {
                    chg.exec("chmod 777" + " " + FileConstant.APK_FILE_PATH).waitFor();
                } catch (Exception e) {

                }
                // 发送消息，显示弹窗
                mHandler.sendEmptyMessage(NEED_UPDATE);
                // 获取app的下载地址。并启动升级
                String appUrl = response.getApp_url();
                HttpUtils.doGet(appUrl, FileConstant.APK_FILE_PATH, AboutFragment.this);
            }
        });
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
}
