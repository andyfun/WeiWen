/*
 * MobileAppSDK android.
 *
 * Copyright (c) 2014 Giant Interactive Group, Inc. All rights reserved.
 */

package com.guagua.knowledge.Utils;


/**
 * 设备信息
 *
 * @author 宋仕良
 */
public final class ZTDeviceInfo {
    /**
     * 应用或者游戏的包名
     */
    private String packageName = "unknown";
    /**
     * 应用或者游戏的版本号
     */
    private int appVersionCode = 0;
    /**
     * 应用或者游戏的版本名称
     */
    private String appVersionName = "unknown";
    /**
     * 系统语言
     */
    private String language = "default";
    /**
     * 当前时区
     */
    private String timeZone = "8";
    /**
     * 系统版本号
     */
    private String osVersion = "unknown";
    /**
     * 系统SDK API版本号
     */
    private int sdkApiVersion = 0;
    /**
     * 设备型号
     */
    private String deviceModel = "unknown";
    /**
     * 运营商
     */
    private String carrier = "unknown";
    /**
     * 设备ID
     */
    private String deviceId = "000000000000000";
    /**
     * 手机屏幕大小
     */
    private String resolution = "320*480";
    /**
     * 纬度，地理位置信息
     */
    private String latitude = "0";
    /**
     * 经度，地理位置信息
     */
    private String longitude = "0";
    /**
     * 设备的IP地址
     */
    private String appIp = "0.0.0.0";
    /**
     * 设备的MAC地址
     */
    private String appMac = "00:00:00:00:00:00";
    /**
     * 设备的手机号
     */
    private String mobileNum = "0";
    /**
     * 设备的内存
     */
    private String totalMemory = "获取不到";
    private String imei = "000000000";
    private String cpu = "unknown";
    private String height;
    private String width;

    private static ZTDeviceInfo mInstance = null;

    private ZTDeviceInfo() {
    }

    public static ZTDeviceInfo getInstance() {
        if (null == mInstance) {
            mInstance = new ZTDeviceInfo();
        }
        return mInstance;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(int appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public int getSdkApiVersion() {
        return sdkApiVersion;
    }

    public void setSdkApiVersion(int sdkApiVersion) {
        this.sdkApiVersion = sdkApiVersion;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAppIp() {
        return appIp;
    }

    public void setAppIp(String appIp) {
        this.appIp = appIp;
    }

    public String getAppMac() {
        return appMac;
    }

    public void setAppMac(String appMac) {
        this.appMac = appMac;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(String totalMemory) {
        this.totalMemory = totalMemory;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}