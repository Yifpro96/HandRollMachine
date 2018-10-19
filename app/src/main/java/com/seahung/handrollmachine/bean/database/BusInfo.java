package com.seahung.handrollmachine.bean.database;

/**
 * Created by jo on 2018/10/18.
 */

/**
 * 校车信息
 */
public class BusInfo {
    private int seat_count;//座椅总数
    private int on_car_stu_count;//已上车人数
    private int seat_row;//座位行数
    private int seat_column;//座位列数

    public int getSeat_count() {
        return seat_count;
    }

    public void setSeat_count(int seat_count) {
        this.seat_count = seat_count;
    }

    public int getOn_car_stu_count() {
        return on_car_stu_count;
    }

    public void setOn_car_stu_count(int on_car_stu_count) {
        this.on_car_stu_count = on_car_stu_count;
    }

    public int getSeat_row() {
        return seat_row;
    }

    public void setSeat_row(int seat_row) {
        this.seat_row = seat_row;
    }

    public int getSeat_column() {
        return seat_column;
    }

    public void setSeat_column(int seat_column) {
        this.seat_column = seat_column;
    }
}
