package com.seahung.handrollmachine.activity;

import android.os.Bundle;

import com.unengchan.sdk.base.BaseActivity;

/**
 * Created by unengchan on 2018/7/27
 * 打电话
 */
public class CallPhoneActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

//    @BindView(R.id.ll_remote_video)
//    LinearLayout llRemoteVideo;
//    @BindView(R.id.ll_local_video)
//    LinearLayout llLocalVideo;
//    @BindView(R.id.iv_hang_up)
//    ImageView ivHangUp;
//    @BindView(R.id.tv_marked_words)
//    TextView tvMarkedWords;
//    @BindView(R.id.cl_call_phone_activity)
//    ConstraintLayout clCallPhoneActivity;
//
//    private static final int CLOSE = 0;
//
//    public ConfigManager mConfigManager;
//    private String mSkinColors;
//    public MyUCVoipListener mUCVoipListener;
//    public long mConnectTimeSeconds;
//    public RefreshTimeRunnable mRefreshTimeRunnable;
//    public String mCallNumber;
////    public CallPhoneHandler mCallPhoneHandler;
//
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(newBase);
////        MultiDex.install(this);
//    }
//
//    @Override
//    protected int getLayoutId() {
//        UCDevice.initial(AppUtils.getAppContext(), new UCDevice.OnInitListener() {
//            @Override
//            public void onInitialized() {
//                try {
//                    // 呼叫鉴权
//                    LinphoneManager.getLc().authUseResponse26(true);
//                    UCDevice.getVoipManager().setIceEnabled(false);
//                    LinphoneManager.getLc().enableVideoStreamio(false);
//                } catch (UCError e) {
////                    printError("设置失败", e);
//                }
//            }
//
//            @Override
//            public void onError(Exception e) {
//
//            }
//        });
//        return R.layout.call_phone_activity;
//    }
//
//    @Override
//    protected void initData() {
//        super.initData();
//        mConfigManager = ConfigManager.getInstance();
////        mCallPhoneHandler = new CallPhoneHandler(this);
//        mRefreshTimeRunnable = new RefreshTimeRunnable();
//        mCallNumber = "6001";
//        if (mUCVoipListener == null) {
//            mUCVoipListener = new MyUCVoipListener();
//            int i = UCDevice.getVoipManager().addListener(mUCVoipListener);
//            LogUtils.d(CallPhoneActivity.class.getSimpleName(), "------添加监听器-----" + i);
//        }
//    }
//
//    @Override
//    protected void initView(Bundle savedInstanceState) {
//        tvMarkedWords.setText("正在拨号，请稍后...");
//
//        // 注册账号
//        boolean registered = UCDevice.getVoipManager().isRegistered();
//        if (!registered) {
//            String userName = "6002";
//            String password = "telpo.123";
//            String server = "183.234.48.161:7080";
//            String proxy = "";  // 代理地址
//            int register = UCDevice.getVoipManager().register(userName, password, server, proxy, null, null);
//            LogUtils.d(CallPhoneActivity.class.getSimpleName(), "------注册账号-----" + register);
//        }else {
//            int call = UCDevice.getVoipManager().call(mCallNumber, true);
//            LogUtils.d(CallPhoneActivity.class.getSimpleName(), "----------正在呼叫-----------"+call);
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // 初始化皮肤
//        String skinColors = mConfigManager.getSkinColors();
//        if (skinColors.equals(mSkinColors)) {
//            return;
//        }
//        mSkinColors = skinColors;
//        clCallPhoneActivity.setBackgroundColor(Color.parseColor(mSkinColors));
//    }
//
//    @OnClick(R.id.iv_hang_up)
//    public void onViewClicked() {
//        try {
//            //ll_input.setVisibility(View.VISIBLE);
//           UCDevice.getVoipManager().hangup();
//        } catch (UCError e) {
//
//        }
////        AppManager.getInstance().removeActivity(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mUCVoipListener != null) {
//            UCDevice.getVoipManager().removeListener(mUCVoipListener);
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mRefreshTimeRunnable != null) {
//            AppUtils.getHandler().removeCallbacks(mRefreshTimeRunnable);
//        }
//        if (mUCVoipListener != null) {
//            UCDevice.getVoipManager().removeListener(mUCVoipListener);
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        // 不能按下返回键
//    }
//
//    /**
//     * 刷新通话时间的runnable
//     */
//    private class RefreshTimeRunnable implements Runnable {
//        @Override
//        public void run() {
//            String time = TimeUtils.formatSecondsToTime(mConnectTimeSeconds);
//            if (tvMarkedWords != null) {
//                tvMarkedWords.setText(time);
//                mConnectTimeSeconds++;
//                AppUtils.getHandler().postDelayed(this, 1000);
//            }
//        }
//    }
//
//    /**
//     * 监听注册、来电、呼叫等状态
//     */
//    private class MyUCVoipListener extends UCVoipListener {
//        /**
//         * 账号注册监听
//         *
//         * @param linphoneCore
//         * @param linphoneProxyConfig
//         * @param registrationState
//         * @param s
//         */
//        @Override
//        public void registrationState(LinphoneCore linphoneCore, LinphoneProxyConfig linphoneProxyConfig, LinphoneCore.RegistrationState registrationState, String s) {
//            if (registrationState == LinphoneCore.RegistrationState.RegistrationProgress) {
////                textOutput.append("正在注册...");
//                LogUtils.d(CallPhoneActivity.class.getSimpleName(), "----------正在注册-----------");
//                return;
//            }
//            if (registrationState == LinphoneCore.RegistrationState.RegistrationOk &&
//                    UCDevice.getVoipManager().isRegistered()) {
////                Log.e(TAG, "账号注册成功:" + smessage);
////                textOutput.append("账号注册成功");
//                // 只有当账号注册成功以后才打电话
//                int call = UCDevice.getVoipManager().call(mCallNumber, true);
//                LogUtils.d(CallPhoneActivity.class.getSimpleName(), "----------账号注册成功-----------");
//                return;
//            }
//            if ((registrationState == LinphoneCore.RegistrationState.RegistrationFailed ||
//                    registrationState == LinphoneCore.RegistrationState.RegistrationCleared) &&
//                    !UCDevice.getVoipManager().isRegistered()) {
////                Log.e(TAG, "账号注册失败:" + smessage);
//                //textOutput.append("账号注册失败");
//                LogUtils.d(CallPhoneActivity.class.getSimpleName(), "----------账号注册失败-----------");
//                return;
//            }
//            if (registrationState == LinphoneCore.RegistrationState.RegistrationNone) {
////                Log.e(TAG, "账号未注册" + smessage);
////                textOutput.append("账号未注册");
//                LogUtils.d(CallPhoneActivity.class.getSimpleName(), "----------账号未注册-----------");
//                return;
//            }
//        }
//
//        /**
//         * 打电话的状态
//         *
//         * @param linphoneCore
//         * @param linphoneCall
//         * @param state
//         * @param s
//         */
//        @Override
//        public void callState(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneCall.State state, String s) {
//
//            if (state == LinphoneCall.State.IncomingReceived) {
//                // 有设备来电
////                mVoipManager.hangup();
//                return;
//            }
//            if (state == LinphoneCall.State.OutgoingInit) {
//                // 呼叫设备
////                tvMarkedWords.setText("正在拨号，请稍候...");
//                LogUtils.d(CallPhoneActivity.class.getSimpleName(), "----------呼叫设备-----------");
//                return;
//            }
//            if (state == LinphoneCall.State.StreamsRunning) {
//                // 设备已经连接
////                LinphoneCallParams remoteParams = linphoneCall.getRemoteParams();
////                if (remoteParams.getVideoEnabled() && !remoteParams.isLowBandwidthEnabled()) {
//                UCDevice.getVoipManager().startVideoEngine(llLocalVideo, llRemoteVideo, 3, 250);
////                }
//                // 开始通话时间
//                LogUtils.d(CallPhoneActivity.class.getSimpleName(), "----------设备已经连接-----------");
//                mConnectTimeSeconds = 0;
//                AppUtils.getHandler().post(mRefreshTimeRunnable);
//                return;
//            }
//
//            if (state == LinphoneCall.State.CallReleased || state == LinphoneCall.State.CallEnd) {
//                // 设备连接已经断开
//                try {
//                    UCDevice.getVoipManager().stopVideoEngine();
//                } catch (Exception e) {
//
//                }
//                AppUtils.getHandler().removeCallbacks(mRefreshTimeRunnable);
//                AppManager.getInstance().removeActivity(CallPhoneActivity.class);
////                mCallPhoneHandler.sendEmptyMessageDelayed(CLOSE,2000);
////                AppManager.getInstance().removeActivity(CallPhoneActivity.class);
//                return;
//            }
//
//            if (state == LinphoneCall.State.Error) {
//                // 出现错误
//                ToastUtils.show("拨号异常，请重试...");
//                AppUtils.getHandler().removeCallbacks(mRefreshTimeRunnable);
//                if (mUCVoipListener != null) {
//                    UCDevice.getVoipManager().removeListener(mUCVoipListener);
//                }
//                AppManager.getInstance().removeActivity(CallPhoneActivity.class);
//                return;
//            }
//
//
//        }
//    }
//
//
//    /**
//     * 打电话的流程
//     */
//    private static class CallPhoneHandler extends BaseHandler<CallPhoneActivity> {
//
//        public CallPhoneHandler(CallPhoneActivity activity) {
//            super(activity);
//        }
//
//        @Override
//        protected void onHandleMessage(CallPhoneActivity acivity, Message msg) {
//            switch (msg.what) {
//                case 0:
//                    AppManager.getInstance().removeActivity(CallPhoneActivity.class);
//                    break;
//            }
//        }
//    }

}

