package com.seahung.handrollmachine.activity;

import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.constant.AppConstant;
import com.seahung.handrollmachine.helper.AsyncTaskHelper;
import com.seahung.handrollmachine.request.LoginSettingRequest;
import com.seahung.handrollmachine.response.LoginSettingResponse;
import com.seahung.handrollmachine.util.XmlHandlerUtils;
import com.unengchan.sdk.base.BaseActivity;
import com.unengchan.sdk.base.BaseHandler;
import com.unengchan.sdk.manager.AppManager;
import com.unengchan.sdk.util.AppUtils;
import com.unengchan.sdk.util.LogUtils;
import com.unengchan.sdk.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by unengchan on 2018/7/12
 * 密码界面
 */
public class PasswordActivity extends BaseActivity implements View.OnLongClickListener {
    @BindView(R.id.tv_pwd_1)
    TextView tvPwd1;
    @BindView(R.id.tv_pwd_2)
    TextView tvPwd2;
    @BindView(R.id.tv_pwd_3)
    TextView tvPwd3;
    @BindView(R.id.tv_pwd_4)
    TextView tvPwd4;
    @BindView(R.id.tv_pwd_5)
    TextView tvPwd5;
    @BindView(R.id.tv_pwd_6)
    TextView tvPwd6;
    @BindView(R.id.tv_pwd_7)
    TextView tvPwd7;
    @BindView(R.id.tv_pwd_8)
    TextView tvPwd8;
    @BindView(R.id.btn_pwd_back)
    Button btnPwdBack;
    @BindView(R.id.btn_pwd_confirm)
    Button btnPwdConfirm;
    @BindView(R.id.btn_num_1)
    Button btnNum1;
    @BindView(R.id.btn_num_2)
    Button btnNum2;
    @BindView(R.id.btn_num_3)
    Button btnNum3;
    @BindView(R.id.btn_num_4)
    Button btnNum4;
    @BindView(R.id.btn_num_5)
    Button btnNum5;
    @BindView(R.id.btn_num_6)
    Button btnNum6;
    @BindView(R.id.btn_num_7)
    Button btnNum7;
    @BindView(R.id.btn_num_8)
    Button btnNum8;
    @BindView(R.id.btn_num_9)
    Button btnNum9;
    @BindView(R.id.btn_num_0)
    Button btnNum0;
    @BindView(R.id.btn_num_delete)
    ImageButton btnNumDelete;

    private static final  int AUTO_BACK = 10;
    public AppManager mAppManager;
    private StringBuffer mStringBuffer;
    public List<TextView> mTextViewList;
    public LoginSettingRequest mLoginSettingRequest;
    public PasswordHandler mPasswordHandler;
    public KProgressHUD mHud;

    @Override
    protected int getLayoutId() {
        return R.layout.password_activity;
    }

    @Override
    protected void initData() {
        super.initData();
        mAppManager = AppManager.getInstance();
        // 存放按键数字
        mStringBuffer = new StringBuffer();
        // textview 集合
        mTextViewList = new ArrayList<>();
        // 登陆请求
        mLoginSettingRequest = new LoginSettingRequest();
        // 处理登陆逻辑
        mPasswordHandler = new PasswordHandler(PasswordActivity.this);
        // 一分钟不输入密码，返回到主页面
        mPasswordHandler.sendEmptyMessageDelayed(AUTO_BACK,30000);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 特殊字符 ●
        mTextViewList.add(tvPwd1);
        mTextViewList.add(tvPwd2);
        mTextViewList.add(tvPwd3);
        mTextViewList.add(tvPwd4);
        mTextViewList.add(tvPwd5);
        mTextViewList.add(tvPwd6);
        mTextViewList.add(tvPwd7);
        mTextViewList.add(tvPwd8);
        // 删除按钮的长按事件
        btnNumDelete.setOnLongClickListener(this);
    }

    @OnClick({R.id.btn_pwd_back, R.id.btn_pwd_confirm, R.id.btn_num_1, R.id.btn_num_2, R.id.btn_num_3,
            R.id.btn_num_4, R.id.btn_num_5, R.id.btn_num_6, R.id.btn_num_7, R.id.btn_num_8,
            R.id.btn_num_9, R.id.btn_num_0, R.id.btn_num_delete})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        String sNum;
        switch (viewId) {
            case R.id.btn_pwd_back:
                mPasswordHandler.removeMessages(AUTO_BACK);
                goBack();
                return;
            case R.id.btn_pwd_confirm:
                mPasswordHandler.removeMessages(AUTO_BACK);
                loginSetting();
                return;
            case R.id.btn_num_delete:
                deletePasswordNumber();
                return;
            default:
                // 获取按下的数字
                sNum = ((Button) findViewById(viewId)).getText().toString();
                break;
        }
        // 保证密码只有8位
        if (mStringBuffer.length() > 7) {
            return;
        }
        // 只有按下数字才调用
        mStringBuffer.append(sNum);
        refreshPassword();
    }

    /**
     * 点击返回按钮，处理逻辑
     */
    private void goBack() {

        // 退出activity
        mAppManager.removeActivity(this);
    }

    /**
     * 登陆设置界面
     */
    private void loginSetting() {
        if (mStringBuffer.length() < 8) {
            ToastUtils.show(mContext.getString(R.string.password_input_complete_password));
            return;
        }
        // 开始登陆，显示登陆等待窗口
        mHud = ToastUtils.showHud(mContext, mContext.getString(R.string.password_logining));
        String password = mStringBuffer.toString();
        mLoginSettingRequest.setPassword(password);
        // 启动线程登陆
        AppUtils.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                String xmlStr = AsyncTaskHelper.getXmlResponse(mLoginSettingRequest);
                LoginSettingResponse response = XmlHandlerUtils.getLoginSettingResponse(xmlStr);
                String canSetting = response.getCan_setting();
                SystemClock.sleep(1000);
                if (AppConstant.YES.equals(canSetting)) {
                    // 密码校验成功
                    mPasswordHandler.sendEmptyMessage(AppConstant.SUCCESS);
                } else if (AppConstant.NO.equals(canSetting)) {
                    // 密码校验失败
                    mPasswordHandler.sendEmptyMessage(AppConstant.FAILURE);
                } else {
                    // 其它情况
                    mPasswordHandler.sendEmptyMessage(AppConstant.UNKNOWN);
                }
                LogUtils.d(tag, canSetting);
            }
        });
    }

    /**
     * 删除密码数字
     */
    private void deletePasswordNumber() {
        int length = mStringBuffer.length();
        if (length > 0) {
            int index = length - 1;
            mStringBuffer.deleteCharAt(index);
            mTextViewList.get(index).setText("");
        }
    }

    /**
     * 刷新密码，并刷新ui
     */
    private void refreshPassword() {
        for (int i = 0; i < mStringBuffer.length(); i++) {
            TextView textView = mTextViewList.get(i);
            String text = textView.getText().toString();
            if ("●".equals(text)) {
                continue;
            }
            textView.setText("●");
        }
    }

    /**
     * 长按事件
     *
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.btn_num_delete:
                if (mStringBuffer.length() <= 0) {
                    // 没有输入密码的情况下，不用重新设置textview
                    return true;
                }
                // 长按删除所以密码数字
                mStringBuffer.setLength(0);
                for (int i = 0; i < mTextViewList.size(); i++) {
                    TextView textView = mTextViewList.get(i);
                    String text = textView.getText().toString();
                    if (!"●".equals(text)) {
                        continue;
                    }
                    textView.setText("");
                }
                return true;
        }
        return false;
    }

    /**
     * 处理登陆逻辑
     */
    private static class PasswordHandler extends BaseHandler<PasswordActivity> {

        public PasswordHandler(PasswordActivity activity) {
            super(activity);
        }

        @Override
        protected void onHandleMessage(PasswordActivity acivity, Message msg) {
            // 取消登陆弹窗
            if (acivity.mHud != null) {
                acivity.mHud.dismiss();
            }
            switch (msg.what) {
                case AppConstant.SUCCESS:
                    // 打开设置界面
                    acivity.startActivity(SettingActivity.class);
                    acivity.mAppManager.removeActivity(acivity);
                    break;
                case AppConstant.FAILURE:
                    acivity.mPasswordHandler.sendEmptyMessageDelayed(AUTO_BACK, 45000);
                    ToastUtils.show(acivity.mContext.getString(R.string.password_input_password_wrong));
                    break;
                case AppConstant.UNKNOWN:
                    acivity.mPasswordHandler.sendEmptyMessageDelayed(AUTO_BACK, 45000);
                    ToastUtils.show(acivity.mContext.getString(R.string.network_connection_exception));
                    break;
                case AUTO_BACK:
                    acivity.goBack();
                    break;
            }
        }
    }
}
