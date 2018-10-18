package com.seahung.handrollmachine.runnable;

import com.seahung.handrollmachine.bean.database.SwipeCard;
import com.seahung.handrollmachine.bean.database.User;
import com.seahung.handrollmachine.constant.FileConstant;
import com.seahung.handrollmachine.fragment.setting.SyncDataFragment;
import com.seahung.handrollmachine.helper.RealmHelper;
import com.unengchan.sdk.util.FileUtils;

import io.realm.Realm;

/**
 * Created by unengchan on 2018/7/23
 * 清除数据的线程
 */
public class ClearDataRunnable implements Runnable {

    private int func;
    private ClearDataListener listener;

    public ClearDataRunnable(int func) {
        this.func = func;
    }

    @Override
    public void run() {

        if (listener != null) {
            listener.onStarClear();
        }
        Realm cardInfoRealm = RealmHelper.getSwipeCardInfoRealm();
        Realm userInfoRealm = RealmHelper.getUserInfoRealm();
        try {
            // 删除刷脸照片
            FileUtils.deleteFile(FileConstant.USER_CAMERA_PICTURE);
            // 删除刷卡数据
            cardInfoRealm.beginTransaction();
            cardInfoRealm.delete(SwipeCard.class);
            cardInfoRealm.commitTransaction();

            if (func == SyncDataFragment.FUNCTION_CLEAR_SWIPE_DATA) {
                if (listener != null) {
                    listener.onFinishClear("清除成功");
                }
                return;
            }
            // 删除用户头像
            FileUtils.deleteFile(FileConstant.USER_DATA_DIR);

            // 删除用户数据
            userInfoRealm.beginTransaction();
            userInfoRealm.delete(User.class);
            userInfoRealm.commitTransaction();

            if (listener != null) {
                listener.onFinishClear("清除成功");
            }

        } catch (Exception e) {
            if (listener != null) {
                listener.onFinishClear("清除失败，请重试");
            }
        } finally {
            cardInfoRealm.close();
            userInfoRealm.close();
        }

    }

    public void setListener(ClearDataListener listener) {
        this.listener = listener;
    }

    public interface ClearDataListener {
        void onStarClear();
        void onFinishClear(String str);
    }
}
