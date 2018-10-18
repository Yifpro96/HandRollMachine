package com.seahung.handrollmachine.response;

/**
 * Created by unengchan on 2018/7/22
 * 上传文件的回执
 */
public class UploadFileResponse extends BaseResponse {

    private String uploadReceipt;

    public String getUploadReceipt() {
        return uploadReceipt;
    }

    public void setUploadReceipt(String uploadReceipt) {
        this.uploadReceipt = uploadReceipt;
    }
}
