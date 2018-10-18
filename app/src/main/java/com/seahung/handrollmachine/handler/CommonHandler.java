package com.seahung.handrollmachine.handler;


import com.seahung.handrollmachine.response.BaseResponse;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by unengchan on 2018/5/11
 * 通用xml解析的handler
 */
public abstract class CommonHandler<T> extends DefaultHandler {

    protected String mTagName;
    private T mResponse;

    /**
     * 保证获取的对象不为空
     *
     * @return T 对象
     */
    public T getResponse() {
        if (mResponse == null) {
            mResponse = getInstance();
        }
        return mResponse;
    }

    protected abstract T getInstance();

    protected abstract void parseData(String tagName, String data);

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        mTagName = localName;
        if ("infobus".equals(localName)) {
            mResponse = getInstance();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        mTagName = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (mTagName == null) {
            return;
        }
        String data = new String(ch, start, length);
        parseData(mTagName, data);
    }

    /**
     * 解析基类的通用数据
     *
     * @param response
     */
    protected void parseCommonXmlStr(BaseResponse response, String tagName, String data) {
        switch (tagName) {
            case "error_code":
                response.setError_code(data);
                break;
            case "error_string":
                response.setError_string(data);
                break;
            case "yc_redirect_request_to":
                response.setRedirect_url(data);
                break;
            case "pop_message":
                response.setPop_message(data);
                break;
            default:
                break;
        }
    }
}

