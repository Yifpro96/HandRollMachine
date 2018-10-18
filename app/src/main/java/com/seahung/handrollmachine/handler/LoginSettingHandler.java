package com.seahung.handrollmachine.handler;


import com.seahung.handrollmachine.response.LoginSettingResponse;

/**
 * Created by unengchan on 2018/5/31
 */
public class LoginSettingHandler extends CommonHandler<LoginSettingResponse> {
    @Override
    protected LoginSettingResponse getInstance() {
        return new LoginSettingResponse();
    }

    @Override
    protected void parseData(String tagName, String data) {
        LoginSettingResponse response = getResponse();
        parseCommonXmlStr(response,tagName, data);
        if ("can_setting".equals(tagName)) {
            response.setCan_setting(data);
        }
    }
}
