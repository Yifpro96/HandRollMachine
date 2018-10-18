package com.seahung.handrollmachine.response;

/**
 * Created by unengchan on 2018/5/31
 * 登陆设置的回复
 */
public class LoginSettingResponse extends BaseResponse {

    private String can_setting;

    public String getCan_setting() {
        return can_setting;
    }

    public void setCan_setting(String can_setting) {
        this.can_setting = can_setting;
    }

}
