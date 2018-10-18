package com.seahung.handrollmachine.helper;

import android.widget.ImageView;

import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.glide.GlideApp;
import com.unengchan.sdk.util.AppUtils;
import com.unengchan.sdk.util.LogUtils;

/**
 * Created by unengchan on 2018/7/17
 * glide 图片加载器
 */
public class GlideHelper {

    public static void loadCircleImage(ImageView imageView, String url) {
//        boolean exists = new File(url).exists();
        LogUtils.d(GlideHelper.class.getSimpleName(), "-----路径======"+url);

        GlideApp.with(AppUtils.getAppContext())
                .load(url)
                .circleCrop()
                .error(R.drawable.default_user_face)
                .placeholder(R.drawable.default_user_face)
                .into(imageView);
    }

}
