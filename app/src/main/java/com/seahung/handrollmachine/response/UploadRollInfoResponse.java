package com.seahung.handrollmachine.response;

/**
 * Created by unengchan on 2017/7/29.
 * 上传考勤信息的回复
 */

public class UploadRollInfoResponse extends BaseResponse {

    private long nextUploadDelayMs;
    private String needUpdateUserInfo;
    private String userAccountUid;
    private String userName;
    private String userCode;
    private String userPhotoUrl;
    private String userDeptId;
    private String userDeptName;
    private String cardId;
    private String touchTime;
    private String liveType;
    private String userRole;

    // 乘坐信息
    private String seat_number;
    private String updown_place_uid;
    private String updown_place_address;
    private String updown_place_address_short;
    private String updown_place_latitude;
    private String updown_place_longitude;


    public long getNextUploadDelayMs() {
        return nextUploadDelayMs;
    }

    public void setNextUploadDelayMs(long nextUploadDelayMs) {
        this.nextUploadDelayMs = nextUploadDelayMs;
    }

    public String getNeedUpdateUserInfo() {
        return needUpdateUserInfo;
    }

    public void setNeedUpdateUserInfo(String needUpdateUserInfo) {
        this.needUpdateUserInfo = needUpdateUserInfo;
    }

    public String getUserAccountUid() {
        return userAccountUid;
    }

    public void setUserAccountUid(String userAccountUid) {
        this.userAccountUid = userAccountUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(String userDeptId) {
        this.userDeptId = userDeptId;
    }

    public String getUserDeptName() {
        return userDeptName;
    }

    public void setUserDeptName(String userDeptName) {
        this.userDeptName = userDeptName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTouchTime() {
        return touchTime;
    }

    public void setTouchTime(String touchTime) {
        this.touchTime = touchTime;
    }

    public String getLiveType() {
        return liveType;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }

    public String getUpdown_place_uid() {
        return updown_place_uid;
    }

    public void setUpdown_place_uid(String updown_place_uid) {
        this.updown_place_uid = updown_place_uid;
    }

    public String getUpdown_place_address() {
        return updown_place_address;
    }

    public void setUpdown_place_address(String updown_place_address) {
        this.updown_place_address = updown_place_address;
    }

    public String getUpdown_place_address_short() {
        return updown_place_address_short;
    }

    public void setUpdown_place_address_short(String updown_place_address_short) {
        this.updown_place_address_short = updown_place_address_short;
    }

    public String getUpdown_place_latitude() {
        return updown_place_latitude;
    }

    public void setUpdown_place_latitude(String updown_place_latitude) {
        this.updown_place_latitude = updown_place_latitude;
    }

    public String getUpdown_place_longitude() {
        return updown_place_longitude;
    }

    public void setUpdown_place_longitude(String updown_place_longitude) {
        this.updown_place_longitude = updown_place_longitude;
    }
}
