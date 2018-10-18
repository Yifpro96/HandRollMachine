package com.seahung.handrollmachine.request;

import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.manager.ConfigManager;
import com.unengchan.sdk.util.AppUtils;
import com.unengchan.sdk.util.StringUtils;

/**
 * Created by unengchan on 2018/5/4
 * 请求基类，设置请求的必要参数
 */
public class BaseRequest {

    protected String tag = this.getClass().getSimpleName();

    private String cmd;
    private String action;
    private String trans_code;
    private String yc_user_account_uid;
    private String yc_user_role;
    private String yc_school_id;
    private String yc_dept_id;
    private String from_system;
    private String from_client_id;
    private String from_client_version;
    private String from_client_desc;

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setTrans_code(String trans_code) {
        this.trans_code = trans_code;
    }

    public void setYc_user_account_uid(String yc_user_account_uid) {
        this.yc_user_account_uid = yc_user_account_uid;
    }

    public void setYc_user_role(String yc_user_role) {
        this.yc_user_role = yc_user_role;
    }

    public String getCmd() {
        if (cmd == null) {
            cmd = "";
        }
        return cmd;
    }

    public String getAction() {
        if (action == null) {
            action = "";
        }
        return action;
    }

    public String getTrans_code() {
        if (trans_code == null) {
            trans_code = "";
        }
        return trans_code;
    }

    public String getYc_user_account_uid() {
        if (StringUtils.isEmpty(yc_user_account_uid)) {
            yc_user_account_uid = "";
        }
        return yc_user_account_uid;
    }

    public String getYc_user_role() {
        if (yc_user_role == null) {
            yc_user_role = ConfigManager.getInstance().getUserType();
        }
        return yc_user_role;
    }

    public String getYc_school_id() {
        if (StringUtils.isEmpty(yc_school_id)) {
            yc_school_id = ConfigManager.getInstance().getSchoolId();
        }
        return yc_school_id;
    }

    public String getYc_dept_id() {
        if (yc_dept_id == null) {
            yc_dept_id = "";
        }
        return yc_dept_id;
    }

    public String getFrom_system() {
        if (StringUtils.isEmpty(from_system)) {
            from_system = AppUtils.getAppContext().getString(R.string.from_system);
        }
        return from_system;
    }

    public String getFrom_client_id() {
        if (StringUtils.isEmpty(from_client_id)) {
            from_client_id = ConfigManager.getInstance().getTerminalId();
        }
        return from_client_id;
    }

    public String getFrom_client_version() {
        if (StringUtils.isEmpty(from_client_version)) {
            from_client_version = AppUtils.getVersionName();
        }
        return from_client_version;
    }

    public String getFrom_client_desc() {
        if (StringUtils.isEmpty(from_client_desc)) {
            from_client_desc = AppUtils.getPackageName() + "_hd";
        }
        return from_client_desc;
    }

    /**
     * 由子类重写
     *
     * @param buffer
     */
    protected void addExtraXMLString(StringBuffer buffer) {

    }

    /**
     * 生成xml请求数据包
     *
     * @return
     */
    public String toXMLString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        buffer.append("<infobus>\n");
        buffer.append("<cmd>").append(getCmd()).append("</cmd>\n");
        buffer.append("<action>").append(getAction()).append("</action>\n");
        buffer.append("<trans_code>").append(getTrans_code()).append("</trans_code>\n");
        buffer.append("<yc_user_account_uid>").append(getYc_user_account_uid()).append("</yc_user_account_uid>\n");
        buffer.append("<yc_user_role>").append(getYc_user_role()).append("</yc_user_role>\n");
        buffer.append("<yc_school_id>").append(getYc_school_id()).append("</yc_school_id>\n");
        buffer.append("<yc_dept_id>").append(getYc_dept_id()).append("</yc_dept_id>\n");
        buffer.append("<from_system>").append(getFrom_system()).append("</from_system>\n");
        buffer.append("<from_client_id>").append(getFrom_client_id()).append("</from_client_id>\n");
        buffer.append("<from_client_version>").append(getFrom_client_version()).append("</from_client_version>\n");
        buffer.append("<from_client_desc>").append(getFrom_client_desc()).append("</from_client_desc>\n");
        // 添加子类重写的xml数据
        addExtraXMLString(buffer);
        buffer.append("</infobus>");
        String xmlStr = buffer.toString().replace("&", "&amp;");
//        LogUtils.d(tag,xmlStr);
        return xmlStr;
    }

}
