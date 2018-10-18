package com.seahung.handrollmachine.global;

import android.content.Context;

import com.unengchan.sdk.base.BaseFragment;

/**
 * Created by unengchan on 2018/7/12
 */
public abstract class GlobalSettingFragment extends BaseFragment {

    protected OnSwitchFragmentListener mFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSwitchFragmentListener) {
            mFragmentListener = (OnSwitchFragmentListener) context;
        }
    }

    /**
     * fragment的接口，用于fragment的相关信息回调和切换fragment
     */
    public interface OnSwitchFragmentListener {

        void onSwitchFragment(int desFragmentIndex);
    }
}
