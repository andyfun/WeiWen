/*
 * MobileAppSDK android.
 *
 * Copyright (c) 2014 Giant Interactive Group, Inc. All rights reserved.
 */

package com.guagua.knowledge.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

/**
 * 获取设备的信息
 *
 * @author caiyuhao
 *         2013年11月21日
 */
public final class ZTDeviceUtil {

    /**
     * 初始化设备信息
     *
     * @param ctx Context
     */
    public static void initDeviceInfo(Context ctx) {
        ZTDeviceInfo deviceInfo = ZTDeviceInfo.getInstance();

        try {
            // 获取包名
            deviceInfo.setPackageName(ctx.getPackageName());
            // 获取appVersion
            try {
                PackageInfo localPackageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
                deviceInfo.setAppVersionCode(localPackageInfo.versionCode);
                deviceInfo.setAppVersionName(localPackageInfo.versionName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 获取配置
            Configuration configuration = ctx.getResources().getConfiguration();
            if ((configuration != null) && (configuration.locale != null)) {
                // 获取当前语言
                String language = configuration.locale.getLanguage();
                if ((language == null) || (language.length() == 0))
                    language = "en";
                deviceInfo.setLanguage(language);
                // 获取时区
                Calendar calendar = Calendar.getInstance(configuration.locale);
                int timeZome = 8;
                if (calendar != null) {
                    timeZome = calendar.getTimeZone().getRawOffset() / (60 * 60 * 1000);
                }
                deviceInfo.setTimeZone(Integer.toString(timeZome));
            }
            // 获取系统版本号
            deviceInfo.setOsVersion(Build.VERSION.RELEASE);
            // 获取系统SDK API版本号
            deviceInfo.setSdkApiVersion(Build.VERSION.SDK_INT);
            //获取设备型号
            deviceInfo.setDeviceModel(Build.MODEL);
            try {
                TelephonyManager localTelephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
                if (null != localTelephonyManager) {
                    // 获取运营商
                    deviceInfo.setCarrier(localTelephonyManager.getNetworkOperatorName());
                    //获取设备ID
                    String deviceId = localTelephonyManager.getDeviceId();
                    if (null != deviceId) {
                        deviceInfo.setImei(deviceId);
                    }
                }

                deviceInfo.setDeviceId(getOnlyDeviceId(ctx));

                WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
                String appMac = wifi.getConnectionInfo().getMacAddress();
                if (null != appMac && !appMac.equals("")) {
                    deviceInfo.setAppMac(appMac);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 获取屏幕大小
            try {
                DisplayMetrics dm = new DisplayMetrics();
                WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
                wm.getDefaultDisplay().getMetrics(dm);
                String resolution = String.valueOf(dm.heightPixels) + "*" + String.valueOf(dm.widthPixels);

                deviceInfo.setHeight(String.valueOf(dm.heightPixels));
                deviceInfo.setWidth(String.valueOf(dm.widthPixels));

                deviceInfo.setResolution(resolution);
            } catch (Exception e) {
                deviceInfo.setResolution("unknown");
            }
            // 获取经纬度
            Location location = getLocation(ctx);
            if (location != null) {
                deviceInfo.setLatitude(Double.toString(location.getLatitude()));
                deviceInfo.setLongitude(Double.toString(location.getLongitude()));
            }
            // 获取ip地址
            String appIp = getLocalIpAddress();
            if ((null != appIp) && (appIp.length() > 0)) {
                deviceInfo.setAppIp(appIp);
            }

            String totolMemory = getTotalMemory(ctx);

            if ((null != totolMemory) && (totolMemory.length() > 0)) {
                deviceInfo.setTotalMemory(totolMemory);
            }
            String cpuStr = getCpuString();
            if (cpuStr != null && cpuStr.length() > 0) {
                deviceInfo.setCpu(cpuStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取地理位置信息
     *
     * @param ctx Context
     * @return 位置信息
     */
    private static Location getLocation(Context ctx) {
        try {
            Location localLocation;
            LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
            if (checkPermission(ctx, "android.permission.ACCESS_FINE_LOCATION")) {
                localLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (localLocation != null) {
                    return localLocation;
                }
            }
            if (checkPermission(ctx, "android.permission.ACCESS_COARSE_LOCATION")) {
                localLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (localLocation != null) {
                    return localLocation;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本地IP地址
     *
     * @return 本地IP地址
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface inferf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = inferf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查网络是否可用
     *
     * @param ctx Context
     * @return 网络是否可用
     */
    public static boolean netIsAvailable(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        NetworkInfo.State state = info.getState();
        return state == NetworkInfo.State.CONNECTED;
    }

    /**
     * 检查权限
     *
     * @param ctx      Context
     * @param permName 待检查的权限名称
     * @return 返回是否具有该权限
     */
    public static boolean checkPermission(Context ctx, String permName) {
        PackageManager packageManager = ctx.getPackageManager();
        boolean havePermission = false;
        if (null != packageManager) {
            try {
                havePermission = packageManager.checkPermission(permName, ctx.getPackageName()) == PackageManager.PERMISSION_GRANTED ? true : false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return havePermission;
    }

    /**
     * 判断应用是否在前台运行
     *
     * @param ctx Context
     * @return true表示在前台运行，false表示在后台运行
     */
    public static boolean isAppOnForeground(Context ctx) {
        Context applicationContext = ctx.getApplicationContext();
        ActivityManager activityManager = null;
        String packageName = null;
        if (applicationContext != null) {
            activityManager = (ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
            packageName = applicationContext.getPackageName();
        }
        if (null != activityManager) {
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (null != appProcesses) {
                for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                    if (appProcess.processName.equals(packageName)
                            && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取总内存的大小
     *
     * @param ctx
     * @return
     *//*
    public static String getTotalMemory(Context ctx) {

        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayofString;
        long inital_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferReader.readLine();
            arrayofString = str2.split("\\s+");
            for (String num : arrayofString) {

            }
            inital_memory = Integer.valueOf(arrayofString[1]).intValue() * 1024;
            localBufferReader.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return Formatter.formatFileSize(ctx, inital_memory);
    }*/

    /**
     * 获取总内存的大小
     *
     * @param ctx
     * @return
     */
    public static String getTotalMemory(Context ctx) {

        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayofString;
        long inital_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferReader.readLine().trim();
            arrayofString = str2.split("\\s+");
            for (String num : arrayofString) {
                // LogUtil().e(" --num  "+num);
            }
            inital_memory = (Integer.valueOf(arrayofString[1]).intValue()) / 1024;

            localBufferReader.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return inital_memory + "MB";
    }


    /**
     * 唯一的设备ID
     */
    public static String getOnlyDeviceId(Context mCtx) {
        final TelephonyManager tm = (TelephonyManager) mCtx.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDeviceID, tmSerial, androidId, imsistr;
        //设备号 装有CM卡的
        tmDeviceID = "" + tm.getDeviceId();
        imsistr = "" + tm.getSubscriberId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(mCtx.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        //序列号  2.3 以上
        String serialnum = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            serialnum = "" + (String) (get.invoke(c, "ro.serialno", "unknown"));
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        String deviceUuid = imsistr + "|" + tmDeviceID + "|" + tmSerial + "|" + androidId + "|" + serialnum;
        //LogUtil.i(" deviceUUID = "+deviceUuid);
        String uniqueId = null;
        try {
           // uniqueId = Mdb.strMD5(deviceUuid);
            //LogUtil.i(" uniqueId = "+uniqueId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return uniqueId;

    }


    /**
     * 获取CPU信息
     *
     * @return
     */
    public static String getCpuString() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            /*for (int i = 0; i < array.length; i++) {
            }*/
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getlocalip(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        if (ipAddress == 0) return null;
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
    }
}