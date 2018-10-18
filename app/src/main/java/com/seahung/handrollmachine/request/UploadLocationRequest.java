package com.seahung.handrollmachine.request;

/**
 * Created by unengchan on 2018/8/20
 * 上报定位信息的请求
 */
public class UploadLocationRequest extends BaseRequest {

    private String kqTerminalId;
    private String schoolId;
    private String gpsTime;
    private String reportTime;

    private double latitude;
    private double longitude;
    private double altitude;
    private String address;
    private float speed;
    private float bearing;

    public UploadLocationRequest(){
        setCmd("schoolbus_gps_pos_report");
        setTrans_code("schoolbus_gps_pos_report");
    }

    public String getKqTerminalId() {
        if (kqTerminalId == null) {
            kqTerminalId = "";
        }
        return kqTerminalId;
    }

    public void setKqTerminalId(String kqTerminalId) {
        this.kqTerminalId = kqTerminalId;
    }

    public String getSchoolId() {
        if (schoolId == null) {
            schoolId = "";
        }
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getGpsTime() {
        if (gpsTime == null) {
            gpsTime = "";
        }
        return gpsTime;
    }

    public void setGpsTime(String gpsTime) {
        this.gpsTime = gpsTime;
    }

    public String getReportTime() {
        if (reportTime == null) {
            reportTime = "";
        }
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public String getAddress() {
        if (address == null) {
            address = "";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    protected void addExtraXMLString(StringBuffer buffer) {
        buffer.append("<kq_terminal_id>").append(getKqTerminalId()).append("</kq_terminal_id>\n");
        buffer.append("<school_id>").append(getSchoolId()).append("</school_id>\n");
        buffer.append("<gps_time>").append(getGpsTime()).append("</gps_time>\n");
        buffer.append("<report_time>").append(getReportTime()).append("</report_time>\n");
        buffer.append("<gps_info>\n");
        buffer.append("<latitude>").append(getLatitude()).append("</latitude>\n");
        buffer.append("<longitude>").append(getLongitude()).append("</longitude>\n");
        buffer.append("<address>").append(getAddress()).append("</address>\n");
        buffer.append("<altitude>").append(getAltitude()).append("</altitude>\n");
        buffer.append("<speed>").append(getSpeed()).append("</speed>\n");
        buffer.append("<direction>").append(getBearing()).append("</direction>\n");
        buffer.append("</gps_info>\n");
    }
}
