#import "SoomlaConfig.h"
#import "SoomlaVungle.h"
#import "Reward.h"
#import "SoomlaUtils.h"
#import "UnitySoomlaVungleEventDispatcher.h"

extern "C" UIViewController* UnityGetGLViewController();

extern "C"{

    void soomlaVungle_Init(const char* appId) {
        [UnitySoomlaVungleEventDispatcher initialize];
        [[SoomlaVungle getInstance] initialize:[NSString stringWithUTF8String:appId]];
    }

	void soomlaVungle_PlayAd(bool enableBackButton){
        UIViewController* uivc = UnityGetGLViewController();
        [[SoomlaVungle getInstance] playAd:uivc withReward:NULL andCloseButtonOption:enableBackButton];
	}
    
    bool soomlaVungle_HasAds() {
        return [[SoomlaVungle getInstance] hasAds];
    }
    
}