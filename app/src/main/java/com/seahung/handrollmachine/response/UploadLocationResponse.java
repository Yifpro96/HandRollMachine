package com.seahung.handrollmachine.response;

/**
 * Created by unengchan on 2018/8/20
 * 上传定位的回复
 */
public class UploadLocationResponse extends BaseResponse {

    private String nextUploadDuration;

    public String getNextUploadDuration() {
        return nextUploadDuration;
    }

    public void setNextUploadDuration(String nextUploadDuration) {
        this.nextUploadDuration = nextUploadDuration;
    }
}
