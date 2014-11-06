package com.soomla.core.unity;

import com.soomla.BusProvider;
import com.soomla.SoomlaUtils;
import com.soomla.plugins.ads.vungle.events.VungleAdEndEvent;
import com.soomla.plugins.ads.vungle.events.VungleAdStartEvent;
import com.soomla.plugins.ads.vungle.events.VungleAdViewedEvent;
import com.soomla.plugins.ads.vungle.events.VungleHasAdsEvent;
import com.squareup.otto.Subscribe;
import com.unity3d.player.UnityPlayer;

import org.json.JSONException;
import org.json.JSONObject;

public class SoomlaVungleEventHandler {
    private static SoomlaVungleEventHandler mLocalEventHandler;

    public static void initialize() {
        SoomlaUtils.LogDebug("SOOMLA Unity SoomlaVungleEventHandler", "Initializing SoomlaVungleEventHandler ...");
        mLocalEventHandler = new SoomlaVungleEventHandler();
    }

    public SoomlaVungleEventHandler() {
        BusProvider.getInstance().register(this);
    }

    @Subscribe
    public void onVungleAdEnd(VungleAdEndEvent vungleAdEndEvent) {
        UnityPlayer.UnitySendMessage("VungleEvents", "onVungleAdEnd", "");
    }

    @Subscribe
    public void onVungleAdStart(VungleAdStartEvent vungleAdStartEvent) {
        UnityPlayer.UnitySendMessage("VungleEvents", "onVungleAdStart", "");
    }

    @Subscribe
    public void onVungleHasAds(VungleHasAdsEvent vungleHasAdsEvent) {
        UnityPlayer.UnitySendMessage("VungleEvents", "onVungleHasAds", "");
    }

    @Subscribe
    public void onVungleAdViewed(VungleAdViewedEvent vungleAdViewedEvent) {
        if (vungleAdViewedEvent.Sender == this) {
            return;
        }
        try {
            JSONObject eventJSON = new JSONObject();

            eventJSON.put("completed", vungleAdViewedEvent.IsCompletedView);
            eventJSON.put("timeWatched", (double)(vungleAdViewedEvent.WatchedMillis/1000.0));

            UnityPlayer.UnitySendMessage("VungleEvents", "onVungleAdViewed", eventJSON.toString());
        } catch (JSONException e) {
            SoomlaUtils.LogError("SOOMLA SoomlaVungleEventHandler", "This is BAD! couldn't create JSON for onVungleAdViewed event.");
        }
    }
}
