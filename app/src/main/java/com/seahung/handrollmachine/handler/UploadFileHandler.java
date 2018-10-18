package com.seahung.handrollmachine.handler;

import com.seahung.handrollmachine.response.UploadFileResponse;

/**
 * Created by unengchan on 2018/7/22
 * 上传文件的handler，获取文件回执
 */
public class UploadFileHandler extends CommonHandler<UploadFileResponse> {

    @Override
    protected UploadFileResponse getInstance() {
        return new UploadFileResponse();
    }

    @Override
    protected void parseData(String tagName, String data) {
        UploadFileResponse response = getResponse();
        parseCommonXmlStr(response, tagName, data);
        switch (tagName) {
            case "receipt.receipt":
                response.setUploadReceipt(data);
                break;
        }
    }
}
