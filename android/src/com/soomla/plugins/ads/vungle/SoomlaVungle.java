/*
 * Copyright (C) 2012-2014 Soomla Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.soomla.plugins.ads.vungle;

import com.soomla.SoomlaApp;
import com.soomla.SoomlaUtils;
import com.soomla.rewards.Reward;
import com.vungle.publisher.AdConfig;
import com.vungle.publisher.EventListener;
import com.vungle.publisher.VunglePub;

/**
 * This class provides the API for working with the SOOMLA Vungle plugin.
 * Use it to initialize Vungle and play video ads in return for rewards.
 * It must be initialized after <code>SoomlaStore</code>.
 */
public class SoomlaVungle {

    final VunglePub vunglePub = VunglePub.getInstance();


    /**
     * Initializes the SOOMLA Vungle plugin.
     *
     * @param appId Your Vungle App ID as defined in the Vungle dashboard
     */
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

    /**
     * Plays a video ad and grants the user a reward for watching it.
     *
     * @param reward The reward that will be given to users for watching the video ad.
     * @param closeButton Determines whether you would like to give the user the option
     *                    to skip out of the video. <code>true</code> means a close button will be displayed.
     */
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
