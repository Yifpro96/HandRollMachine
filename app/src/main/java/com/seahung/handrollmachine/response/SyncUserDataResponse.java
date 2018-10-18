package com.seahung.handrollmachine.response;

/**
 * Created by unengchan on 2018/7/17
 * 同步用户数据回复
 */
public class SyncUserDataResponse extends BaseResponse {

    private String seat_row;
    private String seat_column;
    private String seat_distr_diagram;
    private String seat_all_number;
    private String seat_count;
    private String user_csv_url;

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

    public String getSeat_distr_diagram() {
        return seat_distr_diagram;
    }

    public void setSeat_distr_diagram(String seat_distr_diagram) {
        this.seat_distr_diagram = seat_distr_diagram;
    }

    public String getSeat_all_number() {
        return seat_all_number;
    }

    public void setSeat_all_number(String seat_all_number) {
        this.seat_all_number = seat_all_number;
    }

    public String getSeat_count() {
        return seat_count;
    }

    public void setSeat_count(String seat_count) {
        this.seat_count = seat_count;
    }

    public void setUser_csv_url(String user_csv_url) {
        this.user_csv_url = user_csv_url;
    }

    public String getUser_csv_url() {
        return user_csv_url;
    }
}
