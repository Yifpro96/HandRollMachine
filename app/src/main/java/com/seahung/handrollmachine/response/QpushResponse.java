package com.seahung.handrollmachine.response;

/**
 * Created by unengchan on 2018/7/19
 * qpush 的响应
 */
public class QpushResponse extends BaseResponse {

    private String qpush_info_type;
    private String notify_title;
    private String notify_msg_text;
    private String cmd;
    private String action;
    private String action_parameter;
    private String next_qpush_delay_seconds;

    public String getQpush_info_type() {
        return qpush_info_type;
    }

    public void setQpush_info_type(String qpush_info_type) {
        this.qpush_info_type = qpush_info_type;
    }

    public String getNotify_title() {
        return notify_title;
    }

    public void setNotify_title(String notify_title) {
        this.notify_title = notify_title;
    }

    public String getNotify_msg_text() {
        return notify_msg_text;
    }

    public void setNotify_msg_text(String notify_msg_text) {
        this.notify_msg_text = notify_msg_text;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction_parameter() {
        return action_parameter;
    }

    public void setAction_parameter(String action_parameter) {
        this.action_parameter = action_parameter;
    }

    public String getNext_qpush_delay_seconds() {
        return next_qpush_delay_seconds;
    }

    public void setNext_qpush_delay_seconds(String next_qpush_delay_seconds) {
        this.next_qpush_delay_seconds = next_qpush_delay_seconds;
    }

}
