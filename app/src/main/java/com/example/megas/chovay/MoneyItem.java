package com.example.megas.chovay;

import java.io.Serializable;

public class MoneyItem implements Serializable{
    long id, money;
    String note;

    public MoneyItem(long id, long money, String note) {
        this.id = id;
        this.money = money;
        this.note=note;
    }

    public long getId() {
        return id;
    }

    public long getMoney() {
        return money;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }
}
