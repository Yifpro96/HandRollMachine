package com.seahung.handrollmachine.bean.setting;

/**
 * Created by unengchan on 2018/7/16
 * 皮肤
 */
public class SkinItem {

    private int icon;
    private String colors;
    private String name;
    private boolean selected;

    public SkinItem(int icon, String name,String colors,  boolean selected) {
        this.icon = icon;
        this.colors = colors;
        this.name = name;
        this.selected = selected;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
