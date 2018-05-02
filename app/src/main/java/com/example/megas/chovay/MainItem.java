package com.example.megas.chovay;

import java.io.Serializable;

/**
 * Created by megas on 2018/04/28.
 */

public class MainItem implements Serializable {
    private String name;
    private long id;
    private long money;
    private long groupID;
    private boolean checked = false;
    private boolean state = true;

    public MainItem(long id, String name, long groupID) {
        this.name = name;
        this.id = id;
        this.groupID = groupID;

    }

    public long getGroupID() {
        return groupID;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isState() {


        return state;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {

        return checked;
    }
}
