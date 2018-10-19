package com.seahung.handrollmachine.helper;

import com.seahung.handrollmachine.bean.database.SwipeCard;
import com.seahung.handrollmachine.bean.database.User;
import com.seahung.handrollmachine.constant.FileConstant;
import com.seahung.handrollmachine.response.UploadRollInfoResponse;
import com.unengchan.sdk.util.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by unengchan on 2018/6/8
 * 数据库帮助类
 */
public class RealmHelper {

    // 用户数据库配置
    private static final String USER_DATABASE_NAME = "user_info.realm";
    private static RealmConfiguration userInfoRealmConfig = new RealmConfiguration
            .Builder()
            .deleteRealmIfMigrationNeeded()
            .name(USER_DATABASE_NAME).build();

    // 刷卡数据库配置
    private static final String SWIPE_CARD_NAME = "swipe_card_info.realm";
    private static RealmConfiguration swipeCardInfoRealmConfig = new RealmConfiguration
            .Builder()
            .deleteRealmIfMigrationNeeded()
            .name(SWIPE_CARD_NAME).build();

    /**
     * 获取用户信息的数据库
     *
     * @return
     */
    public static Realm getUserInfoRealm() {
        return Realm.getInstance(userInfoRealmConfig);
    }

    /**
     * 获取所有用户
     *
     * @param realm
     */
    public static HashMap<String, User> getAllUsersHashMap(Realm realm) {

        HashMap<String, User> map = new HashMap<>();
        RealmResults<User> users = realm.where(User.class).findAll();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            map.put(user.getAccountUid(), user);
        }
        return map;
    }

    /**
     * 获取所有用户
     *
     * @param realm
     */
    public static List<User> getAllUsersList(Realm realm) {

        RealmResults<User> users = realm.where(User.class).findAll();
        return users;
    }

    /**
     * 获取face_id的最大值，下一个人的人脸id在这个基础上加一
     *
     * @param realm
     * @return
     */
    public static int getUserFaceIdMax(Realm realm) {
        Number number = realm.where(User.class).max("face_id");
        if (number == null) {
            return 0;
        }
        int faceId = number.intValue();
        return faceId;
    }

    /**
     * 获取刷卡信息的数据库
     *
     * @return
     */
    public static Realm getSwipeCardInfoRealm() {
        return Realm.getInstance(swipeCardInfoRealmConfig);
    }

    /**
     * 查找用户，通过uid
     *
     * @param realm
     * @param accountUid
     * @return
     */
    public static User getUserByUid(Realm realm, String accountUid) {
        User user = realm.where(User.class).equalTo("account_uid", accountUid).findFirst();
        return user;
    }

    /**
     * 通过face_uid 查找用户
     *
     * @param realm
     * @param faceUid
     * @return
     */
    public static User getUserByFaceUid(Realm realm, String faceUid) {
        User user = realm.where(User.class).equalTo("face_uid", faceUid).findFirst();
        return user;
    }

    /**
     * 通过card_id 查找用户
     *
     * @param realm
     * @param cardId
     * @return
     */
    public static User getUserByCardId(Realm realm, String cardId) {
        User user = realm.where(User.class).equalTo("card_id", cardId).findFirst();
        return user;
    }

    /**
     * 插入或更新用户数据
     *
     * @param realm
     * @param response
     */
    public static void inserOrUpdateUser(Realm realm, UploadRollInfoResponse response) {

        // 如果数据有更新，后台需要当前用户的全量数据进行更新
        String accountUid = response.getUserAccountUid();
        if (StringUtils.isEmpty(accountUid)) {
            return;
        }
        User user = new User();
        user.setAccountUid(accountUid);
        user.setCardId(response.getCardId());
        user.setName(response.getUserName());
        user.setDeptId(response.getUserDeptId());
        user.setDeptName(response.getUserDeptName());
        user.setPhotoUrl(response.getUserPhotoUrl());
        user.setCode(response.getUserCode());
        user.setLiveType(response.getLiveType());
        user.setRole(response.getUserRole());
        user.setSwipeCardTime(response.getTouchTime());

        // 头像路径
        String userPhotoPath = FileConstant.USER_PHOTO_DIR + File.separator + response.getUserAccountUid() + ".jpg";
        user.setPhotoPath(userPhotoPath);
        // 人脸id
        String faceUid = accountUid.replaceAll("-", "");
        user.setFaceUid(faceUid);

        // 乘坐信息
        user.setSeat_number(response.getSeat_number());
        user.setUpdown_place_uid(response.getUpdown_place_uid());
        user.setUpdown_place_address(response.getUpdown_place_address());
        user.setUpdown_place_address_short(response.getUpdown_place_address_short());
        user.setUpdown_place_latitude(response.getUpdown_place_latitude());
        user.setUpdown_place_longitude(response.getUpdown_place_longitude());

        // 插入数据
        realm.beginTransaction();
        realm.insertOrUpdate(user);
        realm.commitTransaction();
    }

    /**
     * 添加刷卡数据
     *
     * @param realm
     * @param swipeCard
     */
    public static void addSwipeCardData(Realm realm, SwipeCard swipeCard) {

        realm.beginTransaction();
        realm.insert(swipeCard);
        realm.commitTransaction();

//        RealmAsyncTask transactionAsync = realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                SwipeCard swipeCard = realm.createObject(SwipeCard.class);
//                swipeCard.setCardId(cardId);
//                swipeCard.setFaceUid(faceUid);
//                swipeCard.setTouchTime(touchTime);
//                swipeCard.setPhotoPath(photoPath);
//                swipeCard.setIsUpload("N");
//            }
//        });
    }

    /**
     * 清理刷卡数据
     */
    public static void clearSwipeCardData() {
        Realm swipeCardInfoRealm = RealmHelper.getSwipeCardInfoRealm();
        try {
            swipeCardInfoRealm.beginTransaction();
            swipeCardInfoRealm.delete(SwipeCard.class);
            swipeCardInfoRealm.commitTransaction();
        } finally {
            swipeCardInfoRealm.close();
        }
    }

    /**
     * 通过faceuid获取刷卡的人数，也就是获取不同卡号的数量
     *
     * @param realm
     * @return
     */
    public static int getPeopleCountByFaceUid(Realm realm) {
        RealmResults<SwipeCard> all = realm.where(SwipeCard.class).distinct("face_uid").findAll();
        return all.size();
    }

    /**
     * 通过faceuid获取刷卡的人数，也就是获取不同卡号的数量
     *
     * @param realm
     * @return
     */
    public static int getPeopleCountByCardId(Realm realm) {
        RealmResults<SwipeCard> all = realm.where(SwipeCard.class).distinct("card_id").findAll();
        return all.size();
    }

    /**
     * 获取刷卡的总次数
     *
     * @param realm
     * @return
     */
    public static int getSwipeCardSum(Realm realm) {
        long count = realm.where(SwipeCard.class).count();
        return (int) count;
    }

    /**
     * 获取未上传的数量
     *
     * @param realm
     * @return
     */
    public static int getNotUploadCount(Realm realm) {
        long count = realm.where(SwipeCard.class).equalTo("is_upload", "N").count();
        return (int) count;
    }

    /**
     * 查找当前卡号的刷卡次数通过人脸id
     *
     * @param realm
     * @param faceUid
     * @return
     */
    public static int getSwipFaceCountForFaceUid(Realm realm, String faceUid) {
        long count = realm.where(SwipeCard.class).equalTo("face_uid", faceUid).count();
        return (int) count;
    }

    /**
     * 查找当前卡号的刷卡次数
     *
     * @param realm
     * @param cardId
     * @return
     */
    public static int getSwipCardCountForCardId(Realm realm, String cardId) {
        long count = realm.where(SwipeCard.class).equalTo("card_id", cardId).count();
        return (int) count;
    }


    /**
     * 获取未上传的数据
     *
     * @param realm
     */
    public static SwipeCard getNotUploadLastData(Realm realm) {
        SwipeCard swipeCard = realm.where(SwipeCard.class).equalTo("is_upload", "N")
                .sort("touch_time", Sort.DESCENDING).findFirst();
        return swipeCard;
    }

    /**
     * 查找当前卡号数据
     *
     * @param realm
     * @param cardId
     * @return
     */
    public static SwipeCard getSwipCardById(Realm realm, String cardId) {
        SwipeCard card = realm.where(SwipeCard.class).equalTo("card_id", cardId).findFirst();
        return card;
    }

    /**
     * 查找当前上车的总人数
     *
     * @param realm
     * @return
     */
    public static int  getSwipCardUpCount(Realm realm) {
        int count= (int) realm.where(SwipeCard.class).equalTo("updown_direction", "up").distinct("card_id").count();
        return count;
    }

}
