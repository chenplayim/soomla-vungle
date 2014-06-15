//
//  SoomlaVungle.h
//  SoomlaiOSVungle
//
//  Created by Refael Dakar on 13/06/14.
//  Copyright (c) 2014 SOOMLA. All rights reserved.
//

#import <VungleSDK/VungleSDK.h>

@class Reward;

@interface SoomlaVungle : NSObject <VungleSDKDelegate> {
    @private
    VungleSDK* vunglePub;
    Reward* savedReward;
}

+ (SoomlaVungle*)getInstance;
- (void)initialize:(NSString*)vungleAppId;
- (void)playAd:(UIViewController*)ui withReward:(Reward*)reward andCloseButtonOption:(BOOL)closeButton;


@end
