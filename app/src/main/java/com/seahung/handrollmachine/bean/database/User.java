package com.seahung.handrollmachine.bean.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by unengchan on 2018/5/9
 * 用户基础数据
 */
public class User extends RealmObject{

    @PrimaryKey
    private String account_uid;
    private String card_id;
    private String face_uid;
    private String name;
    private String phone;   // 用户手机号码
    private String code;
    private String third_party_code;    // 第三方编码，云粉会员号
    private String photo_url;
    private String dept_id;
    private String dept_name;
    private String live_type;
    private String user_role;
    private String last_touch_time;
    private String photo_path;

    // 乘坐信息
    private String seat_number;
    private String updown_place_uid;
    private String updown_place_address;
    private String updown_place_address_short;
    private String updown_place_latitude;
    private String updown_place_longitude;

    public String getCardId() {
        return card_id;
    }

    public void setCardId(String card_id) {
        this.card_id = card_id;
    }

    public String getAccountUid() {
        return account_uid;
    }

    public void setAccountUid(String account_uid) {
        this.account_uid = account_uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhotoUrl() {
        return photo_url;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photo_url = photoUrl;
    }

    public String getDeptId() {
        return dept_id;
    }

    public void setDeptId(String deptId) {
        this.dept_id = deptId;
    }

    public String getDeptName() {
        return dept_name;
    }

    public void setDeptName(String deptName) {
        this.dept_name = deptName;
    }

    public String getLiveType() {
        return live_type;
    }

    public void setLiveType(String liveType) {
        this.live_type = liveType;
    }

    public String getRole() {
        return user_role;
    }

    public void setRole(String role) {
        this.user_role = role;
    }

    public String getSwipeCardTime() {
        return last_touch_time;
    }

    public void setSwipeCardTime(String swipeCardTime) {
        this.last_touch_time = swipeCardTime;
    }

    public String getPhotoPath() {
        return photo_path;
    }

    public void setPhotoPath(String photoPath) {
        this.photo_path = photoPath;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getThirdPartyCode() {
        return third_party_code;
    }

    public void setThirdPartyCode(String third_party_code) {
        this.third_party_code = third_party_code;
    }

    public void setFaceUid(String faceUid) {
        this.face_uid = faceUid;
    }

    public String getFaceUid() {
        return face_uid;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setUpdown_place_uid(String updown_place_uid) {
        this.updown_place_uid = updown_place_uid;
    }

    public String getUpdown_place_uid() {
        return updown_place_uid;
    }

    public void setUpdown_place_address(String updown_place_address) {
        this.updown_place_address = updown_place_address;
    }

    public String getUpdown_place_address() {
        return updown_place_address;
    }

    public void setUpdown_place_address_short(String updown_place_address_short) {
        this.updown_place_address_short = updown_place_address_short;
    }

    public String getUpdown_place_address_short() {
        return updown_place_address_short;
    }

    public void setUpdown_place_latitude(String updown_place_latitude) {
        this.updown_place_latitude = updown_place_latitude;
    }

    public String getUpdown_place_latitude() {
        return updown_place_latitude;
    }

    public void setUpdown_place_longitude(String updown_place_longitude) {
        this.updown_place_longitude = updown_place_longitude;
    }

    public String getUpdown_place_longitude() {
        return updown_place_longitude;
    }

}

