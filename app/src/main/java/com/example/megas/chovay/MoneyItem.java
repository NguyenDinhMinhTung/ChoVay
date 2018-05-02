package com.example.megas.chovay;

import java.io.Serializable;

public class MoneyItem implements Serializable {
    long mainID, localID, money;
    String note;
    boolean checked = false;
    int state;

    public MoneyItem(long mainID, long localID, long money, String note, int state) {
        this.mainID = mainID;
        this.localID = localID;
        this.money = money;
        this.note = note;
        this.state = state;
    }

    public long getMoney() {
        return money;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {

        return state;
    }

    public void setMainID(long mainID) {
        this.mainID = mainID;
    }

    public void setLocalID(long localID) {
        this.localID = localID;
    }

    public long getMainID() {

        return mainID;
    }

    public long getLocalID() {
        return localID;
    }

    public String getNote() {
        return note;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {

        return checked;
    }
}
