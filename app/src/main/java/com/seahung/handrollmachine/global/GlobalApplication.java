package com.seahung.handrollmachine.global;

//import com.baidu.shield.authsdk.AuthSDK;
//import com.baidu.shield.authsdk.LicenseSDK;
//import com.facebook.stetho.Stetho;
import com.seahung.handrollmachine.helper.VoiceHelper;
import com.unengchan.sdk.base.BaseApplication;
import com.unengchan.sdk.handler.CrashHandler;
import com.unengchan.sdk.util.LogUtils;

import io.realm.Realm;

/**
 * Created by unengchan on 2018/7/12
 * 全局的应用对象类
 */
public class GlobalApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        // 数据库初始化
        Realm.init(mAppContext);

        // 人脸识别初始化
//        BaiduFaceHelper.init();
//        Stetho.initializeWithDefaults(mAppContext);
//
//        AuthSDK.init();
//        LicenseSDK.init(getAssets(),"license");

        // 崩溃日志记录和重启应用
//        CrashHandler.getInstance().init(mAppContext);

        // 初始化语音引擎，需要安装讯飞语音合成
        VoiceHelper.init();

        // 是否打印log
        LogUtils.openDebug = false;
    }

}
