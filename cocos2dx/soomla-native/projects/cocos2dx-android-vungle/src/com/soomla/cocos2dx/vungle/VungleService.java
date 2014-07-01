package com.soomla.cocos2dx.vungle;

import com.soomla.cocos2dx.common.AbstractSoomlaService;
import com.soomla.cocos2dx.common.DomainFactory;
import com.soomla.cocos2dx.common.NdkGlue;
import com.soomla.plugins.ads.vungle.SoomlaVungle;
import com.soomla.rewards.Reward;
import org.json.JSONObject;

/**
 * @author vedi
 *         date 6/10/14
 *         time 11:08 AM
 */
public class VungleService extends AbstractSoomlaService {

    private static VungleService INSTANCE = null;

    public static VungleService getInstance() {
        if (INSTANCE == null) {
            synchronized (VungleService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VungleService();
                }
            }
        }
        return INSTANCE;
    }

    public VungleService() {
        final NdkGlue ndkGlue = NdkGlue.getInstance();

        ndkGlue.registerCallHandler("CCVungleService::init", new NdkGlue.CallHandler() {
            @Override
            public void handle(JSONObject params, JSONObject retParams) throws Exception {
                JSONObject serviceParams = params.getJSONObject("params");
                String appId = serviceParams.getString("appId");
                VungleService.getInstance().init(appId);
            }
        });

        ndkGlue.registerCallHandler("CCVungleService::playAd", new NdkGlue.CallHandler() {
            @Override
            public void handle(JSONObject params, JSONObject retParams) throws Exception {
                Boolean closeButton = params.optBoolean("closeButton", false);

                JSONObject rewardJson = params.optJSONObject("reward");
                Reward reward = (rewardJson != null) ?
                        DomainFactory.getInstance().<Reward>createWithJsonObject(rewardJson) : null;

                SoomlaVungle.getInstance().playAd(closeButton, true, reward);
            }
        });
    }

    public void init(String appId) {
        SoomlaVungle.getInstance().initialize(appId);
    }

    @Override
    public void onPause() {
        SoomlaVungle.getInstance().onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        SoomlaVungle.getInstance().onResume();
    }
}
