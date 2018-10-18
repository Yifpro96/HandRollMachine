package com.seahung.handrollmachine.fragment.setting;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.constant.FragmentConstant;
import com.seahung.handrollmachine.global.GlobalSettingFragment;
import com.seahung.handrollmachine.runnable.ClearDataRunnable;
import com.seahung.handrollmachine.runnable.SyncUserDataRunnable;
import com.seahung.handrollmachine.widget.CircleProgressView;
import com.unengchan.sdk.util.AppUtils;
import com.unengchan.sdk.util.LogUtils;
import com.unengchan.sdk.util.ToastUtils;

import java.util.concurrent.ExecutorService;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by unengchan on 2018/6/5
 * 同步数据
 */
public class SyncDataFragment extends GlobalSettingFragment implements View.OnClickListener,
        SyncUserDataRunnable.SyncUserDataListener , ClearDataRunnable.ClearDataListener{

    public static final int FUNCTION_RESYNC_DATA = 0;  // 重新同步
    public static final int FUNCTION_INCREMENT_SYNC_DATA = 1;  // 增量同步
    public static final int FUNCTION_CLEAR_SWIPE_DATA = 2; // 清除刷卡数据
    public static final int FUNCTION_CLEAR_DATA = 3;   // 清除数据

    @BindView(R.id.tv_title_name)
    TextView tvTitleName;

    private TextView tvDialogTitle;
    private CircleProgressView circleProgressView;
    private TextView tvDialogContentDesc;
    private LinearLayout llDialogButton;

    private AlertDialog mAlertDialog;
    public Button btnConfirm;
    public Button btnCancel;
    public int mDialogFunction;
    public SyncUserDataRunnable mSyncUserDataRunnable;
    public ExecutorService mThreadPool;
    public ClearDataRunnable mClearDataRunnable;
    public KProgressHUD mHUD;

    @Override
    protected void initData() {
        super.initData();
        mThreadPool = AppUtils.getThreadPool();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvTitleName.setText(mContext.getString(R.string.setting_sync_data));
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.circle_progress_altert_dialog, null);
        tvDialogTitle = ((TextView) dialogView.findViewById(R.id.tv_dialog_title));
        circleProgressView = ((CircleProgressView) dialogView.findViewById(R.id.circle_progress_view));
        tvDialogContentDesc = ((TextView) dialogView.findViewById(R.id.tv_dialog_content_desc));
        llDialogButton = ((LinearLayout) dialogView.findViewById(R.id.ll_dialog_button));
        btnConfirm = ((Button) dialogView.findViewById(R.id.btn_dialog_confirm));
        btnCancel = ((Button) dialogView.findViewById(R.id.btn_dialog_cancel));
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        // 创建dialog，有需要的的时候显示出来
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setNegativeButton("", null)
                .setPositiveButton("", null)
                .setCancelable(false)
                .setView(dialogView);
        mAlertDialog = builder.create();
        mAlertDialog.getWindow().setGravity(Gravity.CENTER);

        circleProgressView.setProgress(0f);
        circleProgressView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sync_data_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_SYNC_DATA_FRAGMENT;
    }

    @OnClick({R.id.ll_resync_data, R.id.ll_incremental_sync_data, R.id.ll_sync_data_clear_attend_data,
            R.id.ll_sync_data_clear_data, R.id.iv_title_back})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.iv_title_back) {
            mFragmentListener.onSwitchFragment(FragmentConstant.SETTING_SETTING_LIST_FRAGMENT);
            return;
        }
        circleProgressView.setVisibility(View.INVISIBLE);
        tvDialogContentDesc.setVisibility(View.VISIBLE);
        switch (viewId) {
            case R.id.ll_resync_data:
                mDialogFunction = FUNCTION_RESYNC_DATA;
                tvDialogTitle.setText(mContext.getString(R.string.resync_data));
                tvDialogContentDesc.setText(mContext.getString(R.string.resync_data_desc));
                mAlertDialog.show();
                break;
            case R.id.ll_incremental_sync_data:
                mDialogFunction = FUNCTION_INCREMENT_SYNC_DATA;
                tvDialogTitle.setText(mContext.getString(R.string.incremental_sync_data));
                tvDialogContentDesc.setText(mContext.getString(R.string.incremental_sync_data_desc));
                mAlertDialog.show();
                break;
            case R.id.ll_sync_data_clear_attend_data:
                mDialogFunction = FUNCTION_CLEAR_SWIPE_DATA;
                tvDialogTitle.setText(mContext.getString(R.string.clear_attendance_data));
                tvDialogContentDesc.setText(mContext.getString(R.string.clear_attendance_data_desc));
                mAlertDialog.show();
                break;
            case R.id.ll_sync_data_clear_data:
                mDialogFunction = FUNCTION_CLEAR_DATA;
                tvDialogTitle.setText(mContext.getString(R.string.clear_all_data));
                tvDialogContentDesc.setText(mContext.getString(R.string.clear_all_data_desc));
                mAlertDialog.show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog_confirm:
                btnConfirm.setEnabled(false);
                handleLogic(mDialogFunction, false);
                break;
            case R.id.btn_dialog_cancel:
                btnConfirm.setEnabled(true);
                handleLogic(mDialogFunction, true);
                break;
        }
    }

    /**
     * 处理dialog的逻辑
     *
     * @param dialogFunction
     */
    private void handleLogic(int dialogFunction, boolean cancel) {

        if (!cancel) {
            // 点击了确定
            circleProgressView.setProgress(0f);

            switch (dialogFunction) {
                case FUNCTION_RESYNC_DATA:
                    tvDialogTitle.setText("重新同步中...");
                    mSyncUserDataRunnable = new SyncUserDataRunnable();
                    mSyncUserDataRunnable.setSyncType("resync");
                    mSyncUserDataRunnable.setProgressView(circleProgressView);
                    mSyncUserDataRunnable.setListener(this);
                    mThreadPool.execute(mSyncUserDataRunnable);
                    tvDialogContentDesc.setVisibility(View.INVISIBLE);
                    circleProgressView.setVisibility(View.VISIBLE);
                    break;
                case FUNCTION_INCREMENT_SYNC_DATA:
                    tvDialogTitle.setText("增量同步中...");
                    mSyncUserDataRunnable = new SyncUserDataRunnable();
                    mSyncUserDataRunnable.setProgressView(circleProgressView);
                    mSyncUserDataRunnable.setSyncType("increment_sync");
                    mSyncUserDataRunnable.setListener(this);
                    mThreadPool.execute(mSyncUserDataRunnable);
                    tvDialogContentDesc.setVisibility(View.INVISIBLE);
                    circleProgressView.setVisibility(View.VISIBLE);
                    break;
                case FUNCTION_CLEAR_SWIPE_DATA:
                    mAlertDialog.dismiss();
                    btnConfirm.setEnabled(true);
                    clearData(FUNCTION_CLEAR_SWIPE_DATA);
                    break;
                case FUNCTION_CLEAR_DATA:
                    mAlertDialog.dismiss();
                    btnConfirm.setEnabled(true);
                    clearData(FUNCTION_CLEAR_DATA);
                    break;
            }
            return;
        }

        // 点击了取消按钮
        if (mSyncUserDataRunnable != null) {
            mSyncUserDataRunnable.setListener(null);
            mSyncUserDataRunnable.setCancel(true);
            mSyncUserDataRunnable = null;
        }
        mAlertDialog.dismiss();

    }

    /**
     * 清除数据
     * @param function
     */
    private void clearData(int function) {

        mClearDataRunnable = new ClearDataRunnable(function);
        mClearDataRunnable.setListener(this);
        mThreadPool.execute(mClearDataRunnable);

    }

    /**
     * 同步结果的监听
     * @param errorCode
     * @param errorStr
     */
    @Override
    public void onResult(final int errorCode, final String errorStr) {
        AppUtils.getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                    btnConfirm.setEnabled(true);
                }
                if (mSyncUserDataRunnable != null) {
                    mSyncUserDataRunnable.setListener(null);
                }
                if (errorCode != 0) {
                    ToastUtils.show(errorStr);
                }else {
                    LogUtils.d(SyncDataFragment.class.getSimpleName(), errorStr);
                }
            }
        });
    }

    @Override
    public void onStarClear() {
        AppUtils.getHandler().post(new Runnable() {
            @Override
            public void run() {
                mHUD = ToastUtils.showHud(mContext, "正在清除...");
            }
        });
    }

    @Override
    public void onFinishClear(final String str) {
        AppUtils.getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mHUD != null) {
                    mHUD.dismiss();
                    ToastUtils.show(str);
                }
                if (mClearDataRunnable != null) {
                    mClearDataRunnable.setListener(null);
                    mClearDataRunnable = null;
                }
            }
        });
    }
}
