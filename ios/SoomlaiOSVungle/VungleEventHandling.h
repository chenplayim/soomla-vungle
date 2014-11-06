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

// Events
#define EVENT_VUNGLE_HAS_VIDEOS             @"VungleHasVideos"
#define EVENT_VUNGLE_AD_VIEWED              @"VungleAdViewed"
#define EVENT_VUNGLE_AD_START               @"VungleAdStart"
#define EVENT_VUNGLE_AD_END                 @"VungleAdEnd"

// Dictionary Elements
#define DICT_ELEMENT_COMPLETED              @"completed"
#define DICT_ELEMENT_TIME_WATCHED           @"timeWatched"


@interface VungleEventHandling : NSObject

+ (void)observeAllEventsWithObserver:(id)observer withSelector:(SEL)selector;
+ (void)postVungleHasVideos;
+ (void)postVungleAdStart;
+ (void)postVungleAdEnd;
+ (void)postVungleAdViewedWithCompletion:(BOOL)isCompleted andTimeWatched:(double)playTime;

@end
