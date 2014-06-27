//
// Created by Fedor Shubin on 5/20/13.
//

#ifndef __CCVungleService_H_
#define __CCVungleService_H_

#include "cocos2d.h"
#include "CCError.h"
#include "CCReward.h"

namespace soomla {

    class CCVungleService: public cocos2d::Ref {
    public:
		/**
		   This class is singleton, use this function to access it.
		*/
        static CCVungleService* getInstance();

        static void initShared(cocos2d::__Dictionary *profileParams);

        CCVungleService();

        virtual bool init(cocos2d::__Dictionary *profileParams);

        void playAd(CCReward *reward, bool closeButton);
    };
};

#endif // !__CCVungleController_H_
