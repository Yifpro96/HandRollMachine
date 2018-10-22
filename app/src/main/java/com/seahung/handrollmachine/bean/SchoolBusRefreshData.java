package com.seahung.handrollmachine.bean;

import java.util.List;

/**
 * Created by jo on 2018/10/19.
 */

public class SchoolBusRefreshData {

    /**
     *  current_addrese  : 越秀东山口
     *  current_latitude  : 23.356431
     *  current_longitude  : 133.4567765
     * all_seat_id_status : [{"seat_number":"02","seat_status":"1","user_name":"马化腾","user_photo_url":"http://xxx/xxx.jpg","updown_place_uid":"bbb-ccc","updown_place_address":"越秀东山口","updown_place_address_short":"越秀东山口","updown_place_latitude":"23.356431","updown_place_longitude":"133.4567765"},{"seat_number":"03","seat_status":"2","user_name":"郑丽丽","user_photo_url":"http://xxx/xxx.jpg","updown_place_uid":"bbb-ccc","updown_place_address":"越秀东山口","updown_place_address_short":"越秀东山口","updown_place_latitude":"23.356431","updown_place_longitude":"133.4567765"},"...."]
     */

    private String current_addrese;
    private Double current_latitude;
    private Double current_longitude;
    private java.util.List<AllSeatIdStatusBean> all_seat_id_status;

    public String getCurrent_addrese() {
        return current_addrese;
    }

    public void setCurrent_addrese(String current_addrese) {
        this.current_addrese = current_addrese;
    }

    public Double getCurrent_latitude() {
        return current_latitude;
    }

    public void setCurrent_latitude(Double current_latitude) {
        this.current_latitude = current_latitude;
    }

    public Double getCurrent_longitude() {
        return current_longitude;
    }

    public void setCurrent_longitude(Double current_longitude) {
        this.current_longitude = current_longitude;
    }

    public List<AllSeatIdStatusBean> getAll_seat_id_status() {
        return all_seat_id_status;
    }

    public void setAll_seat_id_status(List<AllSeatIdStatusBean> all_seat_id_status) {
        this.all_seat_id_status = all_seat_id_status;
    }

    public static class AllSeatIdStatusBean {
        /**
         * seat_number : 02
         * seat_status : 1
         * user_name : 马化腾
         * user_photo_url : http://xxx/xxx.jpg
         * updown_place_uid : bbb-ccc
         * updown_place_address : 越秀东山口
         * updown_place_address_short : 越秀东山口
         * updown_place_latitude : 23.356431
         * updown_place_longitude : 133.4567765
         */

        private String seat_number;
        private String seat_status;
        private String user_name;
        private String user_photo_url;
        private String updown_place_uid;
        private String updown_place_address;
        private String updown_place_address_short;
        private String updown_place_latitude;
        private String updown_place_longitude;

        public String getSeat_number() {
            return seat_number;
        }

        public void setSeat_number(String seat_number) {
            this.seat_number = seat_number;
        }

        public String getSeat_status() {
            return seat_status;
        }

        public void setSeat_status(String seat_status) {
            this.seat_status = seat_status;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_photo_url() {
            return user_photo_url;
        }

        public void setUser_photo_url(String user_photo_url) {
            this.user_photo_url = user_photo_url;
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
}
