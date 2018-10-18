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
import com.unengchan.sdk.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by unengchan on 2018/6/5
 * 终端id
 */
public class TerminalIdFragment extends GlobalSettingFragment implements View.OnLongClickListener {
    @BindView(R.id.tv_terminal_id_code)
    TextView tvTerminalIdCode;
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
        tvTitleName.setText(mContext.getString(R.string.setting_terminal_id));
        btnNumDelete.setOnLongClickListener(this);
        String terminalId = mConfigManager.getTerminalId();
        mStringBuilder.append(terminalId);
        tvTerminalIdCode.setText(terminalId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.terminal_id_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_TERMINAL_ID_FRAGMENT;
    }

    @OnClick({R.id.btn_num_1, R.id.btn_num_2, R.id.btn_num_3, R.id.btn_num_4, R.id.btn_num_5,
            R.id.btn_num_6, R.id.btn_num_7, R.id.btn_num_8, R.id.btn_num_9, R.id.btn_num_0,
            R.id.btn_num_delete,R.id.iv_title_back})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btn_num_delete:
                deleteTerminalId();
                return;
            case R.id.iv_title_back:
                mFragmentListener.onSwitchFragment(FragmentConstant.SETTING_SETTING_LIST_FRAGMENT);
                break;
            default:
                String sNum = ((Button) mView.findViewById(viewId)).getText().toString();
                refreshTerminalId(sNum);
                break;
        }
    }

    /**
     * 刷新并保存终端id
     *
     * @param sNum
     */
    private void refreshTerminalId(String sNum) {
        if (mStringBuilder.length() >= 15) {
            // 长度限制
            ToastUtils.show(mContext.getString(R.string.terminal_id_length_limited));
            return;
        }
        mStringBuilder.append(sNum);
        String terminalId = mStringBuilder.toString();
        tvTerminalIdCode.setText(terminalId);
        mConfigManager.setTerminalId(terminalId);
    }

    /**
     * 删除terminalid，逐个删除
     */
    private void deleteTerminalId() {
        int length = mStringBuilder.length();
        if (length < 1) {
            return;
        }
        mStringBuilder.deleteCharAt(length - 1);
        if (mStringBuilder.length() > 0) {
            tvTerminalIdCode.setText(mStringBuilder.toString());
        } else {
            tvTerminalIdCode.setText("");
            tvTerminalIdCode.setHint(mContext.getString(R.string.input_terminal_id));
        }
        mConfigManager.setTerminalId(mStringBuilder.toString());
    }

    @Override
    public boolean onLongClick(View v) {
        if (mStringBuilder.length() <= 0) {
            return true;
        }
        mStringBuilder.setLength(0);
        tvTerminalIdCode.setText("");
        tvTerminalIdCode.setHint(mContext.getString(R.string.input_terminal_id));
        mConfigManager.setTerminalId(mStringBuilder.toString());
        return true;
    }
}
