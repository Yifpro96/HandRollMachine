package com.seahung.handrollmachine.handler;


import com.seahung.handrollmachine.response.ServiceUrlResponse;

/**
 * Created by unengchan on 2018/5/14
 */
public class ServiceUrlHandler extends CommonHandler<ServiceUrlResponse> {
    @Override
    protected ServiceUrlResponse getInstance() {
        return new ServiceUrlResponse();
    }

    @Override
    protected void parseData(String tagName, String data) {
        ServiceUrlResponse response = getResponse();
        parseCommonXmlStr(response,tagName, data);
        if ("dns.url".equals(tagName)) {
            response.setServiceUrl(data);
        }
    }
}
