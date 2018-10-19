package com.seahung.handrollmachine.bean;

// FIXME generate failure  field _$All_seat_distr_status64

import java.util.List;

/**
 * Created by unengchan on 2018/9/26
 * 网页数据
 */
public class SchoolbusData {


    /**
     * seat_count  : 30
     * on_car_stu_count  : 27
     * seat_row : 10
     * seat_column : 3
     * all_seat_distr_status  : [{"seat_number":"01","seat_status":"0","user_name":"马云","user_photo_url":"http://xxx/xxx.jpg","updown_place_uid":"aaa-bbb","updown_place_address":"天河体育中心","updown_place_address_short":"天河体育中心","updown_place_latitude":"23.232431","updown_place_longitude":"133.4646546"}]
     */

    private String seat_count;
    private int on_car_stu_count;
    private String seat_row;
    private String seat_column;
    private List<SeatData> allSeatDistrStatus;

    public String getSeat_count() {
        return seat_count;
    }

    public void setSeat_count(String seat_count) {
        this.seat_count = seat_count;
    }

    public int getOn_car_stu_count() {
        return on_car_stu_count;
    }

    public void setOn_car_stu_count(int on_car_stu_count) {
        this.on_car_stu_count = on_car_stu_count;
    }

    public String getSeat_row() {
        return seat_row;
    }

    public void setSeat_row(String seat_row) {
        this.seat_row = seat_row;
    }

    public String getSeat_column() {
        return seat_column;
    }

    public void setSeat_column(String seat_column) {
        this.seat_column = seat_column;
    }

    public List<SeatData> getAllSeatDistrStatus() {
        return allSeatDistrStatus;
    }

    public void setAllSeatDistrStatus(List<SeatData> allSeatDistrStatus) {
        this.allSeatDistrStatus = allSeatDistrStatus;
    }

    public static class SeatData {
        /**
         * seat_number : 01
         * seat_status : 0
         * user_name : 马云
         * user_photo_url : http://xxx/xxx.jpg
         * updown_place_uid : aaa-bbb
         * updown_place_address : 天河体育中心
         * updown_place_address_short : 天河体育中心
         * updown_place_latitude : 23.232431
         * updown_place_longitude : 133.4646546
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
