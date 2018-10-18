package com.seahung.handrollmachine.helper;

import android.speech.tts.TextToSpeech;

import com.seahung.handrollmachine.constant.ConfigConstant;
import com.seahung.handrollmachine.manager.ConfigManager;
import com.unengchan.sdk.util.AppUtils;
import com.unengchan.sdk.util.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by unengchan on 2018/7/23
 * 语音播报帮助类
 */
public class VoiceHelper {


    private static TextToSpeech mTts;
    public static Map<String, String> firstNameMap;

    public static void init(){
        firstNameMap = new HashMap<>();
        addFirstName();
        mTts = new TextToSpeech(AppUtils.getAppContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                mTts.setLanguage(Locale.CHINESE);
                mTts.setSpeechRate(1.0f);
            }
        });
    }

    /**
     * 播放tts的语音
     * @param userName
     */
    public static void playTtsVoice(String userName){

        String playVoice = ConfigManager.getInstance().getPlayVoice();
        if (playVoice.equals(ConfigConstant.PLAY_VOICE_CLOSE)) {
            return;
        }
        if (StringUtils.isEmpty(userName)) {
            return;
        }
        String rightName = getRightName(userName);
        mTts.speak(rightName, TextToSpeech.QUEUE_FLUSH, null);


    }

    /**
     * 获取名字的正确读音，针对多音字姓氏的处理
     * @param name
     * @return
     */
    public static String getRightName(String name){

        // 姓氏
        String surname = String.valueOf(name.charAt(0));

        // 如果是多音字姓氏，获取正确的读音
        String rightSurname = firstNameMap.get(surname);
        if (rightSurname != null) {
            String rightName = name.replace(surname, rightSurname);
            return rightName;
        }
        return name;
    }
    /**
     * 添加姓，主要是对姓氏多音字的处理。
     */
    public static  void addFirstName() {
        firstNameMap.put("纪", "己");
        firstNameMap.put("任", "人");
        firstNameMap.put("曾", "增");
        firstNameMap.put("盖", "葛");
        firstNameMap.put("查", "渣");
        firstNameMap.put("翟", "宅");
        firstNameMap.put("朴", "瓢");
        firstNameMap.put("单", "善");
        firstNameMap.put("折", "舌");
        firstNameMap.put("解", "谢");
        firstNameMap.put("洗", "显");
        firstNameMap.put("秘", "闭");
        firstNameMap.put("仇", "求");
        firstNameMap.put("区", "欧");
        firstNameMap.put("重", "崇");
        firstNameMap.put("阚", "看");
        firstNameMap.put("燕", "烟");
        firstNameMap.put("缪", "庙");
        firstNameMap.put("秘", "毕");
        firstNameMap.put("褚", "楚");
        firstNameMap.put("弗", "费");
        firstNameMap.put("藉", "及");
        firstNameMap.put("适", "阔");
        firstNameMap.put("句", "钩");
        firstNameMap.put("繁", "婆");
        firstNameMap.put("乜", "聂");
        firstNameMap.put("旋", "玄");
        firstNameMap.put("眭", "虽");
        firstNameMap.put("员", "运");
        firstNameMap.put("祭", "债");
        firstNameMap.put("厘", "锡");
        firstNameMap.put("宿", "速");
        firstNameMap.put("黑", "贺");
        firstNameMap.put("晟", "成");
    }

}
