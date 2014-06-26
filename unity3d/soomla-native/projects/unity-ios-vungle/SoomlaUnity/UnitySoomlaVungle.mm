#import "SoomlaConfig.h"
#import "SoomlaVungle.h"
#import "Reward.h"
#import "SoomlaUtils.h"

extern "C" UIViewController* UnityGetGLViewController();

extern "C"{

    void soomlaVungle_Init(const char* appId) {
        [[SoomlaVungle getInstance] initialize:[NSString stringWithUTF8String:appId]];
    }

	void soomlaVungle_PlayAd(bool enableBackButton, const char* rewardJSON){
        Reward* reward = NULL;
        if (rewardJSON) {
            NSDictionary* dict = [SoomlaUtils jsonStringToDict:[NSString stringWithUTF8String:rewardJSON]];
            reward = [Reward fromDictionary:dict];
        }
        UIViewController* uivc = UnityGetGLViewController();
        [[SoomlaVungle getInstance] playAd:uivc withReward:reward andCloseButtonOption:enableBackButton];
	}
}