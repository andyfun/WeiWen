package com.guagua.knowledge.Callback;

import android.util.Log;

import com.baidu.appx.BDBannerAd;

/**
 * Created by zt-2010271 on 2016/12/1.
 */

public class AdListener implements BDBannerAd.BannerAdListener  {
    private static final String TAG = AdListener.class.getSimpleName();
    @Override
    public void onAdvertisementDataDidLoadSuccess() {
        Log.d(TAG," 广告加载成功");
    }

    @Override
    public void onAdvertisementDataDidLoadFailure() {
        Log.d(TAG," 广告加载失败");
    }

    @Override
    public void onAdvertisementViewDidShow() {
        Log.d(TAG," 广告已经展示");


    }

    @Override
    public void onAdvertisementViewDidClick() {
        Log.d(TAG," 广告点击");

    }

    @Override
    public void onAdvertisementViewWillStartNewIntent() {
        Log.d(TAG," 广告跳转");

    }
}
