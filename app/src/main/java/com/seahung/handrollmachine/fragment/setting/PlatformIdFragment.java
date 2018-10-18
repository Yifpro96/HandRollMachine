package com.seahung.handrollmachine.fragment.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.constant.FragmentConstant;
import com.seahung.handrollmachine.global.GlobalSettingFragment;
import com.seahung.handrollmachine.manager.ConfigManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by unengchan on 2018/6/5
 * 平台编码
 */
public class PlatformIdFragment extends GlobalSettingFragment implements View.OnLongClickListener{
    @BindView(R.id.tv_platform_id_code)
    TextView tvPlatformIdCode;
    @BindView(R.id.btn_num_delete)
    ImageButton btnNumDelete;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    public View mView;
    public StringBuilder mStringBuilder;
    public ConfigManager mConfigManager;

    @Override
    protected void initData() {
        super.initData();
        mConfigManager = ConfigManager.getInstance();
        mStringBuilder = new StringBuilder();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mView = view;
        tvTitleName.setText(mContext.getString(R.string.setting_platform_id));
        btnNumDelete.setOnLongClickListener(this);
        String terminalId = mConfigManager.getPlatformId();
        tvPlatformIdCode.setText(terminalId);
        mStringBuilder.append(terminalId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.platform_id_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_PLATFORM_ID_FRAGMENT;
    }

    @OnClick({R.id.btn_num_1, R.id.btn_num_2, R.id.btn_num_3, R.id.btn_num_4, R.id.btn_num_5,
            R.id.btn_num_6, R.id.btn_num_7, R.id.btn_num_8, R.id.btn_num_9, R.id.btn_num_0,
            R.id.btn_num_delete,R.id.iv_title_back})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.iv_title_back:
                mFragmentListener.onSwitchFragment(FragmentConstant.SETTING_SETTING_LIST_FRAGMENT);
                break;
            case R.id.btn_num_delete:
                deletePlatformId();
                return;
            default:
                String sNum = ((Button) mView.findViewById(viewId)).getText().toString();
                refreshPlatformId(sNum);
                break;
        }
    }

    /**
     * 刷新平台id并保存
     * @param sNum 键盘数字
     */
    private void refreshPlatformId(String sNum) {
        if (mStringBuilder.length() >= 20) {
            return;
        }
        mStringBuilder.append(sNum);
        String platformId = mStringBuilder.toString();
        tvPlatformIdCode.setText(platformId);
        mConfigManager.setPlatformId(platformId);
    }

    /**
     * 删除id
     */
    private void deletePlatformId() {
        int length = mStringBuilder.length();
        if (length < 1) {
            return;
        }
        mStringBuilder.deleteCharAt(length - 1);
        if (mStringBuilder.length() > 0) {
            tvPlatformIdCode.setText(mStringBuilder.toString());
        }else {
            tvPlatformIdCode.setText("");
            tvPlatformIdCode.setHint(mContext.getString(R.string.input_platform_id));
        }
        mConfigManager.setPlatformId(mStringBuilder.toString());
    }

    @Override
    public boolean onLongClick(View v) {
        if (mStringBuilder.length() <=0) {
            return true;
        }
        mStringBuilder.setLength(0);
        tvPlatformIdCode.setText("");
        tvPlatformIdCode.setHint(mContext.getString(R.string.input_platform_id));
        mConfigManager.setPlatformId(mStringBuilder.toString());
        return true;
    }
}
