package com.seahung.handrollmachine.handler;

import com.seahung.handrollmachine.response.UploadRollInfoResponse;

/**
 * Created by unengchan on 2018/7/22
 */
public class UploadRollInfoHandler extends CommonHandler<UploadRollInfoResponse> {
    @Override
    protected UploadRollInfoResponse getInstance() {
        return new UploadRollInfoResponse();
    }

    @Override
    protected void parseData(String tagName, String data) {
        UploadRollInfoResponse response = getResponse();
        parseCommonXmlStr(response, tagName, data);
        switch (tagName) {
            case "user_account_uid":
                response.setUserAccountUid(data);
                break;
            case "user_name":
                response.setUserName(data);
                break;
            case "user_stu_code":
                response.setUserCode(data);
                break;
            case "user_photo_url":
                response.setUserPhotoUrl(data);
                break;
            case "user_dept_id":
                response.setUserDeptId(data);
                break;
            case "user_dept_name":
                response.setUserDeptName(data);
                break;
            case "live_type":
                response.setLiveType(data);
                break;
            case "user_role":
                response.setUserRole(data);
                break;
            case "is_need_update_user_info":
                response.setNeedUpdateUserInfo(data);
                break;

            case "seat_number":
                response.setSeat_number(data);
                break;
            case "updown_place_uid":
                response.setUpdown_place_uid(data);
                break;
            case "updown_place_address":
                response.setUpdown_place_address(data);
                break;
            case "updwon_place_address_short":
                response.setUpdown_place_address_short(data);
                break;
            case "updown_place_latitude":
                response.setUpdown_place_latitude(data);
                break;
            case "updown_place_longitude":
                response.setUpdown_place_longitude(data);
                break;
        }
    }
}
