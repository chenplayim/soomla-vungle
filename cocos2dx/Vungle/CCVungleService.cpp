//
// Created by Fedor Shubin on 5/20/13.
//

#include "CCVungleService.h"
#include "CCNdkBridge.h"

using namespace cocos2d;

namespace soomla {

    USING_NS_CC;

    static CCVungleService *sInstance = NULL;

    CCVungleService *CCVungleService::getInstance() {
        if (!sInstance)
        {
            sInstance = new CCVungleService();
            sInstance->retain();
        }
        return sInstance;
    }

    void CCVungleService::initShared(__Dictionary *profileParams) {
        CCVungleService *profileService = CCVungleService::getInstance();
        if (!profileService->init(profileParams)) {
            exit(1);
        }
    }

    CCVungleService::CCVungleService() {
    }

    bool CCVungleService::init(__Dictionary *profileParams) {

        __Dictionary *params = __Dictionary::create();
        params->setObject(__String::create("CCVungleService::init"), "method");
        params->setObject(profileParams, "params");
        CCNdkBridge::callNative(params, nullptr);

        return true;
    }

    void CCVungleService::playAd(CCReward *reward, bool closeButton) {
        __Dictionary *params = __Dictionary::create();
        params->setObject(__String::create("CCVungleService::playAd"), "method");
        if (reward) {
            params->setObject(reward->toDictionary(), "reward");
        }
        params->setObject(__Bool::create(closeButton), "closeButton");
        CCNdkBridge::callNative(params, nullptr);
    }
}
