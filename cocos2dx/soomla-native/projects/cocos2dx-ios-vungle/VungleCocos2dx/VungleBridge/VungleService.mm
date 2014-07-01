//
// Created by Fedor Shubin on 6/12/14.
//

#import "VungleService.h"
#import "NdkGlue.h"
#import "Reward.h"
#import "DomainFactory.h"
#import "SoomlaVungle.h"

@interface VungleService ()
@end

@implementation VungleService {

}

+ (id)sharedVungleService {
    static VungleService *sharedVungleService = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedVungleService = [self alloc];
    });
    return sharedVungleService;
}

+ (void)initialize {
    [super initialize];
    [self initGlue];
}

+ (void)initGlue {
    NdkGlue *ndkGlue = [NdkGlue sharedInstance];

    /* -= Call handlers =- */
    [ndkGlue registerCallHandlerForKey:@"CCVungleService::init" withBlock:^(NSDictionary *parameters, NSMutableDictionary *retParameters) {
        NSDictionary *dictionary = [parameters objectForKey:@"params"];
        NSString *appId = [dictionary objectForKey:@"appId"];
        [[VungleService sharedVungleService] initWithAppId: appId];
    }];
    [ndkGlue registerCallHandlerForKey:@"CCVungleService::playAd" withBlock:^(NSDictionary *parameters, NSMutableDictionary *retParameters) {
        NSNumber *closeButton = [parameters objectForKey:@"closeButton"];
        NSDictionary *rewardDict = [parameters objectForKey:@"reward"];
        Reward *reward = rewardDict ? [[DomainFactory sharedDomainFactory] createWithDict:rewardDict] : nil;
        [[SoomlaVungle getInstance] playAd:[[VungleService sharedVungleService] controller] withReward:reward andCloseButtonOption:closeButton.boolValue];
    }];
}

- (id)initWithAppId:(NSString *)appId {
    self = [super init];

    if (self) {
        [[SoomlaVungle getInstance] initialize:appId];
    }

    return self;
}

- (void)dealloc {
    [_controller release];
    [super dealloc];
}

@end