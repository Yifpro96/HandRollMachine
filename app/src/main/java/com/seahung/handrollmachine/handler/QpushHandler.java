package com.seahung.handrollmachine.handler;

import com.seahung.handrollmachine.response.QpushResponse;

/**
 * Created by unengchan on 2018/7/23
 * qpush 的handler
 */
public class QpushHandler extends CommonHandler<QpushResponse> {
    @Override
    protected QpushResponse getInstance() {
        return new QpushResponse();
    }

    @Override
    protected void parseData(String tagName, String data) {
        QpushResponse response = getResponse();
        parseCommonXmlStr(response, tagName, data);
        switch (tagName) {
            case "qpush_info_type":
                response.setQpush_info_type(data);
                break;
            case "cmd":
                response.setCmd(data);
                break;
            case "action":
                response.setAction(data);
                break;
            case "action_parameter":
                response.setAction_parameter(data);
                break;
            case "notify_title":
                response.setNotify_title(data);
                break;
            case "notify_msg_text":
                response.setNotify_msg_text(data);
                break;
            case "next_qpush_delay_seconds":
                response.setNext_qpush_delay_seconds(data);
                break;
        }
    }
}
