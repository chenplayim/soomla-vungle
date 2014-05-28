package com.soomla.plugins.ads.vungle;

import com.soomla.blueprint.rewards.Reward;
import com.soomla.store.SoomlaApp;
import com.soomla.store.StoreUtils;
import com.vungle.publisher.AdConfig;
import com.vungle.publisher.EventListener;
import com.vungle.publisher.VunglePub;

/**
 * Created by refaelos on 28/05/14.
 */
public class SoomlaVungle {

    final VunglePub vunglePub = VunglePub.getInstance();

    public void initialize(String appId) {

        // initialize the Publisher SDK
        vunglePub.init(SoomlaApp.getAppContext(), appId);
        vunglePub.setEventListener(vungleListener);
    }

    public void onResume() {
        vunglePub.onResume();
    }

    public void onPause() {
        vunglePub.onPause();
    }

    public void playIncentivisedAd(Reward reward) {
        mCurrentReward = reward;

        final AdConfig overrideConfig = new AdConfig();
        overrideConfig.setIncentivized(true);
        vunglePub.playAd(overrideConfig);
    }

    public void playAd(Reward reward) {
        mCurrentReward = reward;

        vunglePub.playAd();
    }

    public static SoomlaVungle getInstance() {
        if (sInstance == null) {
            sInstance = new SoomlaVungle();
        }
        return sInstance;
    }

    private final EventListener vungleListener = new EventListener(){

        @Override
        public void onVideoView(boolean isCompletedView, int watchedMillis, int videoDurationMillis) {
            StoreUtils.LogError("AAAAAAA", "video view   " + isCompletedView + "   " + watchedMillis + "   " + videoDurationMillis);
            if (isCompletedView) {
                mCurrentReward.give();
            }
        }

        @Override
        public void onAdStart() {
            StoreUtils.LogError("AAAAAAA", "started");
        }

        @Override
        public void onAdUnavailable(String s) {
            StoreUtils.LogError("AAAAAAA", "avail");
        }

        @Override
        public void onAdEnd() {
            StoreUtils.LogError("AAAAAAA", "ended");
        }

        @Override
        public void onCachedAdAvailable() {
            StoreUtils.LogError("AAAAAAA", "cache avail");
        }

    };

    private SoomlaVungle() {

    }

    private Reward mCurrentReward = null;
    private static SoomlaVungle sInstance = null;
}
