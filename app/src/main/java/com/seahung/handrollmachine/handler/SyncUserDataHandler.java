package com.seahung.handrollmachine.handler;

import com.seahung.handrollmachine.response.SyncUserDataResponse;

/**
 * Created by unengchan on 2018/7/17
 * 解析同步数据的回复
 */
public class SyncUserDataHandler extends CommonHandler<SyncUserDataResponse> {
    @Override
    protected SyncUserDataResponse getInstance() {
        return new SyncUserDataResponse();
    }

    @Override
    protected void parseData(String tagName, String data) {
        SyncUserDataResponse response = getResponse();
        parseCommonXmlStr(response, tagName, data);
        switch (tagName) {
            case "user_csv_url":
                response.setUser_csv_url(data);
                break;
            case "seat_row":
                response.setSeat_row(data);
                break;
            case "seat_column":
                response.setSeat_column(data);
                break;
            case "seat_distr_diagram":
                response.setSeat_distr_diagram(data);
                break;
            case "seat_all_number":
                response.setSeat_all_number(data);
                break;
            case "seat_count":
                response.setSeat_count(data);
                break;
        }
    }
}
