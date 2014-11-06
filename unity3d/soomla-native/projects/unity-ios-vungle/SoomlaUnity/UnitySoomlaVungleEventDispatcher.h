
#import <Foundation/Foundation.h>

@interface UnitySoomlaVungleEventDispatcher : NSObject{
    
}
- (id)init;
- (void)handleEvent:(NSNotification*)notification;
+ (void)initialize;

@end
