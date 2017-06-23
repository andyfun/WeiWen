package com.example;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by admin on 2017/4/13.
 */

public class GameDate {
    //排序
    @JSONField(ordinal = 1)
    private  int channelID;
    @JSONField(ordinal = 2)
    private  String dataTime;
    @JSONField(ordinal = 3)
    private  String productIDName;
   // private  String productName;
   @JSONField(ordinal = 4)
    private  int apkPackageID;
    @JSONField(ordinal = 5)
    private  String apkPackageName;
    @JSONField(ordinal = 6)
    private  int   clickNum;//点击数
    @JSONField(ordinal = 7)
    private int  acceleratorNum;//加速器激活人数
    @JSONField(ordinal = 8)
    private  int apkActivationNumber;//apk 激活人数
    @JSONField(ordinal = 9)
    private  int newRegisterNumber;//新增注册人
    @JSONField(ordinal = 10)
    private int moneyDay;//当日付钱


    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getProductIDName() {
        return productIDName;
    }

    public void setProductIDName(String productIDName) {
        this.productIDName = productIDName;
    }

    public int getClickNum() {
        return clickNum;
    }

    public void setClickNum(int clickNum) {
        this.clickNum = clickNum;
    }

    //    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }

    public int getApkPackageID() {
        return apkPackageID;
    }

    public void setApkPackageID(int apkPackageID) {
        this.apkPackageID = apkPackageID;
    }

    public String getApkPackageName() {
        return apkPackageName;
    }

    public void setApkPackageName(String apkPackageName) {
        this.apkPackageName = apkPackageName;
    }

    public int getAcceleratorNum() {
        return acceleratorNum;
    }

    public void setAcceleratorNum(int acceleratorNum) {
        this.acceleratorNum = acceleratorNum;
    }

    public int getApkActivationNumber() {
        return apkActivationNumber;
    }

    public void setApkActivationNumber(int apkActivationNumber) {
        this.apkActivationNumber = apkActivationNumber;
    }

    public int getNewRegisterNumber() {
        return newRegisterNumber;
    }

    public void setNewRegisterNumber(int newRegisterNumber) {
        this.newRegisterNumber = newRegisterNumber;
    }

    public int getMoneyDay() {
        return moneyDay;
    }

    public void setMoneyDay(int moneyDay) {
        this.moneyDay = moneyDay;
    }
}
