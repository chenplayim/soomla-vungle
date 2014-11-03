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

@class Reward;
@protocol VungleSDKDelegate;
@class VungleSDK;

/**
 This class provides the API for working with the SOOMLA Vungle plugin.
 Use it to initialize Vungle and play video ads in return for rewards.
 It must be initialized after `SoomlaStore`.
 */
@interface SoomlaVungle : NSObject <VungleSDKDelegate> {
    @private
    VungleSDK* vunglePub;
    Reward* savedReward;
}

+ (SoomlaVungle*)getInstance;

/**
  Initializes the SOOMLA Vungle plugin.

  @param vungleAppId Your Vungle App ID as defined in the Vungle dashboard
 */
- (void)initialize:(NSString*)vungleAppId;

/**
 Plays a video ad and grants the user a reward for watching it.

 @param reward The reward that will be given to users for watching the video ad.
 @param closeButton Determines whether you would like to give the user the option
                    to skip out of the video. `YES` means a close button will be displayed.
 */
- (void)playAd:(UIViewController*)ui withReward:(Reward*)reward andCloseButtonOption:(BOOL)closeButton;


@end
