package com.seahung.handrollmachine.helper;


import com.csvreader.CsvReader;
import com.seahung.handrollmachine.bean.database.User;
import com.seahung.handrollmachine.constant.FileConstant;
import com.unengchan.sdk.util.LogUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by unengchan on 2018/6/11
 * csv文件解析类
 */
public class CsvHelper {

    private static final String TAG = CsvHelper.class.getSimpleName();

    /**
     * 获取用户数据
     *
     * @param filePath
     * @return
     */
    public static List<User> getUserData(String filePath) {
        List<User> users = null;
        try {

            // 用户头像文件夹
            String userPhotoDir = FileConstant.USER_PHOTO_DIR;
            File photoDir = new File(userPhotoDir);
            if (!photoDir.exists()) {
                photoDir.mkdirs();
            }

            CsvReader csvReader = new CsvReader(filePath, ',', Charset.forName("GBK"));
            csvReader.readHeaders();
            users = new ArrayList<>();
            while (csvReader.readRecord()) {
                User user = new User();
                String accountUid = csvReader.get("user_account_uid");
                user.setAccountUid(accountUid);
                user.setName(csvReader.get("user_name"));
                user.setCardId(csvReader.get("card_id"));
                user.setCode(csvReader.get("user_stu_code"));
                user.setPhotoUrl(csvReader.get("user_photo_url"));
                user.setDeptId(csvReader.get("user_dept_id"));
                user.setDeptName(csvReader.get("user_dept_name"));
                user.setLiveType(csvReader.get("live_type"));
                user.setRole(csvReader.get("user_role"));
                user.setPhone(csvReader.get("user_phone"));
                user.setThirdPartyCode(csvReader.get("third_party_code"));
                // 用户头像的保存路径
                String userPhotoPath = userPhotoDir + File.separator + accountUid + ".jpg";
                user.setPhotoPath(userPhotoPath);
                user.setFaceUid(accountUid.replaceAll("-",""));

                // 校车相关信息
                user.setSeat_number(csvReader.get("seat_number"));
                user.setUpdown_place_uid(csvReader.get("updown_place_uid"));
                user.setUpdown_place_address(csvReader.get("updown_place_address"));
                user.setUpdown_place_address_short(csvReader.get("updown_place_address_short"));
                user.setUpdown_place_latitude(csvReader.get("updown_place_latitude"));
                user.setUpdown_place_longitude(csvReader.get("updown_place_longitude"));

                users.add(user);
            }
        } catch (Exception e) {
            LogUtils.d(TAG + "----getUserData", e.getMessage());
        }
        return users;
    }

}
