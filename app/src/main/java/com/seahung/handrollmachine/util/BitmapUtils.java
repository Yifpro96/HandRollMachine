package com.seahung.handrollmachine.util;

import android.graphics.Bitmap;

import com.unengchan.sdk.util.IOUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by unengchan on 2018/7/20
 * bitmap 的辅助类
 */
public class BitmapUtils {

    /**
     * 保存bitmap
     *
     * @param filePath
     * @param bitmap
     * @return
     */
    public static boolean saveBitmap(String filePath, Bitmap bitmap) {
        FileOutputStream fos = null;
        boolean successed;
        try {
            File file = new File(filePath);
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            successed = true;
        } catch (Exception e) {
            successed = false;
        } finally {
            IOUtils.close(fos);
        }
        return successed;

    }

}
