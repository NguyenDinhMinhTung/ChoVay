package com.example.megas.chovay;

import java.io.Serializable;

/**
 * Created by megas on 2018/04/28.
 */

public class MainItem implements Serializable {
    private String name;
    private long id;
    private long money;

    public MainItem(long id, String name) {
        this.name = name;
        this.id = id;
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
}
