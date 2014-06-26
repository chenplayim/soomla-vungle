/*
 Copyright (C) 2012-2014 Soomla Inc.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

#import "SoomlaVungle.h"
#import <VungleSDK/VungleSDK.h>
#import "SoomlaConfig.h"
#import "SoomlaUtils.h"
#import "Reward.h"


@implementation SoomlaVungle

static NSString* TAG = @"SOOMLA SoomlaVungle";

+ (SoomlaVungle*)getInstance {
    static SoomlaVungle* instance = NULL;
    if (!instance) {
        instance = [[SoomlaVungle alloc] init];
    }
    return instance;
}

- (void)initialize:(NSString*)vungleAppId {
    vunglePub = [VungleSDK sharedSDK];
    [vunglePub startWithAppId:vungleAppId];

    if (DEBUG_LOG) {
        [vunglePub setLoggingEnabled:YES];
    }
    vunglePub.delegate = self;
}

- (void)playAd:(UIViewController*)ui withReward:(Reward*)reward andCloseButtonOption:(BOOL)closeButton {
    savedReward = reward;
    [vunglePub playAd:ui withOptions:@{ @"showClose": [NSNumber numberWithBool:closeButton] }];
}


/**
 * if implemented, this will get called when the SDK is about to show an ad. This point
 * might be a good time to pause your game, and turn off any sound you might be playing.
 */
- (void)vungleSDKwillShowAd {
    LogDebug(TAG, @"vungleSDKwillShowAd");
}

/**
 * if implemented, this will get called when the SDK closes the ad view, but there might be
 * a product sheet that will be presented. This point might be a good place to resume your game
 * if there's no product sheet being presented. If the product sheet will be shown, we recommend
 * waiting for it to close before you resume, show a reward confirmation to the user, etc. The
 * viewInfo dictionary will contain the following keys:
 * - "completedView": NSNumber representing a BOOL whether or not the video can be considered a
 *                full view.
 * - "playTime": NSNumber representing the time in seconds that the user watched the video.
 * - "didDownlaod": NSNumber representing a BOOL whether or not the user clicked the download
 *                  button.
 */
- (void)vungleSDKwillCloseAdWithViewInfo:(NSDictionary*)viewInfo willPresentProductSheet:(BOOL)willPresentProductSheet {
    BOOL completed = [(NSNumber*)[viewInfo objectForKey:@"completedView"] boolValue];
    BOOL didDownlaod = [(NSNumber*)[viewInfo objectForKey:@"didDownlaod"] boolValue];
    double watched = [(NSNumber*)[viewInfo objectForKey:@"playTime"] doubleValue];
    LogDebug(TAG, ([NSString stringWithFormat:@"vungleSDKwillCloseAdWithViewInfo   completed: %@   watched: %f   didDownlaod: %@", completed?@"true":@"false", watched, didDownlaod?@"true":@"false"]));

    if (completed && savedReward) {
        [savedReward give];
        savedReward = NULL;
    }
}

/**
 * if implemented, this will get called when the product sheet is about to be closed.
 */
- (void)vungleSDKwillCloseProductSheet:(id)productSheet {
    LogDebug(TAG, @"vungleSDKwillCloseProductSheet");
}

@end
