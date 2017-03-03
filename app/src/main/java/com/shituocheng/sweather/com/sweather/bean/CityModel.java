package com.shituocheng.sweather.com.sweather.bean;

/**
 * Created by shituocheng on 16/6/28.
 */
public class CityModel {

    private String name;
    private int city_id;
    private boolean isChecked;
    private boolean hasActivity;

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isHasActivity() {
        return hasActivity;
    }

    public void setHasActivity(boolean hasActivity) {
        this.hasActivity = hasActivity;
    }
}
