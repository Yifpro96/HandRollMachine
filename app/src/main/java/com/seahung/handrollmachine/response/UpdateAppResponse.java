package com.seahung.handrollmachine.response;

/**
 * Created by unengchan on 2018/5/25
 * 更新app的回复
 */
public class UpdateAppResponse extends BaseResponse {
    private String upgrade;
    private String app_version;
    private String app_detail;
    private String app_url;
    private String new_terminal_mac;

    public String getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getApp_detail() {
        return app_detail;
    }

    public void setApp_detail(String app_detail) {
        this.app_detail = app_detail;
    }

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public String getNew_terminal_mac() {
        return new_terminal_mac;
    }

    public void setNew_terminal_mac(String new_terminal_mac) {
        this.new_terminal_mac = new_terminal_mac;
    }
}
