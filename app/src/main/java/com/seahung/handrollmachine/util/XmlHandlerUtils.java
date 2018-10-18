package com.seahung.handrollmachine.util;


import com.seahung.handrollmachine.handler.BaseHandler;
import com.seahung.handrollmachine.handler.CommonHandler;
import com.seahung.handrollmachine.handler.LoginSettingHandler;
import com.seahung.handrollmachine.handler.QpushHandler;
import com.seahung.handrollmachine.handler.ServiceUrlHandler;
import com.seahung.handrollmachine.handler.SyncUserDataHandler;
import com.seahung.handrollmachine.handler.UpdateAppHandler;
import com.seahung.handrollmachine.handler.UploadFileHandler;
import com.seahung.handrollmachine.handler.UploadLocationHandler;
import com.seahung.handrollmachine.handler.UploadRollInfoHandler;
import com.seahung.handrollmachine.response.BaseResponse;
import com.seahung.handrollmachine.response.LoginSettingResponse;
import com.seahung.handrollmachine.response.QpushResponse;
import com.seahung.handrollmachine.response.ServiceUrlResponse;
import com.seahung.handrollmachine.response.SyncUserDataResponse;
import com.seahung.handrollmachine.response.UpdateAppResponse;
import com.seahung.handrollmachine.response.UploadFileResponse;
import com.seahung.handrollmachine.response.UploadLocationResponse;
import com.seahung.handrollmachine.response.UploadRollInfoResponse;
import com.unengchan.sdk.util.IOUtils;
import com.unengchan.sdk.util.LogUtils;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by unengchan on 2018/5/11
 * 解析xml
 */
public class XmlHandlerUtils {

    /**
     * 获取基础响应数据
     *
     * @param xmlStr xml字符串
     * @return BaseResponse
     */
    public static BaseResponse getBaseResponse(String xmlStr) {
        BaseHandler handler = new BaseHandler();
        parseXmlStr(xmlStr, handler);
        return handler.getResponse();
    }

    /**
     * 获取服务器地址
     *
     * @param xmlStr
     * @return
     */
    public static ServiceUrlResponse getServiceUrlResponse(String xmlStr) {
        ServiceUrlHandler handler = new ServiceUrlHandler();
        parseXmlStr(xmlStr, handler);
        return handler.getResponse();
    }

    /**
     * 获取升级app的信息
     *
     * @param xmlStr
     * @return
     */
    public static UpdateAppResponse getUpdateAppResponse(String xmlStr) {
        UpdateAppHandler handler = new UpdateAppHandler();
        parseXmlStr(xmlStr, handler);
        return handler.getResponse();
    }

    /**
     * 获取登录的回复
     *
     * @param xmlStr
     * @return
     */
    public static LoginSettingResponse getLoginSettingResponse(String xmlStr) {
        LoginSettingHandler handler = new LoginSettingHandler();
        parseXmlStr(xmlStr, handler);
        return handler.getResponse();
    }

    /**
     * 获取同步数据的回复
     *
     * @param xmlStr
     * @return
     */
    public static SyncUserDataResponse getSyncUserDataResponse(String xmlStr) {
        SyncUserDataHandler handler = new SyncUserDataHandler();
        parseXmlStr(xmlStr, handler);
        return handler.getResponse();
    }

    /**
     * 获取上传考勤信息的回复
     *
     * @param xmlStr
     * @return
     */
    public static UploadRollInfoResponse getUploadRollInfoResponse(String xmlStr) {
        UploadRollInfoHandler handler = new UploadRollInfoHandler();
        parseXmlStr(xmlStr, handler);
        return handler.getResponse();
    }

    /**
     * 获取上传文件的回执的回复
     *
     * @param xmlStr
     * @return
     */
    public static UploadFileResponse getUploadFileResponse(String xmlStr) {
        UploadFileHandler handler = new UploadFileHandler();
        parseXmlStr(xmlStr, handler);
        return handler.getResponse();
    }

    /**
     * 获取qpush的响应
     *
     * @param xmlStr
     * @return
     */
    public static QpushResponse getQpushResponse(String xmlStr) {
        QpushHandler handler = new QpushHandler();
        parseXmlStr(xmlStr, handler);
        return handler.getResponse();
    }

    /**
     * 获取上传定位的响应
     *
     * @param xmlStr    xml字符串
     * @return
     */
    public static UploadLocationResponse getUploadLocationResponse(String xmlStr) {
        UploadLocationHandler handler = new UploadLocationHandler();
        parseXmlStr(xmlStr, handler);
        return handler.getResponse();
    }

    /**
     * 解析xml数据
     *
     * @param xmlStr  xml字符串
     * @param handler handler
     */
    private static void parseXmlStr(String xmlStr, CommonHandler handler) {
        if (xmlStr == null || xmlStr.length() == 0) {
            return;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(xmlStr.getBytes());
        BufferedInputStream bis = new BufferedInputStream(bais);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(bis));
        } catch (Exception e) {
            // 解析异常
            LogUtils.d(XmlHandlerUtils.class.getSimpleName() + "---" + handler.getClass().getSimpleName(), e.getMessage());
        } finally {
            // 关闭操作流
            IOUtils.close(bais);
            IOUtils.close(bis);
        }
    }

}
