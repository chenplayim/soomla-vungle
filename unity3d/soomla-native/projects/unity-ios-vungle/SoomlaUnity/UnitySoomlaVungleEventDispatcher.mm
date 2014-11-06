#import "Soomla.h"
#import "UnitySoomlaVungleEventDispatcher.h"
#import "VungleEventHandling.h"
#import "Reward.h"
#import "SoomlaUtils.h"

@implementation UnitySoomlaVungleEventDispatcher

+ (void)initialize {
    static UnitySoomlaVungleEventDispatcher* instance = nil;
    if (!instance) {
        LogDebug(@"UnitySoomlaVungleEventDispatcher", @"initialize");
        instance = [[UnitySoomlaVungleEventDispatcher alloc] init];
    }
}

- (id) init {
    if (self = [super init]) {
        [VungleEventHandling observeAllEventsWithObserver:self withSelector:@selector(handleEvent:)];
    }
    
    return self;
}

- (void)handleEvent:(NSNotification*)notification{
    
	if ([notification.name isEqualToString:EVENT_VUNGLE_AD_END]) {
        LogDebug(@"UnitySoomlaVungleEventDispatcher", @"EVENT_VUNGLE_AD_END");
        UnitySendMessage("VungleEvents", "onVungleAdEnd", "");
	}
    else if ([notification.name isEqualToString:EVENT_VUNGLE_AD_START]) {
        LogDebug(@"UnitySoomlaVungleEventDispatcher", @"EVENT_VUNGLE_AD_START");
        UnitySendMessage("VungleEvents", "onVungleAdStart", "");
    }
    else if ([notification.name isEqualToString:EVENT_VUNGLE_HAS_VIDEOS]) {
        LogDebug(@"UnitySoomlaVungleEventDispatcher", @"EVENT_VUNGLE_HAS_VIDEOS");
        UnitySendMessage("VungleEvents", "onVungleHasAds", "");
    }
	else if ([notification.name isEqualToString:EVENT_VUNGLE_AD_VIEWED]) {
        LogDebug(@"UnitySoomlaVungleEventDispatcher", @"EVENT_VUNGLE_AD_VIEWED");
        NSDictionary* userInfo = [notification userInfo];
        NSDictionary* eventJSON = @{
                                    @"completed": [userInfo objectForKey:DICT_ELEMENT_COMPLETED],
                                    @"timeWatched": [userInfo objectForKey:DICT_ELEMENT_TIME_WATCHED]
                                    };
        UnitySendMessage("VungleEvents", "onVungleAdViewed", [[SoomlaUtils dictToJsonString:eventJSON] UTF8String]);
	}
}

@end
