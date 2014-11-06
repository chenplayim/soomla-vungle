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

#import "VungleEventHandling.h"


@implementation VungleEventHandling

+ (void)observeAllEventsWithObserver:(id)observer withSelector:(SEL)selector {
    [[NSNotificationCenter defaultCenter] addObserver:observer selector:selector name:EVENT_VUNGLE_HAS_VIDEOS object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:observer selector:selector name:EVENT_VUNGLE_AD_VIEWED object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:observer selector:selector name:EVENT_VUNGLE_AD_START object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:observer selector:selector name:EVENT_VUNGLE_AD_END object:nil];
}

+ (void)postVungleHasVideos {
    [[NSNotificationCenter defaultCenter] postNotificationName:EVENT_VUNGLE_HAS_VIDEOS object:self userInfo:nil];
}
+ (void)postVungleAdStart {
    [[NSNotificationCenter defaultCenter] postNotificationName:EVENT_VUNGLE_AD_START object:self userInfo:nil];
}
+ (void)postVungleAdEnd {
    [[NSNotificationCenter defaultCenter] postNotificationName:EVENT_VUNGLE_AD_END object:self userInfo:nil];
}
+ (void)postVungleAdViewedWithCompletion:(BOOL)isCompleted andTimeWatched:(double)playTime {
    NSDictionary *userInfo = @{
                               DICT_ELEMENT_COMPLETED: [NSNumber numberWithBool:isCompleted],
                               DICT_ELEMENT_TIME_WATCHED: [NSNumber numberWithDouble:playTime]
                               };
    [[NSNotificationCenter defaultCenter] postNotificationName:EVENT_VUNGLE_AD_VIEWED object:self userInfo:userInfo];
}

@end
