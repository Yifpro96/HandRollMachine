package com.seahung.handrollmachine.handler;

import com.seahung.handrollmachine.response.UploadLocationResponse;

/**
 * Created by unengchan on 2018/8/20
 * 上传定位的解析handler
 */
public class UploadLocationHandler extends CommonHandler<UploadLocationResponse>{
    @Override
    protected UploadLocationResponse getInstance() {
        return new UploadLocationResponse();
    }

    @Override
    protected void parseData(String tagName, String data) {
        UploadLocationResponse response = getResponse();
        parseCommonXmlStr(response,tagName,data);
        switch (tagName) {
            case "next_upload_duration":
                response.setNextUploadDuration(data);
                break;
        }
    }
}
