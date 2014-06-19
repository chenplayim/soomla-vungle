package com.soomla.plugins.ads.vungle;

import com.soomla.SoomlaApp;
import com.soomla.SoomlaUtils;
import com.soomla.rewards.Reward;
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
        SoomlaUtils.LogDebug(TAG, "Starting Vungle for: " + appId);

        vunglePub.init(SoomlaApp.getAppContext(), appId);
        vunglePub.setEventListener(vungleListener);
    }

    public void onResume() {
        vunglePub.onResume();
    }

    public void onPause() {
        vunglePub.onPause();
    }

    public boolean isCachedAdAvailable() {
        return vunglePub.isCachedAdAvailable();
    }

    public void playIncentivisedAd(Reward reward) {
        playIncentivisedAd(false, true, reward);
    }

    public void playIncentivisedAd(boolean enableBackButton, boolean enableSounds, Reward reward) {
        mCurrentReward = reward;

        final AdConfig overrideConfig = new AdConfig();
        overrideConfig.setIncentivized(true);
        overrideConfig.setSoundEnabled(enableSounds);
        overrideConfig.setBackButtonImmediatelyEnabled(enableBackButton);
        vunglePub.playAd(overrideConfig);
    }

    public void playAd(Reward reward) {
      playAd(false, true, reward);
    }

    public void playAd(boolean enableBackButton, boolean enableSounds, Reward reward) {
        mCurrentReward = reward;

        final AdConfig overrideConfig = new AdConfig();
        overrideConfig.setSoundEnabled(enableSounds);
        overrideConfig.setBackButtonImmediatelyEnabled(enableBackButton);
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
            SoomlaUtils.LogDebug(TAG, "onVideoView:   completed: " +
                    isCompletedView + "   watched: " +
                    watchedMillis + "   duration: " +
                    videoDurationMillis);
            if (isCompletedView && mCurrentReward != null) {
                mCurrentReward.give();
                mCurrentReward = null;
            }
        }

        @Override
        public void onAdStart() {
            SoomlaUtils.LogError(TAG, "onAdStart");
        }

        @Override
        public void onAdUnavailable(String s) {
            SoomlaUtils.LogError(TAG, "onAdUnavailable");
            mCurrentReward = null;
        }

        @Override
        public void onAdEnd() {
            SoomlaUtils.LogError(TAG, "onAdEnd");
        }

        @Override
        public void onCachedAdAvailable() {
            SoomlaUtils.LogError(TAG, "onCachedAdAvailable");
        }

    };

    private SoomlaVungle() {

    }

    private Reward mCurrentReward = null;
    private static SoomlaVungle sInstance = null;
    private static final String TAG = "SOOMLA SoomlaVungle";
}
