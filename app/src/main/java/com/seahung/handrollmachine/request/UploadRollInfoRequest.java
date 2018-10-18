package com.seahung.handrollmachine.request;

/**
 * Created by unengchan on 2018/7/22
 * 上传考勤信息
 */
public class UploadRollInfoRequest extends BaseRequest {

    private String faceUid;
    private String cardId;
    private String kqTerminalId;
    private String touchType;
    private String schoolId;
    private String uploadPhotoReceipt;
    private String uploadPhotoFile;
    private String touchTime;
    private String reportTime;
    private String userAccountUid;
    private String userName;
    private String userCode;
    private String userPhotoUrl;
    private String userDeptId;
    private String userDeptName;
    private String liveType;
    private String userRole;
    private String userType;
    private String platformId;

    // gps定位数据
    private double latitude;
    private double longitude;
    private double altitude;
    private float speed;
    private float bearing;
    private String address;
    private String updownDirection;

    public UploadRollInfoRequest(){
        setCmd("schoolbus_submit_roll_info");
        setTrans_code("schoolbus_submit_roll_info");
    }

    public void setFaceUid(String faceId) {
        this.faceUid = faceId;
    }

    public String getFaceUid() {
        if (faceUid == null) {
            faceUid = "";
        }
        return faceUid;
    }

    public String getCardId() {
        if (cardId == null) {
            cardId = "";
        }
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getKqTerminalId() {
        if (kqTerminalId == null) {
            kqTerminalId = "";
        }
        return kqTerminalId;
    }

    public void setKqTerminalId(String kqTerminalId) {
        this.kqTerminalId = kqTerminalId;
    }

    public String getTouchType() {
        if (touchType == null) {
            touchType = "";
        }
        return touchType;
    }

    public void setTouchType(String touchType) {
        this.touchType = touchType;
    }

    public String getSchoolId() {
        if (schoolId == null) {
            schoolId = "";
        }
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getUploadPhotoReceipt() {
        if (uploadPhotoReceipt == null) {
            uploadPhotoReceipt = "";
        }
        return uploadPhotoReceipt;
    }

    public void setUploadPhotoReceipt(String uploadPhotoReceipt) {
        this.uploadPhotoReceipt = uploadPhotoReceipt;
    }

    public String getUploadPhotoFile() {
        if (uploadPhotoFile == null) {
            uploadPhotoFile = "";
        }
        return uploadPhotoFile;
    }

    public void setUploadPhotoFile(String uploadPhotoFile) {
        this.uploadPhotoFile = uploadPhotoFile;
    }

    public String getTouchTime() {
        if (touchTime == null) {
            touchTime = "";
        }
        return touchTime;
    }

    public void setTouchTime(String touchTime) {
        this.touchTime = touchTime;
    }

    public String getReportTime() {
        if (reportTime == null) {
            reportTime = "";
        }
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getUserAccountUid() {
        if (userAccountUid == null) {
            userAccountUid = "";
        }
        return userAccountUid;
    }

    public void setUserAccountUid(String userAccountUid) {
        this.userAccountUid = userAccountUid;
    }

    public String getUserName() {
        if (userName == null) {
            userName = "";
        }
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        if (userCode == null) {
            userCode = "";
        }
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPhotoUrl() {
        if (userPhotoUrl == null) {
            userPhotoUrl = "";
        }
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getUserDeptId() {
        if (userDeptId == null) {
            userDeptId = "";
        }
        return userDeptId;
    }

    public void setUserDeptId(String userDeptId) {
        this.userDeptId = userDeptId;
    }

    public String getUserDeptName() {
        if (userDeptName == null) {
            userDeptName = "";
        }
        return userDeptName;
    }

    public void setUserDeptName(String userDeptName) {
        this.userDeptName = userDeptName;
    }

    public String getLiveType() {
        if (liveType == null) {
            liveType = "";
        }
        return liveType;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public String getUserRole() {
        if (userRole == null) {
            userRole = "";
        }
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserType() {
        if (userType == null) {
            userType = "";
        }
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPlatformId() {
        if (platformId == null) {
            platformId = "";
        }
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public String getAddress() {
        if (address == null) {
            address = "";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getUpdownDirection() {
        if (updownDirection == null) {
            updownDirection = "";
        }
        return updownDirection;
    }

    public void setUpdownDirection(String updownDirection) {
        this.updownDirection = updownDirection;
    }

    @Override
    protected void addExtraXMLString(StringBuffer buffer) {
        buffer.append("<card_id>").append(getCardId()).append("</card_id>\n");
        buffer.append("<kq_terminal_id>").append(getKqTerminalId()).append("</kq_terminal_id>\n");
        buffer.append("<touch_type>").append(getTouchType()).append("</touch_type>\n");
        buffer.append("<school_id>").append(getSchoolId()).append("</school_id>\n");
        buffer.append("<upload_photo_receipt>").append(getUploadPhotoReceipt()).append("</upload_photo_receipt>\n");
        buffer.append("<upload_photo_file>").append(getUploadPhotoFile()).append("</upload_photo_file>\n");
        buffer.append("<touch_time>").append(getTouchTime()).append("</touch_time>\n");
        buffer.append("<report_time>").append(getReportTime()).append("</report_time>\n");
        buffer.append("<user_account_uid>").append(getUserAccountUid()).append("</user_account_uid>\n");
        buffer.append("<user_name>").append(getUserName()).append("</user_name>\n");
        buffer.append("<user_stu_code>").append(getUserCode()).append("</user_stu_code>\n");
        buffer.append("<user_photo_url>").append(getUserPhotoUrl()).append("</user_photo_url>\n");
        buffer.append("<user_dept_id>").append(getUserDeptId()).append("</user_dept_id>\n");
        buffer.append("<user_dept_name>").append(getUserDeptName()).append("</user_dept_name>\n");
        buffer.append("<live_type>").append(getLiveType()).append("</live_type>\n");
        buffer.append("<user_role>").append(getUserRole()).append("</user_role>\n");
        buffer.append("<tea_stu_rollm_type>").append(getUserType()).append("</tea_stu_rollm_type>\n");
        buffer.append("<from_client_system>").append(getPlatformId()).append("</from_client_system>\n");

        buffer.append("<updown_direction>").append(getUpdownDirection()).append("</updown_direction>\n");
        buffer.append("<gps_info>\n");
        buffer.append("<latitude>").append(getLatitude()).append("</latitude>\n");
        buffer.append("<longitude>").append(getLongitude()).append("</longitude>\n");
        buffer.append("<address>").append(getAddress()).append("</address>\n");
        buffer.append("<altitude>").append(getAltitude()).append("</altitude>\n");
        buffer.append("<speed>").append(getSpeed()).append("</speed>\n");
        buffer.append("<direction>").append(getBearing()).append("</direction>\n");
        buffer.append("</gps_info>\n");
    }
}
