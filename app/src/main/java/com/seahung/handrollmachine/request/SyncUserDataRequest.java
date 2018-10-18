package com.seahung.handrollmachine.request;

/**
 * Created by unengchan on 2018/7/17
 * 同步用户数据的请求
 */
public class SyncUserDataRequest extends BaseRequest {

    private String school_id;
    private String terminal_id;
    private String user_type;

    public SyncUserDataRequest(){
        setCmd("schoolbus_rollm_sync_user_info");
        setTrans_code("schoolbus_rollm_sync_user_info");

//        setCmd("rollm_sync_user_info");
//        setTrans_code("rollm_sync_user_info");
    }

    public String getSchool_id() {
        if (school_id == null) {
            school_id = "";
        }
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getTerminal_id() {
        if (terminal_id == null) {
            terminal_id = "";
        }
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getUser_type() {
        if (user_type == null) {
            user_type = "";
        }
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    @Override
    protected void addExtraXMLString(StringBuffer buffer) {
        buffer.append("<school_id>").append(getSchool_id()).append("</school_id>\n");
        buffer.append("<kq_terminal_id>").append(getTerminal_id()).append("</kq_terminal_id>\n");
        buffer.append("<tea_stu_rollm_type>").append(getUser_type()).append("</tea_stu_rollm_type>\n");
    }
}
