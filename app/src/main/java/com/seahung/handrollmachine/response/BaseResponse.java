package com.seahung.handrollmachine.response;

/**
 * Created by unengchan on 2018/5/11
 * 响应基类
 */
public class BaseResponse {

    private String error_code;  // 错误码
    private String error_string;    // 错误信息
    private String redirect_url;    // 重定向地址
    private String pop_message;     // 弹窗信息

    private String delayMillis;     // 下一次访问服务器的延迟时间

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_string() {
        return error_string;
    }

    public void setError_string(String error_string) {
        this.error_string = error_string;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getPop_message() {
        return pop_message;
    }

    public void setPop_message(String pop_message) {
        this.pop_message = pop_message;
    }

    public String getDelayMillis() {
        return delayMillis;
    }

    public void setDelayMillis(String delayMillis) {
        this.delayMillis = delayMillis;
    }
}
