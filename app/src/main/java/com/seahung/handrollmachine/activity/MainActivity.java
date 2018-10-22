package com.seahung.handrollmachine.activity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.flurgle.camerakit.CameraListener;
import com.flurgle.camerakit.CameraProperties;
import com.flurgle.camerakit.CameraView;
import com.flurgle.camerakit.Size;
import com.google.gson.Gson;
import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.bean.GpsInfo;
import com.seahung.handrollmachine.bean.SchoolBusRefreshData;
import com.seahung.handrollmachine.bean.SchoolbusData;
import com.seahung.handrollmachine.bean.database.SwipeCard;
import com.seahung.handrollmachine.bean.database.User;
import com.seahung.handrollmachine.constant.ConfigConstant;
import com.seahung.handrollmachine.constant.FileConstant;
import com.seahung.handrollmachine.helper.GlideHelper;
import com.seahung.handrollmachine.helper.RealmHelper;
import com.seahung.handrollmachine.helper.VoiceHelper;
import com.seahung.handrollmachine.manager.ConfigManager;
import com.seahung.handrollmachine.runnable.RefreshTimeRunnable;
import com.seahung.handrollmachine.runnable.UploadLocationRunnable;
import com.seahung.handrollmachine.runnable.UploadRollInfoThread;
import com.seahung.handrollmachine.service.QpushService;
import com.seahung.handrollmachine.util.BitmapUtils;
import com.seahung.handrollmachine.util.LocationUtils;
import com.seahung.handrollmachine.util.TimeUtils;
import com.seahung.handrollmachine.widget.LampProgressBar;
import com.unengchan.sdk.base.BaseActivity;
import com.unengchan.sdk.base.BaseHandler;
import com.unengchan.sdk.manager.AppManager;
import com.unengchan.sdk.util.AppUtils;
import com.unengchan.sdk.util.LogUtils;
import com.unengchan.sdk.util.SPUtils;
import com.unengchan.sdk.util.ToastUtils;
import com.unengchan.sdk.util.ViewUtils;

import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;

//import com.baidu.shield.authsdk.AuthSDK;
//import com.baidu.shield.facesdk.FaceSDKManager;
//import com.baidu.shield.facesdk.FaceStatus;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;

/**
 * Created by unengchan on 2018/7/12
 * 首页
 */
public class MainActivity extends BaseActivity
        implements UploadRollInfoThread.UploadRollInfoListener,
        AMapLocationListener, UploadLocationRunnable.UploadLocationListener {

    @BindView(R.id.tv_date_time)
    TextView tvDateTime;
    @BindView(R.id.camera_view)
    CameraView cameraView;
    @BindView(R.id.iv_user_face)
    ImageView ivUserFace;
    @BindView(R.id.tv_marked_words)
    TextView tvMarkedWords;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_swipe_face_info)
    TextView tvSwipeFaceInfo;
    @BindView(R.id.iv_success_words)
    ImageView ivSuccessWords;
    @BindView(R.id.tv_people_count)
    TextView tvPeopleCount;
    @BindView(R.id.tv_swipe_count)
    TextView tvSwipeCount;
    @BindView(R.id.lamp_progress_bar)
    LampProgressBar lampProgressBar;
    @BindView(R.id.cl_main_activity)
    ConstraintLayout clMainActivity;
    @BindView(R.id.iv_to_setting)
    ImageView ivToSetting;
    @BindView(R.id.tv_card_id)
    TextView tvCardId;
    @BindView(R.id.tv_class_name)
    TextView tvClassName;
    @BindView(R.id.tv_user_code)
    TextView tvUserCode;
    @BindView(R.id.rl_count)
    RelativeLayout rlCount;
    @BindView(R.id.iv_updown_car_bg)
    ImageView ivUpdownCarBg;
    @BindView(R.id.web_view)
    WebView webView;

    public static final int CAPTURE_IMAGE = 0;  // 拍照
    public static final int RESET_UI = 1;  // 重置UI
    public static final int ADD_SWIPE_DATA = 2;
    public static final int CLOSE_RELAY = 3;
    public static final int HIDE_UPDOWN_DIRECTION = 4;
    public static final int REFRESH_H5 = 5;//刷新H5

    public ExecutorService mThreadPool;
    public RefreshTimeRunnable mRefreshTimeRunnable;
    public ConfigManager mConfigManager;
    public String mSkinColors;

    public MainHandler mHandler;
    public int mPreviewTimes;

    public String mTouchTime;
    public String mFaceUid;
    public Realm mUserRealm;
    public Realm mSwipeCardInfoRealm;
    //    public String mCurrentUserPhotoPath;
    public int mSwipeCardCount;
    public int mPeopleCount;
    public String mCameraPhotoPath;
    public int mNotUploadCount;
    public UploadRollInfoThread mUploadRollInfoThread;
    public Intent mQpushServiceIntent;
    @BindView(R.id.tv_direction_up)
    TextView tvDirectionUp;
    @BindView(R.id.tv_direction_down)
    TextView tvDirectionDown;
    @BindView(R.id.rl_direction_updown)
    RelativeLayout rlDirectionUpdown;
    private long mBackPressedTime;

    public NfcAdapter mNfcAdapter;
    public PendingIntent mPendingIntent;
    public String mCardId;
    public List<GpsInfo> mGpsInfos;
    public double mLatitude;
    public double mLongitude;
    public double mAltitude;
    public float mSpeed;
    public float mBearing;
    public String mGpsTime;
    public String mAddress;
    public String mUpdownDirection = "up";
    public UploadLocationRunnable mUploadLocationRunnable;
    public String mPhotoMode;
    public boolean isAutoHide;
    public SchoolbusData mSchoolbusData;
    private Gson mGson;

    //    public boolean isSetting = true;
    private String TAG = "*********-" + MainActivity.class.getSimpleName() + "-************* ";


    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initData() {
        super.initData();

        Date date = new Date();
        Log.i(TAG, date.toString());
        mConfigManager = ConfigManager.getInstance();
        // 线程池
        mThreadPool = AppUtils.getThreadPool();
        mHandler = new MainHandler(this);

        mGson = new Gson();

        // 刷新时间
        mRefreshTimeRunnable = new RefreshTimeRunnable(tvDateTime);
        AppUtils.getHandler().post(mRefreshTimeRunnable);

        // 获取用户数据库
        mUserRealm = RealmHelper.getUserInfoRealm();
        // 获取刷卡/刷脸的数据库
        mSwipeCardInfoRealm = RealmHelper.getSwipeCardInfoRealm();

        // 启动qpush的服务
        mQpushServiceIntent = new Intent(this, QpushService.class);
        startService(mQpushServiceIntent);

        // gps定位数据
        initGpsInfo();
        // 初始化NFC读卡
        initNFC();


        // 加载网页
        initWebView();
    }

    /**
     * gps定位数据
     */
    private void initGpsInfo() {
        mGpsInfos = new ArrayList<>();
    }

    /**
     * 初始化NFC读卡
     */
    private void initNFC() {
        NfcManager mNfcManager = (NfcManager) getSystemService(Context.NFC_SERVICE);
        mNfcAdapter = mNfcManager.getDefaultAdapter();
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

  /*     findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData2H5();
            }
        });*/
        isAutoHide = true;
        rlDirectionUpdown.setVisibility(View.GONE);

        ivSuccessWords.setVisibility(View.INVISIBLE);
        tvCardId.setVisibility(View.INVISIBLE);
        tvUserName.setVisibility(View.INVISIBLE);
        tvClassName.setVisibility(View.INVISIBLE);
        tvUserCode.setVisibility(View.INVISIBLE);
        tvSwipeFaceInfo.setVisibility(View.INVISIBLE);
        ivUserFace.setImageResource(R.drawable.default_user_face);
        tvMarkedWords.setVisibility(View.VISIBLE);


        // 设置cameralistener
        cameraView.setCameraListener(mCameraListener);

        // 测量灯的宽高
        ViewUtils.measureWidthHeight(lampProgressBar, new ViewUtils.ViewCallbackListener() {
            @Override
            public void onMeasureResponse(int width, int height) {
                lampProgressBar.setWidthHeight(width, height);
                if (mNotUploadCount != 0) {
                    lampProgressBar.setSize(mNotUploadCount);
                }
            }
        });
    }

    /**
     * 初始化网页
     */

    private void initWebView() {

        webView.setVerticalScrollbarOverlay(true);
        WebSettings settings = webView.getSettings();

    /*    settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setVerticalScrollBarEnabled(false);*/
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

//        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "调用H5 function " + message);
                return true;
            }
        });
        webView.loadUrl("file:///android_asset/school_bus_attendance/index.html");


//        webView.loadUrl("http://192.168.0.21:8001/school_bus_attendance/index.html");


        //发送数据到H5

        sendData2H5();

    }

    /**
     * 发送数据到h5
     */
    private void sendData2H5() {

        Log.i(TAG, "sendData2H5");
        ArrayList<SchoolbusData.SeatData> seatDatas = new ArrayList<>();
        SchoolbusData schoolbusData = new SchoolbusData();


        generateSwipeCardData(schoolbusData, seatDatas);

        String param = mGson.toJson(schoolbusData);
        String loadUrl = generateMethodStr(param);
        Log.i(TAG, loadUrl);

        webView.loadUrl(loadUrl);
    }

    /**
     * 刷新h5页面数据
     */
    private void refreshH5Data() {

        ArrayList<SchoolbusData.SeatData> seatDatas = new ArrayList<>();

        SchoolbusData schoolbusData = new SchoolbusData();
        int preDownCount = 0;//即将下车人数
        int notOnTheBus = 0;//下车人数

//        ArrayList<SchoolBusRefreshData.AllSeatIdStatusBean> allSeatIdStatusBeans = new ArrayList<>();
//        SchoolBusRefreshData schoolBusRefreshData = new SchoolBusRefreshData();
//        schoolBusRefreshData.setCurrent_addrese(mAddress);
//        schoolBusRefreshData.setCurrent_latitude(mLatitude);
//        schoolBusRefreshData.setCurrent_longitude(mLongitude);

        List<User> users = RealmHelper.getAllUsersList(mUserRealm);

        for (User user : users) {

            SwipeCard swipeCard = RealmHelper.getSwipCardById(mUserRealm, user.getCardId());
            SchoolbusData.SeatData statusBean = new SchoolbusData.SeatData();


            //计算两个经纬度距离
            double upDownLatitude = Double.parseDouble(user.getUpdown_place_latitude());
            double upDownLongitude = Double.parseDouble(user.getUpdown_place_longitude());
            long second = 0;

            if (swipeCard != null) {
                second = TimeUtils.compare2Date(swipeCard.getTouchTime());

            }
            // 0-未上车 1-已上车  2-即将上下车 255-走道
            if (user.getSeat_number().equals("255")) {

                statusBean.setSeat_status("255");
            } else if (LocationUtils.calcDistance(mLatitude, mLongitude, upDownLatitude, upDownLongitude) < 1
                    && second > 4 * 60) {//如果当前经纬度和上下车经纬度小于1km且刷卡时间大于4分钟才设置为状态2

                preDownCount++;
                statusBean.setSeat_status("2");
            } else if (swipeCard != null && swipeCard.getUpdownDirection().equals("up")) {
                statusBean.setSeat_status("1");
            } else {
                notOnTheBus++;
                statusBean.setSeat_status("0");
            }

            statusBean.setSeat_number(user.getSeat_number());
            statusBean.setUser_name(user.getName());
            statusBean.setUser_photo_url(user.getPhotoUrl());
            statusBean.setUpdown_place_uid(user.getUpdown_place_uid());
            statusBean.setUpdown_place_address(user.getUpdown_place_address());
            statusBean.setUpdown_place_address_short(user.getUpdown_place_address_short());
            statusBean.setUpdown_place_latitude(user.getUpdown_place_latitude());
            statusBean.setUpdown_place_longitude(user.getUpdown_place_longitude());

            seatDatas.add(statusBean);
        }

        schoolbusData.setCurrent_addrese(mAddress);
        schoolbusData.setCurrent_latitude(mLatitude);
        schoolbusData.setCurrent_longitude(mLongitude);
        schoolbusData.setDownCount(preDownCount);
        //已上车人数
        schoolbusData.setUpCount(Integer.parseInt(ConfigManager.getInstance().getSchoolBusSeatCount())-notOnTheBus);

        schoolbusData.setAllSeatDistrStatus(seatDatas);

        String param = mGson.toJson(schoolbusData);

//        Log.i(TAG, param);

        webView.loadUrl(generateMethodStr(param));

    }

    private void generateSwipeCardData(SchoolbusData schoolbusData, ArrayList<SchoolbusData.SeatData> seatDatas) {
        String seatCount = ConfigManager.getInstance().getSchoolBusSeatCount();
        String seatRow = ConfigManager.getInstance().getSchoolBusSeatRow();
        String seatCloumn = ConfigManager.getInstance().getSchoolBusSeatColumn();

        schoolbusData.setSeat_count(seatCount);
        schoolbusData.setSeat_row(seatRow);
        schoolbusData.setSeat_column(seatCloumn);

        schoolbusData.setDownCount(0);
        schoolbusData.setUpCount(0);
        schoolbusData.setCurrent_addrese(mAddress);
        schoolbusData.setCurrent_latitude(mLatitude);
        schoolbusData.setCurrent_longitude(mLongitude);

        Log.i("TAG", seatCount + " - " + seatRow + " - " + seatCloumn);

        int swipCardUpCount = RealmHelper.getSwipCardUpCount(mSwipeCardInfoRealm);
        schoolbusData.setOn_car_stu_count(swipCardUpCount);


        List<User> users = RealmHelper.getAllUsersList(mUserRealm);

        for (User user : users) {

            SchoolbusData.SeatData seatData = new SchoolbusData.SeatData();

            seatData.setSeat_number(user.getSeat_number());


            SwipeCard swipeCard = RealmHelper.getSwipCardById(mSwipeCardInfoRealm, user.getCardId());

            if (swipeCard != null && swipeCard.getUpdownDirection().equals("up")) {
                seatData.setSeat_status("0");
            } else {
                seatData.setSeat_status("1");
            }

            seatData.setUser_name(user.getName());
            seatData.setUser_photo_url(user.getPhotoUrl());
            seatData.setUpdown_place_uid(user.getUpdown_place_uid());
            seatData.setUpdown_place_address(user.getUpdown_place_address());
            seatData.setUpdown_place_address_short(user.getUpdown_place_address_short());
            seatData.setUpdown_place_latitude(user.getUpdown_place_latitude());
            seatData.setUpdown_place_longitude(user.getUpdown_place_longitude());

            seatDatas.add(seatData);

            schoolbusData.setAllSeatDistrStatus(seatDatas);
        }
    }

    /**
     * 生成h5调用url
     *
     * @param param
     * @return
     */
    private String generateMethodStr(String param) {
        return String.format("javascript:H5PageDataIniShow(\'%s\')", param);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d(MainActivity.class.getSimpleName(), "------onStart---------");
        // 启动考勤数据上传
        mUploadRollInfoThread = new UploadRollInfoThread();
        mUploadRollInfoThread.setListener(this);
        mThreadPool.execute(mUploadRollInfoThread);

        // 启动上传定位
        mUploadLocationRunnable = new UploadLocationRunnable(this);
        mUploadLocationRunnable.setGpsInfos(mGpsInfos);
        mThreadPool.execute(mUploadLocationRunnable);

        // 启动摄像机
        mPhotoMode = ConfigManager.getInstance().getPhotoMode();
        if (mPhotoMode.equals(ConfigConstant.PHOTO_MODE_WITHOUT_PHOTO)) {
            ivUpdownCarBg.setVisibility(View.VISIBLE);
        } else {
            ivUpdownCarBg.setVisibility(View.GONE);
            try {
                cameraView.start();
            } catch (Exception e) {
                ToastUtils.show(e.getMessage());
            }
        }

        // 刷卡总数量
        mSwipeCardCount = RealmHelper.getSwipeCardSum(mSwipeCardInfoRealm);
        tvSwipeCount.setText(mSwipeCardCount + " 次");

        // 刷卡人数
        mPeopleCount = RealmHelper.getPeopleCountByCardId(mSwipeCardInfoRealm);
        tvPeopleCount.setText(mPeopleCount + " 人");
        // 未上传的人数
        mNotUploadCount = RealmHelper.getNotUploadCount(mSwipeCardInfoRealm);
        lampProgressBar.setSize(mNotUploadCount + mGpsInfos.size());

        // 初始化皮肤
        String skinColors = mConfigManager.getSkinColors();
        if (!skinColors.equals(mSkinColors)) {
            mSkinColors = skinColors;
            clMainActivity.setBackgroundColor(Color.parseColor(mSkinColors));
        }

        // 定位设置
        int locationInterval = mConfigManager.getLocationInterval();
        LocationUtils.setLocationInterval(locationInterval);
        String gpsLocation = mConfigManager.getGpsLocation();
        if (ConfigConstant.GPS_LOCATION_OPEN.equals(gpsLocation)) {
            LocationUtils.initLocationClient();
            LocationUtils.startLocation(this);
        } else {
            LocationUtils.stopLocation();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // nfc读卡
        if (mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(this.getIntent().getAction())) {
                processIntent(this.getIntent());
            }
        }
        LogUtils.d(MainActivity.class.getSimpleName(), "------onResume---------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 停止NFC
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
        LogUtils.d(MainActivity.class.getSimpleName(), "------onpause---------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 停止定位
        LocationUtils.stopLocation();
        // 只有拍照的时候才需要停止摄像头
        if (!mPhotoMode.equals(ConfigConstant.PHOTO_MODE_WITHOUT_PHOTO)) {
            if (cameraView.isStarted()) {
                cameraView.stop();
            }
        }
        LogUtils.d(MainActivity.class.getSimpleName(), "------onstop---------");
        // 停止数据上传
        if (mUploadRollInfoThread != null) {
            mUploadRollInfoThread.stop();
            mUploadRollInfoThread.setListener(null);
            mUploadRollInfoThread = null;
        }

        if (mUploadLocationRunnable != null) {
            mUploadLocationRunnable.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭数据库
        if (mUserRealm != null && !mUserRealm.isClosed()) {
            mUserRealm.close();
        }
        if (mSwipeCardInfoRealm != null && !mSwipeCardInfoRealm.isClosed()) {
            mSwipeCardInfoRealm.close();
        }
        // 停止qpush的服务
        if (mQpushServiceIntent != null) {
            stopService(mQpushServiceIntent);
        }
        // 退出应用时，移除所有消息
        AppUtils.getHandler().removeCallbacksAndMessages(null);
        AppManager.getInstance().removeAllActivity();

        LogUtils.d(MainActivity.class.getSimpleName(), "------onDestroy---------");
    }

    @Override
    public void onBackPressed() {
        // 双击退出
//        AppUtils.exit();
//        long nowTime = System.currentTimeMillis();
//        if (nowTime - mBackPressedTime > 2500) {
//            mBackPressedTime = nowTime;
//            ToastUtils.show(com.unengchan.sdk.R.string.app_exit);
//            return;
//        }
    }

    public int mPreviewWidth;
    public int mPreviewHeight;
    public int mDisplayOrientation;
    public float mScaleWidth, mScaleHeight;

    /**
     * camera 的回调
     */
    private CameraListener mCameraListener = new CameraListener() {
        @Override
        public void onCameraOpened() {
            super.onCameraOpened();
            Size previewSize = cameraView.getPreviewSize();
            mPreviewWidth = previewSize.getWidth();
            mPreviewHeight = previewSize.getHeight();
            CameraProperties cameraProperties = cameraView.getCameraProperties();
            if (cameraProperties != null) {
                mDisplayOrientation = cameraProperties.getDisplayOrientation();
            }
            ViewUtils.measureWidthHeight(cameraView, new ViewUtils.ViewCallbackListener() {
                @Override
                public void onMeasureResponse(int width, int height) {
                    // 计算缩放比
                    mScaleWidth = width * 1.0f / mPreviewWidth;
                    mScaleHeight = height * 1.0f / mPreviewHeight;
                }
            });
        }

        @Override
        public void onCameraClosed() {
            super.onCameraClosed();
        }

        @Override
        public void onPreview(byte[] data) {
        }

        @Override
        public void onPictureTaken(byte[] jpeg) {
            if (jpeg == null) {
//                mCameraPhotoPath = mCurrentUserPhotoPath;
            } else {
//                mCameraPhotoPath = FileConstant.USER_CAMERA_PICTURE + File.separator + mFaceUid + "_" + TimeUtils.getCurrentTime("yyyyMMddHHmmss") + ".jpg";
                mCameraPhotoPath = FileConstant.USER_CAMERA_PICTURE + File.separator + mCardId + "_" + TimeUtils.getCurrentTime("yyyyMMddHHmmss") + ".jpg";
                Bitmap bitmap = BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length);
                boolean success = BitmapUtils.saveBitmap(mCameraPhotoPath, bitmap);
//                if (!success) {
//                    mCameraPhotoPath = mCurrentUserPhotoPath;
//                }
            }
            LogUtils.d(MainActivity.class.getSimpleName(), "========拍照回调=======" + mCameraPhotoPath);
//            mHandler.sendEmptyMessage(ADD_SWIPE_DATA);
        }
    };

//    /**
//     * 绘制人脸框
//     *
//     * @param fasePos
//     */
//    @Override
//    public void onFacePosition(int[] fasePos) {
//        faceRectView.setFaceRectView(fasePos, mScaleWidth, mScaleHeight, 1);
//    }
//
//    /**
//     * 识别结果回调
//     *
//     * @param faceId
//     */
//    @Override
//    public void onFaceUid(int faceId) {
//        // 时间和id
//        mTouchTime = TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
//        mFaceUid = faceId;
//        mHandler.sendEmptyMessage(CAPTURE_IMAGE);
//    }
//
//    /**
//     * 是否能够打电话到前台
//     */
//    @Override
//    public void onCallPhone(boolean canCallPhone) {
////        LogUtils.d(MainActivity.class.getSimpleName(), "--------cancallphone------" + canCallPhone);
//        if (mCanCallPhone == canCallPhone) {
//            return;
//        }
//        mCanCallPhone = canCallPhone;
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mCanCallPhone) {
//                    tvMarkedWords.setText("请联系前台");
//                    ivUserFace.setVisibility(View.INVISIBLE);
//                    ivCallPhone.setVisibility(View.VISIBLE);
//                } else {
//                    tvMarkedWords.setText("— 请刷脸 —");
//                    ivUserFace.setVisibility(View.VISIBLE);
//                    ivCallPhone.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
//    }


    @OnClick({R.id.iv_to_setting, R.id.tv_marked_words, R.id.tv_direction_up, R.id.tv_direction_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_to_setting:
                if (rlDirectionUpdown.getVisibility() != View.VISIBLE) {
                    startActivity(PasswordActivity.class);
                } else {
                    ToastUtils.show("请选择上下车");
                }
                break;
            case R.id.tv_direction_up:
                isAutoHide = false;
                mHandler.removeMessages(HIDE_UPDOWN_DIRECTION);
                rlDirectionUpdown.setVisibility(View.GONE);
                mUpdownDirection = "up";
                mHandler.sendEmptyMessage(ADD_SWIPE_DATA);
                break;
            case R.id.tv_direction_down:
                isAutoHide = false;
                mHandler.removeMessages(HIDE_UPDOWN_DIRECTION);
                rlDirectionUpdown.setVisibility(View.GONE);
                mUpdownDirection = "down";
                mHandler.sendEmptyMessage(ADD_SWIPE_DATA);
                break;
        }
    }

    /**
     * 上传考勤信息成功
     *
     * @param notUploadCount
     */
    @Override
    public void onUploadSuccess(int notUploadCount) {
        mNotUploadCount = notUploadCount;
        // 可以直接在线程更新
        lampProgressBar.setSize(mNotUploadCount + mGpsInfos.size());
    }

    /**
     * 上传考勤信息失败
     *
     * @param errorCode
     * @param errorStr
     */
    @Override
    public void onUploadFailure(int errorCode, final String errorStr) {
        if (errorCode != 0) {
            // 上传失败
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show(errorStr);
                }
            });
        }
//        LogUtils.d(MainActivity.class.getSimpleName(), "------上传考勤信息-------" + errorStr);
    }

    /**
     * NFC读卡
     *
     * @param intent
     */
    public void processIntent(Intent intent) {

        // 选择上下车时，有人刷卡了
        if (rlDirectionUpdown.getVisibility() != View.GONE && !isAutoHide) {
            mHandler.sendEmptyMessage(ADD_SWIPE_DATA);
        }

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            return;
        }
        byte[] id = tag.getId();
        StringBuilder stringBuilder = new StringBuilder();
        char[] buffer = new char[2];
        for (int i = id.length - 1; i >= 0; i--) {
            buffer[0] = Character.forDigit((id[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(id[i] & 0x0F, 16);
            stringBuilder.append(buffer);
        }
        // 卡号和刷卡时间
        mTouchTime = TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
        //1 15   045
        mCardId = String.valueOf(Long.parseLong(stringBuilder.toString(), 16));

        mHandler.removeMessages(RESET_UI);
        mHandler.sendEmptyMessage(CAPTURE_IMAGE);


        // 5秒后消失
        isAutoHide = false;
        rlDirectionUpdown.setVisibility(View.VISIBLE);
        mHandler.removeMessages(HIDE_UPDOWN_DIRECTION);
        mHandler.sendEmptyMessageDelayed(HIDE_UPDOWN_DIRECTION, 5000);
        LogUtils.d(this.tag, "---------卡号========" + mCardId);
    }

    /**
     * 定位位置
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation == null) {
            return;
        }
        mLatitude = aMapLocation.getLatitude();
        mLongitude = aMapLocation.getLongitude();
        mAltitude = aMapLocation.getAltitude();
        mSpeed = aMapLocation.getSpeed();
        mBearing = aMapLocation.getBearing();
        mGpsTime = TimeUtils.formatMillisToDateTime(aMapLocation.getTime());
        mAddress = aMapLocation.getAddress();

        GpsInfo gpsInfo = new GpsInfo();
        gpsInfo.setLatitude(mLatitude);
        gpsInfo.setLongitude(mLongitude);
        gpsInfo.setAltitude(mAltitude);
        gpsInfo.setSpeed(mSpeed);
        gpsInfo.setDirection(mBearing);
        gpsInfo.setGpsTime(mGpsTime);
        gpsInfo.setAddress(mAddress);
        mGpsInfos.add(gpsInfo);

        if (mUploadLocationRunnable != null) {
            mUploadLocationRunnable.setGpsInfos(mGpsInfos);
        }
//        if (lampProgressBar != null) {
        lampProgressBar.setSize(mNotUploadCount + mGpsInfos.size());
//        }
//        LogUtils.d(this.tag, "---定位数据----" + aMapLocation.toJson(1));
        mHandler.sendEmptyMessage(REFRESH_H5);
    }

    /**
     * 位置上报结果
     *
     * @param errorCode
     * @param errorString
     */
    @Override
    public void onUploadResult(int errorCode, String errorString) {
        if (errorCode != 0) {

        } else {
            if (lampProgressBar != null) {
                lampProgressBar.setSize(mNotUploadCount + mGpsInfos.size());
            }
        }
    }


    /**
     * main handler
     */
    private static class MainHandler extends BaseHandler<MainActivity> {

        public MainHandler(MainActivity activity) {
            super(activity);
        }

        @Override
        protected void onHandleMessage(MainActivity acivity, Message msg) {
            switch (msg.what) {
                case CAPTURE_IMAGE:
                    // 拍照
                    if (!acivity.mPhotoMode.equals(ConfigConstant.PHOTO_MODE_WITHOUT_PHOTO)) {
                        acivity.cameraView.captureImage();
                    }
                    // 显示ui
                    boolean hasUser = handleUi(acivity);
                    if (hasUser) {
//                        // 拍照
//                        acivity.cameraView.captureImage();
                    } else {
                        LogUtils.d(MainActivity.class.getSimpleName(), "-----数据库中不存在该用户-----");
                    }
                    break;

                case RESET_UI:
                    acivity.ivSuccessWords.setVisibility(View.INVISIBLE);
                    acivity.tvCardId.setVisibility(View.INVISIBLE);
                    acivity.tvUserName.setVisibility(View.INVISIBLE);
                    acivity.tvClassName.setVisibility(View.INVISIBLE);
                    acivity.tvUserCode.setVisibility(View.INVISIBLE);
                    acivity.tvSwipeFaceInfo.setVisibility(View.INVISIBLE);
                    acivity.ivUserFace.setImageResource(R.drawable.default_user_face);
                    acivity.tvMarkedWords.setVisibility(View.VISIBLE);
                    acivity.tvMarkedWords.setText("请刷卡");
                    break;
                case HIDE_UPDOWN_DIRECTION:
                    // 隐藏弹窗,5秒后自动消失上下车选择
                    acivity.isAutoHide = true;
                    sendEmptyMessage(ADD_SWIPE_DATA);
                    acivity.rlDirectionUpdown.setVisibility(View.GONE);
                    break;
                case ADD_SWIPE_DATA:
                    // 添加前移除所有消息。
                    removeMessages(ADD_SWIPE_DATA);
                    LogUtils.d(MainActivity.class.getSimpleName(), "-------------添加数据-----------------");
                    // 加入数据库
                    SwipeCard swipeCard = new SwipeCard();
                    swipeCard.setAddress(acivity.mAddress);
                    swipeCard.setAltitude(acivity.mAltitude);
                    swipeCard.setLatitude(acivity.mLatitude);
                    swipeCard.setLongitude(acivity.mLongitude);
                    swipeCard.setSpeed(acivity.mSpeed);
                    swipeCard.setBearing(acivity.mBearing);
                    swipeCard.setGpsTime(acivity.mGpsTime);
                    swipeCard.setUpdownDirection(acivity.mUpdownDirection);

                    swipeCard.setCardId(acivity.mCardId);
                    swipeCard.setIsUpload("N");
                    swipeCard.setFaceUid(acivity.mFaceUid);
                    swipeCard.setPhotoPath(acivity.mCameraPhotoPath);
                    swipeCard.setTouchTime(acivity.mTouchTime);

                    // 添加数据
                    RealmHelper.addSwipeCardData(acivity.mSwipeCardInfoRealm, swipeCard);

                    acivity.sendData2H5();
                    break;
                case REFRESH_H5:
                    //刷新H5
                    acivity.refreshH5Data();

                    break;


            }
        }


        /**
         * 界面的显示
         *
         * @param acivity
         */
        @SuppressLint("SetTextI18n")
        private boolean handleUi(MainActivity acivity) {
//            if (acivity.mUserRealm.isClosed()) {
//                return false;
//            }
            User user = RealmHelper.getUserByCardId(acivity.mUserRealm, acivity.mCardId);
            if (user == null) {
                acivity.ivSuccessWords.setVisibility(View.INVISIBLE);
                acivity.tvCardId.setVisibility(View.INVISIBLE);
                acivity.tvUserName.setVisibility(View.INVISIBLE);
                acivity.tvClassName.setVisibility(View.INVISIBLE);
                acivity.tvUserCode.setVisibility(View.INVISIBLE);
                acivity.tvSwipeFaceInfo.setVisibility(View.INVISIBLE);
                acivity.ivUserFace.setImageResource(R.drawable.default_user_face);
                acivity.tvMarkedWords.setVisibility(View.VISIBLE);
                acivity.tvMarkedWords.setText(acivity.mCardId);
            } else {
                // 播报姓名
                String name = user.getName();
                VoiceHelper.playTtsVoice(name);

                acivity.tvMarkedWords.setVisibility(View.INVISIBLE);
                acivity.ivSuccessWords.setVisibility(View.VISIBLE);
                acivity.tvUserName.setVisibility(View.VISIBLE);
                acivity.tvSwipeFaceInfo.setVisibility(View.VISIBLE);
                acivity.ivUserFace.setVisibility(View.VISIBLE);
                acivity.tvClassName.setVisibility(View.VISIBLE);
                acivity.tvUserCode.setVisibility(View.VISIBLE);
                acivity.tvCardId.setVisibility(View.VISIBLE);

                String photoPath = user.getPhotoPath();
                // 当前用户头像的路径，如果拍照失败，就使用这张图片
//            acivity.mCurrentUserPhotoPath = photoPath;
                GlideHelper.loadCircleImage(acivity.ivUserFace, photoPath);
                acivity.tvUserName.setText(name);
                acivity.tvCardId.setText(acivity.mCardId);
                acivity.tvClassName.setText(user.getDeptName());
                acivity.tvUserCode.setText(user.getCode());
            }

//            int count = RealmHelper.getSwipFaceCountForFaceUid(acivity.mSwipeCardInfoRealm, acivity.mFaceUid);
            int count = RealmHelper.getSwipCardCountForCardId(acivity.mSwipeCardInfoRealm, acivity.mCardId);
            if (count == 0) {
                acivity.mPeopleCount++;
                acivity.tvPeopleCount.setText(acivity.mPeopleCount + " 人");
            }
            count++;
            acivity.tvSwipeFaceInfo.setText("第 " + count + " 次刷卡\t" + TimeUtils.getCurrentTime("HH:mm:ss"));

            // 统计信息
            acivity.mSwipeCardCount++;
            acivity.tvSwipeCount.setText(acivity.mSwipeCardCount + " 次");
            // 未上传的数量
            acivity.mNotUploadCount++;
            acivity.lampProgressBar.setSize(acivity.mNotUploadCount + acivity.mGpsInfos.size());
            // 个人信息显示5秒后，重新设置ui
            sendEmptyMessageDelayed(RESET_UI, 5000);

            return true;
        }
    }


}
