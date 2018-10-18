package com.seahung.handrollmachine.handler;


import com.seahung.handrollmachine.response.BaseResponse;

/**
 * Created by unengchan on 2018/5/11
 * 基础response的handler
 */
public class BaseHandler extends CommonHandler<BaseResponse> {

    @Override
    protected BaseResponse getInstance() {
        return new BaseResponse();
    }

    @Override
    protected void parseData(String tagName, String data) {
        BaseResponse response = getResponse();
        parseCommonXmlStr(response,tagName, data);
        switch (tagName) {
            case "next_upload_delay_ms":
                response.setDelayMillis(data);
                break;
            case "next_upload_duration":
                response.setDelayMillis(data);
                break;
        }
    }


}
