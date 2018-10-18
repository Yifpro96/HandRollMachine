package com.seahung.handrollmachine.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.constant.FragmentConstant;
import com.seahung.handrollmachine.fragment.setting.AboutFragment;
import com.seahung.handrollmachine.fragment.setting.AnnounceBoardFragment;
import com.seahung.handrollmachine.fragment.setting.AttendTypeFragment;
import com.seahung.handrollmachine.fragment.setting.CardReadingFragment;
import com.seahung.handrollmachine.fragment.setting.ChangeSkinFragment;
import com.seahung.handrollmachine.fragment.setting.ClassIdFragment;
import com.seahung.handrollmachine.fragment.setting.InoutSchoolFragment;
import com.seahung.handrollmachine.fragment.setting.LocationFragment;
import com.seahung.handrollmachine.fragment.setting.PhotoModeFragment;
import com.seahung.handrollmachine.fragment.setting.PlatformIdFragment;
import com.seahung.handrollmachine.fragment.setting.PlayVoiceFragment;
import com.seahung.handrollmachine.fragment.setting.SchoolIdFragment;
import com.seahung.handrollmachine.fragment.setting.SettingListFragment;
import com.seahung.handrollmachine.fragment.setting.SyncDataFragment;
import com.seahung.handrollmachine.fragment.setting.TerminalIdFragment;
import com.seahung.handrollmachine.fragment.setting.UploadLogFragment;
import com.seahung.handrollmachine.fragment.setting.UserTypeFragment;
import com.seahung.handrollmachine.global.GlobalSettingFragment;
import com.unengchan.sdk.base.BaseActivity;
import com.unengchan.sdk.base.BaseFragment;

/**
 * Created by unengchan on 2018/7/12
 */
public class SettingActivity extends BaseActivity implements GlobalSettingFragment.OnSwitchFragmentListener {

    public FragmentManager mFragmentManager;

    public SchoolIdFragment mSchoolIdFragment;
    public ClassIdFragment mClassIdFragment;
    public TerminalIdFragment mTerminalIdFragment;
    public CardReadingFragment mCardReadingFragment;
    public PlatformIdFragment mPlatformIdFragment;
    public AttendTypeFragment mAttendTypeFragment;
    public PhotoModeFragment mPhotoModeFragment;
    public ChangeSkinFragment mChangeSkinFragment;
    public UserTypeFragment mUserTypeFragment;
    public InoutSchoolFragment mInoutSchoolFragment;
    public SyncDataFragment mSyncDataFragment;
    public PlayVoiceFragment mPlayVoiceFragment;
    public AnnounceBoardFragment mAnnounceBoardFragment;
    public AboutFragment mAboutFragment;
    public UploadLogFragment mUploadLogFragment;
    public SettingListFragment mSettingListFragment;
    public LocationFragment mLocationFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.setting_activity;
    }

    @Override
    protected void initData() {
        super.initData();
        mFragmentManager = getSupportFragmentManager();

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showFragment(FragmentConstant.SETTING_SETTING_LIST_FRAGMENT);
    }

    @Override
    public void onBackPressed() {
        // 点击系统的返回不做任何操作
    }

    /**
     * fragment的显示
     *
     * @param index
     */
    private void showFragment(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideCurrentFragment(transaction);
        switch (index) {
            case FragmentConstant.SETTING_SETTING_LIST_FRAGMENT:
                if (mSettingListFragment == null) {
                    mSettingListFragment = new SettingListFragment();
                    transaction.add(R.id.cl_setting_fragment, mSettingListFragment);
                }
                transaction.show(mSettingListFragment);
                break;
            case FragmentConstant.SETTING_SCHOOL_ID_FRAGMENT:
                if (mSchoolIdFragment == null) {
                    mSchoolIdFragment = new SchoolIdFragment();
                    transaction.add(R.id.cl_setting_fragment, mSchoolIdFragment);
                }
                transaction.show(mSchoolIdFragment);
                break;
            case FragmentConstant.SETTING_CLASS_ID_FRAGMENT:
                if (mClassIdFragment == null) {
                    mClassIdFragment = new ClassIdFragment();
                    transaction.add(R.id.cl_setting_fragment, mClassIdFragment);
                }
                transaction.show(mClassIdFragment);
                break;
            case FragmentConstant.SETTING_TERMINAL_ID_FRAGMENT:
                if (mTerminalIdFragment == null) {
                    mTerminalIdFragment = new TerminalIdFragment();
                    transaction.add(R.id.cl_setting_fragment, mTerminalIdFragment);
                }
                transaction.show(mTerminalIdFragment);
                break;
            case FragmentConstant.SETTING_CARD_READING_FRAGMENT:
                if (mCardReadingFragment == null) {
                    mCardReadingFragment = new CardReadingFragment();
                    transaction.add(R.id.cl_setting_fragment, mCardReadingFragment);
                }
                transaction.show(mCardReadingFragment);
                break;
            case FragmentConstant.SETTING_PLATFORM_ID_FRAGMENT:
                if (mPlatformIdFragment == null) {
                    mPlatformIdFragment = new PlatformIdFragment();
                    transaction.add(R.id.cl_setting_fragment, mPlatformIdFragment);
                }
                transaction.show(mPlatformIdFragment);
                break;
            case FragmentConstant.SETTING_ATTEND_TYPE_FRAGMENT:
                if (mAttendTypeFragment == null) {
                    mAttendTypeFragment = new AttendTypeFragment();
                    transaction.add(R.id.cl_setting_fragment, mAttendTypeFragment);
                }
                transaction.show(mAttendTypeFragment);
                break;
            case FragmentConstant.SETTING_PHOTO_MODE_FRAGMENT:
                if (mPhotoModeFragment == null) {
                    mPhotoModeFragment = new PhotoModeFragment();
                    transaction.add(R.id.cl_setting_fragment, mPhotoModeFragment);
                }
                transaction.show(mPhotoModeFragment);
                break;
            case FragmentConstant.SETTING_LOCATION_FRAGMENT:
                if (mLocationFragment == null) {
                    mLocationFragment = new LocationFragment();
                    transaction.add(R.id.cl_setting_fragment, mLocationFragment);
                }
                transaction.show(mLocationFragment);
                break;
            case FragmentConstant.SETTING_CHANGE_SKIN_FRAGMENT:
                if (mChangeSkinFragment == null) {
                    mChangeSkinFragment = new ChangeSkinFragment();
                    transaction.add(R.id.cl_setting_fragment, mChangeSkinFragment);
                }
                transaction.show(mChangeSkinFragment);
                break;
            case FragmentConstant.SETTING_USER_TYPE_FRAGMENT:
                if (mUserTypeFragment == null) {
                    mUserTypeFragment = new UserTypeFragment();
                    transaction.add(R.id.cl_setting_fragment, mUserTypeFragment);
                }
                transaction.show(mUserTypeFragment);
                break;
            case FragmentConstant.SETTING_INOUT_SCHOOL_FRAGMENT:
                if (mInoutSchoolFragment == null) {
                    mInoutSchoolFragment = new InoutSchoolFragment();
                    transaction.add(R.id.cl_setting_fragment, mInoutSchoolFragment);
                }
                transaction.show(mInoutSchoolFragment);
                break;
            case FragmentConstant.SETTING_SYNC_DATA_FRAGMENT:
                if (mSyncDataFragment == null) {
                    mSyncDataFragment = new SyncDataFragment();
                    transaction.add(R.id.cl_setting_fragment, mSyncDataFragment);
                }
                transaction.show(mSyncDataFragment);
                break;
            case FragmentConstant.SETTING_PLAY_VOICE_FRAGMENT:
                if (mPlayVoiceFragment == null) {
                    mPlayVoiceFragment = new PlayVoiceFragment();
                    transaction.add(R.id.cl_setting_fragment, mPlayVoiceFragment);
                }
                transaction.show(mPlayVoiceFragment);
                break;
            case FragmentConstant.SETTING_ANNOUNCE_BOARD_FRAGMENT:
                if (mAnnounceBoardFragment == null) {
                    mAnnounceBoardFragment = new AnnounceBoardFragment();
                    transaction.add(R.id.cl_setting_fragment, mAnnounceBoardFragment);
                }
                transaction.show(mAnnounceBoardFragment);
                break;
            case FragmentConstant.SETTING_ABOUT_FRAGMENT:
                if (mAboutFragment == null) {
                    mAboutFragment = new AboutFragment();
                    transaction.add(R.id.cl_setting_fragment, mAboutFragment);
                }
                transaction.show(mAboutFragment);
                break;
            case FragmentConstant.SETTING_UPLOAD_LOG_FRAGMENT:
                if (mUploadLogFragment == null) {
                    mUploadLogFragment = new UploadLogFragment();
                    transaction.add(R.id.cl_setting_fragment, mUploadLogFragment);
                }
                transaction.show(mUploadLogFragment);
                break;
        }
        transaction.commit();
    }

    /**
     * 切换fragment时，隐藏当前的fragment
     *
     * @param transaction
     */
    private void hideCurrentFragment(FragmentTransaction transaction) {
        hideFragment(transaction, mSettingListFragment);
        hideFragment(transaction, mSchoolIdFragment);
        hideFragment(transaction, mClassIdFragment);
        hideFragment(transaction, mTerminalIdFragment);
        hideFragment(transaction, mCardReadingFragment);
        hideFragment(transaction, mPlatformIdFragment);
        hideFragment(transaction, mAttendTypeFragment);
        hideFragment(transaction, mPhotoModeFragment);
        hideFragment(transaction, mChangeSkinFragment);
        hideFragment(transaction, mUserTypeFragment);
        hideFragment(transaction, mInoutSchoolFragment);
        hideFragment(transaction, mSyncDataFragment);
        hideFragment(transaction, mPlayVoiceFragment);
        hideFragment(transaction, mAnnounceBoardFragment);
        hideFragment(transaction, mAboutFragment);
        hideFragment(transaction, mUploadLogFragment);
        hideFragment(transaction,mLocationFragment);
    }

    /**
     * 隐藏fragment，统一判断是否隐藏
     *
     * @param transaction
     * @param fragment
     */
    private void hideFragment(FragmentTransaction transaction, BaseFragment fragment) {
        if (fragment != null && !fragment.isHidden()) {
            transaction.hide(fragment);
        }
    }

    @Override
    public void onSwitchFragment(int desFragmentIndex) {
        showFragment(desFragmentIndex);
    }
}
