package com.seahung.handrollmachine.util;

import android.support.v4.util.ArrayMap;

import com.seahung.handrollmachine.constant.AppConstant;
import com.seahung.handrollmachine.request.ServiceUrlRequest;
import com.seahung.handrollmachine.response.ServiceUrlResponse;
import com.unengchan.sdk.util.IOUtils;
import com.unengchan.sdk.util.LogUtils;
import com.unengchan.sdk.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by unengchan on 2018/5/11
 * 数据下载和上传辅助类
 */
public class HttpUtils {
    public static final String TAG = HttpUtils.class.getSimpleName();
    // 服务器地址缓存集合
    public static ArrayMap<String, String> mServiceUrlMap = new ArrayMap<>();
    // 超时时间设置
    public static final int CONNECT_TIMEOUT = 3000;
    public static final int READ_TIMEOUT = 15000;


    /**
     * 进度接口回调
     */
    public interface HttpCallbackListener {
        // 回调加载进度
        void onHttpProgress(float progress);

        // 回调结果
        void onHttpResult(boolean success);
    }

    /**
     * 获取服务器地址
     *
     * @param transCode 交易码
     * @param action    获取数据标识
     * @return
     */
    public static String getServiceUrl(String transCode, String action) {
        String serviceUrl = null;
        // 缓存服务器地址
        String key = transCode + File.separator + action;
        String cacheUrl = mServiceUrlMap.get(key);
        if (StringUtils.isNotEmpty(cacheUrl)) {
            return cacheUrl;
        }
        ServiceUrlRequest request = new ServiceUrlRequest();
        request.setTrans_code(transCode);
        String xmlStr = doPost(AppConstant.DNS_SERVICE_URL, request.toXMLString());
        ServiceUrlResponse response = XmlHandlerUtils.getServiceUrlResponse(xmlStr);
        if (response != null && StringUtils.isNotEmpty(response.getServiceUrl())) {
            // 获取服务器地址，并且放到缓存中
            serviceUrl = response.getServiceUrl();
            mServiceUrlMap.put(key, serviceUrl);
        }
        return serviceUrl;
    }

    /**
     * 下载文件
     *
     * @param url      下载地址
     * @param filePath 文件路径
     * @return 文件路径
     */
    public static String doGet(String url, String filePath) {
        return doGet(url, filePath, null);
    }

    /**
     * 下载文件，包括csv、
     *
     * @param url
     * @param filePath
     * @param callbackListener
     * @return
     */
    public static String doGet(String url, String filePath, HttpCallbackListener callbackListener) {
        String result = "";
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        try {
            String sUrl = URLEncoder.encode(url, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("%3A", ":")
                    .replaceAll("%2F", "/");
            URL requestUrl = new URL(sUrl);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            if (connection.getResponseCode() != 200) {
                return result;
            }
            File file = new File(filePath);
            fos = new FileOutputStream(file);
            bis = new BufferedInputStream(connection.getInputStream());
            byte[] bytes = new byte[1024];
            int current = 0;
            int total = connection.getContentLength();
            int temp;
            float currentProgress = 0f;
            while ((temp = bis.read(bytes)) != -1) {
                fos.write(bytes, 0, temp);
                if (callbackListener == null) {
                    continue;
                }
                // 计算进度，保留1位小数
                current += temp;
                float progress = ((int) (current * 1000.0f / total)) / 10.0f;
                if (progress != currentProgress) {
                    callbackListener.onHttpProgress(progress);
                    currentProgress = progress;
                }
            }
            fos.flush();
            result = file.getAbsolutePath();
            if (callbackListener != null) {
                // 下载成功
                callbackListener.onHttpResult(true);
            }
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage());
            if (callbackListener != null) {
                // 下载失败
                callbackListener.onHttpResult(false);
            }
        } finally {
            IOUtils.close(fos);
            IOUtils.close(bis);
//            if (connection != null) {
//                connection.disconnect();
//            }
        }
        return result;
    }

    /**
     * post string
     *
     * @param url
     * @param xmlStr
     * @return
     */
    public static String doPost(String url, String xmlStr) {
//        LogUtils.d(TAG,url+"\n"+ xmlStr);
        byte[] bytes = xmlStr.getBytes();
        return doPost(url, bytes);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param file
     * @return
     */
    public static String doPost(String url, File file) {
        String result = "";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int len = fis.available();
            byte[] bytes = new byte[len];
            fis.read(bytes, 0, len);
            result = doPost(url, bytes);
        } catch (Exception e) {
            // 文件找不到或读取失败
            LogUtils.d(TAG, e.getMessage());
        } finally {
            IOUtils.close(fis);
        }
        return result;
    }

    /**
     * 上传数据到后台，合并了上传文件跟字符串信息
     *
     * @param url
     * @param bytes
     * @return
     */
    private static String doPost(String url, byte[] bytes) {
        String result = "";
        DataOutputStream dos = null;
        BufferedReader reader = null;
        try {
            // 去掉中文字符
            String sUrl = URLEncoder.encode(url, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("%3A", ":")
                    .replaceAll("%2F", "/");
            URL requestUrl = new URL(sUrl);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Type", "text/xml");
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-length", String.valueOf(bytes.length));
            dos = new DataOutputStream(connection.getOutputStream());
            dos.write(bytes);
            dos.flush();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String temp;
            while ((temp = reader.readLine()) != null) {
                result += temp;
            }
            IOUtils.close(inputStream);
            IOUtils.close(inputStreamReader);
        } catch (Exception e) {
            // 打印异常信息
//            LogUtils.d(TAG, "========异常信息===="+e.getMessage());
        } finally {
            IOUtils.close(dos);
            IOUtils.close(reader);
//            不一定要关掉连接
//            if (connection != null) {
//                connection.disconnect();
//            }
        }
        return result;
    }
}
