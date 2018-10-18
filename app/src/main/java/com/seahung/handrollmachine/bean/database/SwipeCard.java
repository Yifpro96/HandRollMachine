package com.seahung.handrollmachine.bean.database;

import io.realm.RealmObject;

/**
 * Created by unengchan on 2018/7/17
 * 刷脸
 */
public class SwipeCard extends RealmObject {

    private String card_id;
    private String face_uid;
    private String touch_time;
    private String is_upload;
    private String photo_path;

    // gps定位数据
    private double latitude;
    private double longitude;
    private double altitude;
    private String address;
    private float speed;
    private float bearing;
    private String updown_direction;
    private String gps_time;

    public String getCardId() {
        return card_id;
    }

    public void setCardId(String cardId) {
        this.card_id = cardId;
    }

    public String getTouchTime() {
        return touch_time;
    }

    public void setTouchTime(String touch_time) {
        this.touch_time = touch_time;
    }

    public String getIsUpload() {
        return is_upload;
    }

    public void setIsUpload(String is_upload) {
        this.is_upload = is_upload;
    }

    public String getPhotoPath() {
        return photo_path;
    }

    public void setPhotoPath(String photo_path) {
        this.photo_path = photo_path;
    }

    public void setFaceUid(String faceUid) {
        this.face_uid = faceUid;
    }

    public String getFaceUid() {
        return face_uid;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getUpdownDirection() {
        return updown_direction;
    }

    public void setUpdownDirection(String updown_direction) {
        this.updown_direction = updown_direction;
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

    public String getGpsTime() {
        return gps_time;
    }

    public void setGpsTime(String gps_time) {
        this.gps_time = gps_time;
    }

    /**
     * 卡号和刷卡时间相同那么就相同
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SwipeCard) {
            SwipeCard card = (SwipeCard) obj;
            if (this.card_id.equals(card.card_id) &&
                    this.face_uid.equals(card.face_uid) &&
                    this.touch_time.equals(card.touch_time)) {
                return true;
            }
        }
        return false;
    }
}
