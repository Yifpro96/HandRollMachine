package com.seahung.handrollmachine.request;

/**
 * Created by unengchan on 2018/7/19
 * qpush 的请求
 */
public class QpushRequest extends BaseRequest {

    private String kqTerminalId;

    public QpushRequest() {
        setCmd("cplate_has_qpush");
        setTrans_code("cplate_has_qpush");
    }

    public String getKqTerminalId() {
        return kqTerminalId;
    }

    public void setKqTerminalId(String kqTerminalId) {
        this.kqTerminalId = kqTerminalId;
    }

    @Override
    protected void addExtraXMLString(StringBuffer buffer) {
        buffer.append("<kq_terminal_id>").append(getKqTerminalId()).append("</kq_terminal_id>\n");
    }
}
