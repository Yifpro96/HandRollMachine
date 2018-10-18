package com.seahung.handrollmachine.fragment.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.constant.ConfigConstant;
import com.seahung.handrollmachine.constant.FragmentConstant;
import com.seahung.handrollmachine.global.GlobalSettingFragment;
import com.seahung.handrollmachine.helper.ViewHelper;
import com.seahung.handrollmachine.manager.ConfigManager;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by unengchan on 2018/6/5
 * 语音播报
 */
public class PlayVoiceFragment extends GlobalSettingFragment implements SeekBar.OnSeekBarChangeListener ,TextToSpeech.OnInitListener{

    @BindView(R.id.tv_play_voice_open)
    TextView tvPlayVoiceOpen;
    @BindView(R.id.tv_play_voice_close)
    TextView tvPlayVoiceClose;
    @BindView(R.id.tv_play_voice_live_type_name)
    TextView tvPlayVoiceLiveTypeName;
    @BindView(R.id.tv_play_voice_live_type)
    TextView tvPlayVoiceLiveType;
    @BindView(R.id.tv_play_voice_name)
    TextView tvPlayVoiceName;
    @BindView(R.id.ll_play_voice_example)
    LinearLayout llPlayVoiceExample;
    @BindView(R.id.tv_play_voice_volume)
    TextView tvPlayVoiceVolume;
    @BindView(R.id.seek_bar_volume)
    SeekBar seekBarVolume;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    public ConfigManager mConfigManager;
    public AudioManager mAudioManager;
    public ChangeVolumeReceiver mChangeVolumeReceiver;
    public TextToSpeech mTts;

    @Override
    protected void initData() {
        super.initData();
        mConfigManager = ConfigManager.getInstance();
        // 初始化播报tts
        mTts = new TextToSpeech(mContext, this);
        // 设置音量
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seekBarVolume.setMax(maxVolume);
        int volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBarVolume.setProgress(volume);
        tvPlayVoiceVolume.setText(String.valueOf(volume));

        // 注册音量改变广播
        mChangeVolumeReceiver = new ChangeVolumeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        mContext.registerReceiver(mChangeVolumeReceiver, filter);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvTitleName.setText(mContext.getString(R.string.setting_play_voice));
        seekBarVolume.setOnSeekBarChangeListener(this);
        String playVoice = mConfigManager.getPlayVoice();
        String playVoiceType = mConfigManager.getPlayVoiceType();
        refreshPlayVoiceUi(playVoice);
        refreshPlayVoiceTypeUi(playVoiceType);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.play_voice_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_PLAY_VOICE_FRAGMENT;
    }

    @OnClick({R.id.tv_play_voice_open, R.id.tv_play_voice_close, R.id.tv_play_voice_live_type_name,
            R.id.tv_play_voice_live_type, R.id.tv_play_voice_name, R.id.ll_play_voice_example,R.id.iv_title_back})
    public void onViewClicked(View view) {
        String playVoice = null;
        String playVoiceType = null;
        switch (view.getId()) {
            case R.id.iv_title_back:
                mFragmentListener.onSwitchFragment(FragmentConstant.SETTING_SETTING_LIST_FRAGMENT);
                return;
            case R.id.tv_play_voice_open:
                playVoice = ConfigConstant.PLAY_VOICE_OPEN;
                break;
            case R.id.tv_play_voice_close:
                playVoice = ConfigConstant.PLAY_VOICE_CLOSE;
                break;
            case R.id.tv_play_voice_live_type_name:
                playVoiceType = ConfigConstant.PLAY_VOICE_TYPE_LIVE_TYPE_NAME;
                break;
            case R.id.tv_play_voice_live_type:
                playVoiceType = ConfigConstant.PLAY_VOICE_TYPE_LIVE_TYPE;
                break;
            case R.id.tv_play_voice_name:
                playVoiceType = ConfigConstant.PLAY_VOICE_TYPE_NAME;
                break;
            case R.id.ll_play_voice_example:
                playVoiceExample();
                return;
        }
        if (playVoice != null) {
            refreshPlayVoiceUi(playVoice);
            mConfigManager.setPlayVoice(playVoice);
        }
        if (playVoiceType != null) {
            refreshPlayVoiceTypeUi(playVoiceType);
            mConfigManager.setPlayVoiceType(playVoiceType);
        }
    }

    /**
     * 打开/关闭语音播报
     * @param playVoice
     */
    private void refreshPlayVoiceUi(String playVoice) {
        int index = -1;
        switch (playVoice) {
            case ConfigConstant.PLAY_VOICE_OPEN:
                index = 0;
                break;
            case ConfigConstant.PLAY_VOICE_CLOSE:
                index = 1;
                break;
        }
        ViewHelper.refreshTextViews(index, tvPlayVoiceOpen, tvPlayVoiceClose);
    }

    /**
     * 语音播报方式选择
     * @param playVoiceType
     */
    private void refreshPlayVoiceTypeUi(String playVoiceType) {
        int index = -1;
        switch (playVoiceType) {
            case ConfigConstant.PLAY_VOICE_TYPE_LIVE_TYPE_NAME:
                index = 0;
                break;
            case ConfigConstant.PLAY_VOICE_TYPE_LIVE_TYPE:
                index = 1;
                break;
            case ConfigConstant.PLAY_VOICE_TYPE_NAME:
                index = 2;
                break;
        }
        ViewHelper.refreshTextViews(index, tvPlayVoiceLiveTypeName, tvPlayVoiceLiveType, tvPlayVoiceName);
    }

    /**
     * 播放语音合成示例
     */
    private void playVoiceExample() {
        String playVoiceType = mConfigManager.getPlayVoiceType();
        if (ConfigConstant.PLAY_VOICE_TYPE_LIVE_TYPE_NAME.equals(playVoiceType)) {
            mTts.speak("住校生，小明同学",TextToSpeech.QUEUE_FLUSH,null);
            return;
        }
        if (ConfigConstant.PLAY_VOICE_TYPE_LIVE_TYPE.equals(playVoiceType)) {
            mTts.speak("住校生",TextToSpeech.QUEUE_FLUSH,null);
            return;
        }
        mTts.speak("小明同学",TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }
        seekBarVolume.setProgress(progress);
        tvPlayVoiceVolume.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mTts.setLanguage(Locale.CHINESE);
            mTts.setSpeechRate(1.0f);
        }
    }

    /**
     * 系统音量改变广播接收器
     */
    private class ChangeVolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                int volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                seekBarVolume.setProgress(volume);
                tvPlayVoiceVolume.setText(String.valueOf(volume));
            }
        }
    }

    @Override
    public void onDestroyView() {
        mContext.unregisterReceiver(mChangeVolumeReceiver);
        super.onDestroyView();
        mTts.shutdown();
    }
}
