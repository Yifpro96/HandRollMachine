package com.unengchan.sdk.util;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;

/**
 * Created by unengchan on 2017/12/26.
 * 文件操作帮助类
 */

public class FileUtils {

    private FileUtils() {
    }

    /**
     * 结果回调接口
     */

    public interface FileCallbackListener {
        void onSuccess();

        void onfailure(String value);
    }

    /**
     * 通过路径获取文件，路径由目录和文件名组成
     *
     * @param dir
     * @param fileName
     * @return
     */
    public static File getFileFromPath(String dir, String fileName) {
        if (EmptyUtils.isEmpty(dir) || EmptyUtils.isEmpty(fileName)) {
            return null;
        }
        File file = new File(dir + File.separator + fileName);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    /**
     * 删除某一路径下的所有文件和文件夹
     *
     * @param filePath 即将删除的文件夹路径
     */
    public static void deleteFile(String filePath) {

        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                // 如果不是文件夹，直接删除
                if (!file.isDirectory()) {
                    file.delete();
                } else {
                    File[] files = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFile(files[i].getAbsolutePath());
                    }
                }
            }
        }

    }

    /**
     * 复制文件
     *
     * @param sourcefile 源文件
     * @param targetFile 目标文件
     */
    public static void copyFile(final File sourcefile, final File targetFile, final FileCallbackListener callback) {
        AppUtils.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                boolean successed = false;
                FileInputStream input = null;
                BufferedInputStream inbuff = null;
                FileOutputStream out = null;
                BufferedOutputStream outbuff = null;
                try {
                    input = new FileInputStream(sourcefile);
                    inbuff = new BufferedInputStream(input);

                    out = new FileOutputStream(targetFile);
                    out.flush();
                    outbuff = new BufferedOutputStream(out);

                    byte[] b = new byte[1024 * 5];
                    int len = 0;
                    while ((len = inbuff.read(b)) != -1) {
                        outbuff.write(b, 0, len);
                    }
                    outbuff.flush();
                    successed = true;
                } catch (Exception ex) {
                    successed = false;
                } finally {
                    IOUtils.close(inbuff);
                    IOUtils.close(outbuff);
                    IOUtils.close(out);
                    IOUtils.close(input);
                    // 成功和失败的回调
                    if (successed) {
                        callback.onSuccess();
                    } else {
                        callback.onfailure("复制失败");
                    }
                }
            }
        });
    }

    /**
     * 复制文件
     *
     * @param sourcefilePath
     * @param targetFilePath
     * @param callback
     */
    public static void copyFile(final String sourcefilePath, final String targetFilePath, final FileCallbackListener callback) {

        if (TextUtils.isEmpty(sourcefilePath) || TextUtils.isEmpty(targetFilePath)) {
            callback.onfailure("复制失败，文件路径为空");
            return;
        }
        AppUtils.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                boolean successed = false;
                FileInputStream input = null;
                BufferedInputStream inbuff = null;
                FileOutputStream out = null;
                BufferedOutputStream outbuff = null;
                final File srcFile = new File(sourcefilePath);
                final File desFile = new File(targetFilePath);
                try {
                    input = new FileInputStream(srcFile);
                    inbuff = new BufferedInputStream(input);

                    out = new FileOutputStream(desFile);
                    out.flush();
                    outbuff = new BufferedOutputStream(out);

                    byte[] b = new byte[1024 * 5];
                    int len = 0;
                    while ((len = inbuff.read(b)) != -1) {
                        outbuff.write(b, 0, len);
                    }
                    outbuff.flush();
                    successed = true;
                } catch (Exception ex) {
                    successed = false;
                } finally {
                    IOUtils.close(inbuff);
                    IOUtils.close(outbuff);
                    IOUtils.close(out);
                    IOUtils.close(input);
                    // 成功和失败的回调
                    if (successed) {
                        callback.onSuccess();
                    } else {
                        callback.onfailure("复制失败");
                    }
                }
            }
        });
    }

    /**
     * 保存bitmap文件
     */
    public static void saveBitmap(final String filePath, final Bitmap bitmap, final FileCallbackListener callback) {

        AppUtils.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fos = null;
                boolean successed = false;
                File file = new File(filePath);
                try {
                    fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    successed = true;
                } catch (Exception e) {
                    successed = false;
                } finally {
                    IOUtils.close(fos);
                    if (successed) {
                        callback.onSuccess();
                    } else {
                        callback.onfailure("保存失败");
                    }
                }
            }
        });
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String formatFileSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
