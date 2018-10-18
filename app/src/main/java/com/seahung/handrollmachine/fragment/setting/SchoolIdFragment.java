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
 * 设置学校id
 */
public class SchoolIdFragment extends GlobalSettingFragment implements View.OnLongClickListener {

    @BindView(R.id.tv_school_id_code)
    TextView tvSchoolIdCode;
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
        // 用不到线程，不考虑线程安全
        mStringBuilder = new StringBuilder();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mView = view;
        tvTitleName.setText(mContext.getString(R.string.setting_school_id));

        mConfigManager = ConfigManager.getInstance();
        String schoolId = mConfigManager.getSchoolId();
        tvSchoolIdCode.setText(schoolId);
        mStringBuilder.append(schoolId);
        btnNumDelete.setOnLongClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.school_id_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_SCHOOL_ID_FRAGMENT;
    }

    @OnClick({R.id.btn_num_1, R.id.btn_num_2, R.id.btn_num_3, R.id.btn_num_4, R.id.btn_num_5,
            R.id.btn_num_6, R.id.btn_num_7, R.id.btn_num_8, R.id.btn_num_9, R.id.btn_num_0,
            R.id.btn_num_delete,R.id.iv_title_back})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btn_num_delete:
                deleteSchoolId();
                return;
            case R.id.iv_title_back:
                mFragmentListener.onSwitchFragment(FragmentConstant.SETTING_SETTING_LIST_FRAGMENT);
                break;
            default:
                String sNum = ((Button) mView.findViewById(viewId)).getText().toString();
                refreshSchoolId(sNum);
                break;
        }
    }

    /**
     * 删除id，一个一个删除
     */
    private void deleteSchoolId() {
        int length = mStringBuilder.length();
        if (length < 5) {
            // 保留 "sch_"
            return;
        }
        mStringBuilder.deleteCharAt(length - 1);
        String schoolId = mStringBuilder.toString();
        tvSchoolIdCode.setText(schoolId);
        mConfigManager.setSchoolId(schoolId);
    }

    /**
     * 刷新学校id，并保存
     *
     * @param sNum 按下的数字
     */
    private void refreshSchoolId(String sNum) {
        if (mStringBuilder.length() > 30) {
            return;
        }
        mStringBuilder.append(sNum);
        String schoolId = mStringBuilder.toString();
        tvSchoolIdCode.setText(schoolId);
        // 保存学校id，按下即保存
        mConfigManager.setSchoolId(schoolId);
    }

    /**
     * 长按事件
     *
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {
        int length = mStringBuilder.length();
        if (length > 4) {
            mStringBuilder.delete(4, length);
            String schoolId = mStringBuilder.toString();
            tvSchoolIdCode.setText(schoolId);
            mConfigManager.setSchoolId(schoolId);
        }
        return true;
    }
}
