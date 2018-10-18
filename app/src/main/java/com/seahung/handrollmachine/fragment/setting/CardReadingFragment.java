package com.seahung.handrollmachine.fragment.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.constant.ConfigConstant;
import com.seahung.handrollmachine.constant.FragmentConstant;
import com.seahung.handrollmachine.global.GlobalSettingFragment;
import com.seahung.handrollmachine.helper.ViewHelper;
import com.seahung.handrollmachine.manager.ConfigManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by unengchan on 2018/6/5
 * 读卡配置
 */
public class CardReadingFragment extends GlobalSettingFragment {

    @BindView(R.id.tv_serial_port_one)
    TextView tvSerialPortOne;
    @BindView(R.id.tv_serial_port_two)
    TextView tvSerialPortTwo;
    @BindView(R.id.tv_serial_port_three)
    TextView tvSerialPortThree;
    @BindView(R.id.tv_serial_port_four)
    TextView tvSerialPortFour;
    @BindView(R.id.tv_baud_rate_2400)
    TextView tvBaudRate2400;
    @BindView(R.id.tv_baud_rate_4800)
    TextView tvBaudRate4800;
    @BindView(R.id.tv_baud_rate_9600)
    TextView tvBaudRate9600;
    @BindView(R.id.tv_baud_rate_19200)
    TextView tvBaudRate19200;
    public ConfigManager mConfigManager;

    @Override
    protected void initData() {
        super.initData();
        mConfigManager = ConfigManager.getInstance();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        String serialPort = mConfigManager.getSerialPort();
        int baudRate = mConfigManager.getBaudRate();
        refreshSerialPortUi(serialPort);
        refreshBaudRateUi(baudRate);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.card_reading_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_CARD_READING_FRAGMENT;
    }

    @OnClick({R.id.tv_serial_port_one, R.id.tv_serial_port_two, R.id.tv_serial_port_three, R.id.tv_serial_port_four, R.id.tv_baud_rate_2400, R.id.tv_baud_rate_4800, R.id.tv_baud_rate_9600, R.id.tv_baud_rate_19200})
    public void onViewClicked(View view) {
        String serialPort = "";
        int baudRate = 0;
        switch (view.getId()) {
            case R.id.tv_serial_port_one:
                serialPort = ConfigConstant.SERIAL_PORT_ONE;
                break;
            case R.id.tv_serial_port_two:
                serialPort = ConfigConstant.SERIAL_PORT_TWO;
                break;
            case R.id.tv_serial_port_three:
                serialPort = ConfigConstant.SERIAL_PORT_THREE;
                break;
            case R.id.tv_serial_port_four:
                serialPort = ConfigConstant.SERIAL_PORT_FOUR;
                break;
            case R.id.tv_baud_rate_2400:
                baudRate = 2400;
                break;
            case R.id.tv_baud_rate_4800:
                baudRate = 4800;
                break;
            case R.id.tv_baud_rate_9600:
                baudRate = 9600;
                break;
            case R.id.tv_baud_rate_19200:
                baudRate = 19200;
                break;
        }
        // 刷新界面并保存串口
        if (!"".equals(serialPort)) {
            refreshSerialPortUi(serialPort);
            mConfigManager.setSerialPort(serialPort);
        }
        // 刷新界面并保存波特率
        if (baudRate != 0) {
            refreshBaudRateUi(baudRate);
            mConfigManager.setBaudRate(baudRate);
        }
    }

    /**
     * 串口号选择
     *
     * @param serialPort 串口
     */
    private void refreshSerialPortUi(String serialPort) {
        int index;
        switch (serialPort) {
            case ConfigConstant.SERIAL_PORT_ONE:
                index = 0;
                break;
            case ConfigConstant.SERIAL_PORT_TWO:
                index = 1;
                break;
            case ConfigConstant.SERIAL_PORT_THREE:
                index = 2;
                break;
            case ConfigConstant.SERIAL_PORT_FOUR:
                index = 3;
                break;
            default:
                index = -1;
                break;
        }
        ViewHelper.refreshTextViews(index, tvSerialPortOne, tvSerialPortTwo, tvSerialPortThree, tvSerialPortFour);
    }

    /**
     * 波特率选择
     *
     * @param baudRate 波特率
     */
    private void refreshBaudRateUi(int baudRate) {
        int index;
        switch (baudRate) {
            case ConfigConstant.BAUD_RATE_2400:
                index = 0;
                break;
            case ConfigConstant.BAUD_RATE_4800:
                index = 1;
                break;
            case ConfigConstant.BAUD_RATE_9600:
                index = 2;
                break;
            case ConfigConstant.BAUD_RATE_19200:
                index = 3;
                break;
            default:
                index = -1;
                break;
        }
        ViewHelper.refreshTextViews(index, tvBaudRate2400, tvBaudRate4800, tvBaudRate9600, tvBaudRate19200);
    }



}
