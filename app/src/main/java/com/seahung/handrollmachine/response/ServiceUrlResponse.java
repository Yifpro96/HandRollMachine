package com.seahung.handrollmachine.response;

/**
 * Created by unengchan on 2018/5/14
 * 获取服务器地址的回复
 */
public class ServiceUrlResponse extends BaseResponse {
    private String serviceUrl;

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
