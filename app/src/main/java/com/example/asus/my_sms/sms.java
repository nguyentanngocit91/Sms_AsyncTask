package com.example.asus.my_sms;

/**
 * Created by ASUS on 9/28/2017.
 */

public class sms {
    private String mName,mInfo;

    public sms(String name, String info) {
        this.mName = name;
        this.mInfo = info;
    }



    public String getName() {
        return mName;
    }
    public void setName(String name) {
        this.mName = name;
    }

    public String getInfo() {
        return mInfo;
    }
    public void setInfo(String info) {
        this.mInfo = info;
    }
}