package com.seahung.handrollmachine.request;

import com.unengchan.sdk.util.StringUtils;

/**
 * Created by unengchan on 2018/5/31
 * 登陆设置的请求
 */
public class LoginSettingRequest extends BaseRequest {

    String password;

    public LoginSettingRequest() {
        setTrans_code("rollm_check_setting_password");
    }

    public String getPassword() {
        if (StringUtils.isEmpty(password)) {
            password = "";
        }
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected void addExtraXMLString(StringBuffer buffer) {
        buffer.append("<password>").append(getPassword()).append("</password>\n");
    }
}
