package com.guagua.knowledge;

import android.app.Application;
import android.content.Context;

import com.guagua.knowledge.Utils.ZTDeviceUtil;

/**
 * Created by tusalin on 9/9/2016.
 */

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        mContext = getApplicationContext();
        ZTDeviceUtil.initDeviceInfo(this);
    }

    public static Context getContext(){
        return mContext;
    }
}
