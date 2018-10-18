package com.seahung.handrollmachine.bean.setting;

/**
 * Created by unengchan on 2018/6/4
 * 设置项、例如班级id，学校id等等
 */
public class SettingItem {

    private int iconId;
    private String itemName;
    private int itemIndex;

    public SettingItem(int iconId, String itemName, int itemIndex) {
        this.iconId = iconId;
        this.itemName = itemName;
        this.itemIndex = itemIndex;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    public int getItemIndex() {
        return itemIndex;
    }
}
