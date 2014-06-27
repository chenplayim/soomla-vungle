//
// Created by Fedor Shubin on 6/12/14.
//

#import <Foundation/Foundation.h>

@class RootViewController;

@interface VungleService : NSObject
@property(nonatomic, retain) UIViewController *controller;

+ (id)sharedVungleService;

- (id)initWithAppId:(NSString *)appId;
@end