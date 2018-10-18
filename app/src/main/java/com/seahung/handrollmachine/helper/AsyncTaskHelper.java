package com.seahung.handrollmachine.helper;


import com.seahung.handrollmachine.request.BaseRequest;
import com.seahung.handrollmachine.response.BaseResponse;
import com.seahung.handrollmachine.util.HttpUtils;
import com.seahung.handrollmachine.util.XmlHandlerUtils;
import com.unengchan.sdk.util.StringUtils;

/**
 * Created by unengchan on 2018/5/25
 * 异步任务帮助类，将通用方法抽出来
 */
public class AsyncTaskHelper {

    /**
     * 获取最终的重定向地址
     *
     * @param request
     * @return
     */
    public static String getRedirectUrl(BaseRequest request) {

        String redirectUrl = null;
        String serviceUrl = HttpUtils.getServiceUrl(request.getTrans_code(), request.getAction());
        if (StringUtils.isEmpty(serviceUrl)) {
            return redirectUrl;
        }
        String redirect_url;
        do {
            String xmlStr = HttpUtils.doPost(serviceUrl, request.toXMLString());
            BaseResponse baseResponse = XmlHandlerUtils.getBaseResponse(xmlStr);
            if (baseResponse == null) {
                return redirectUrl;
            }
            redirect_url = baseResponse.getRedirect_url();
            if (StringUtils.isNotEmpty(redirect_url)) {
                redirectUrl = redirect_url;
            } else {
                break;
            }
        } while (StringUtils.isNotEmpty(redirect_url));

        return redirectUrl;

    }

    /**
     * 判断是否存在重定向，并获取最终的响应
     *
     * @param request
     * @return
     */
    public static String getXmlResponse(BaseRequest request) {
        String xml = "";
        String serviceUrl = HttpUtils.getServiceUrl(request.getTrans_code(), request.getAction());
        if (StringUtils.isEmpty(serviceUrl)) {
            return xml;
        }
        do {
            xml = HttpUtils.doPost(serviceUrl, request.toXMLString());
            BaseResponse baseResponse = XmlHandlerUtils.getBaseResponse(xml);
            if (baseResponse == null) {
                // 如果为空，直接返回。
                return xml;
            }
            serviceUrl = baseResponse.getRedirect_url();
        } while (StringUtils.isNotEmpty(serviceUrl));
        return xml;
    }

}
