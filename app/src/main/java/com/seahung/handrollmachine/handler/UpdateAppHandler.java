package com.seahung.handrollmachine.handler;


import com.seahung.handrollmachine.response.UpdateAppResponse;

/**
 * Created by unengchan on 2018/5/25
 * 更新app的handler
 */
public class UpdateAppHandler extends CommonHandler<UpdateAppResponse> {
    @Override
    protected UpdateAppResponse getInstance() {
        return new UpdateAppResponse();
    }

    @Override
    protected void parseData(String tagName, String data) {
        UpdateAppResponse response = getResponse();
        parseCommonXmlStr(response, tagName, data);
        switch (tagName) {
            case "upg":
                response.setUpgrade(data);
                break;
            case "appv":
                response.setApp_version(data);
                break;
            case "detail":
                response.setApp_detail(data);
                break;
            case "url":
                response.setApp_url(data);
                break;
            case "new_terminal_mac":
                response.setNew_terminal_mac(data);
                break;
            default:
                break;
        }
    }
}
