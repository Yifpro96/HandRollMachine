package com.seahung.handrollmachine.request;

import com.seahung.handrollmachine.util.DeviceUtils;
import com.unengchan.sdk.util.AppUtils;
import com.unengchan.sdk.util.SPUtils;

/**
 * Created by unengchan on 2018/5/10
 * 升级app的请求
 */
public class UpdateAppRequest extends BaseRequest {

    public UpdateAppRequest() {
        setCmd("app_startup");
        setTrans_code("app_startup");
    }

    @Override
    protected void addExtraXMLString(StringBuffer buffer) {
        String newTerminal = (String) SPUtils.getValue("is_new_terminal",  "Y");
        if ("Y".equals(newTerminal)) {
            // 首次安装是新终端
            SPUtils.putValue("is_new_terminal", "N");
        }
        buffer.append("<is_new_terminal>").append(newTerminal).append("</is_new_terminal>\n");
        buffer.append("<terminal>\n");
        buffer.append("<mac>").append(DeviceUtils.getMacAddress()).append("</mac>\n");
        buffer.append("<imei>").append(DeviceUtils.getImei()).append("</imei>\n");
        buffer.append("<imsi>").append(DeviceUtils.getImsi()).append("</imsi>\n");
        buffer.append("<os>Android</os>\n");
        buffer.append("<os_version>").append(DeviceUtils.getOsVersion()).append("</os_version>\n");
        buffer.append("<manu>").append(DeviceUtils.getManufacturer()).append("</manu>\n");
        buffer.append("<model>").append(DeviceUtils.getModel()).append("</model>\n");
        buffer.append("<resolution>").append(DeviceUtils.getResolution()).append("</resolution>\n");
        buffer.append("<app_version>").append(AppUtils.getVersionName()).append("</app_version>\n");
        buffer.append("<battery_level>" + "unknown" + "</battery_level>\n");
        buffer.append("<battery_in_charging>" + "N" + "</battery_in_charging>\n");
        buffer.append("</terminal>\n");

        buffer.append("<net>\n");
        buffer.append("<mobile_operator>").append(DeviceUtils.getOperatorName()).append("</mobile_operator>\n");
        buffer.append("<mobile_standard>").append(DeviceUtils.getPhoneType()).append("</mobile_standard>\n");
        buffer.append("<mobile_network>").append(DeviceUtils.getNetworkType()).append("</mobile_network>\n");
        buffer.append("<mobile_generation>").append(DeviceUtils.getGeneration()).append("</mobile_generation>\n");
        buffer.append("<current_apn>").append(DeviceUtils.getApn()).append("</current_apn>\n");
        buffer.append("<is_roam>").append(DeviceUtils.getRoamStatus()).append("</is_roam>\n");
        buffer.append("<current_using_wifi>").append(DeviceUtils.getWifiStatus()).append("</current_using_wifi>\n");
        buffer.append("</net>\n");
    }
}
