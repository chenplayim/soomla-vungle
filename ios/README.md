This project works alongside with SOOMLA for iOS.

* If you haven't already done so, head over to Vungle's [dashboard](https://v.vungle.com/dashboard/login) and add your app to your account. You need to do this so that you can get your App ID that you’ll be initializing SoomlaVungle with. It’s in **red** on your app’s page.


## Getting Started - iOS

1. You'll need to attach the static libraries from the [build](https://github.com/soomla/soomla-vungle/tree/master/android/build) folder to your project. Add that folder to your "Library Search Paths" and "Header Search Path". Also, you'll need to add `-ObjC -lSoomlaiOSCore -lSoomlaiOSVungle` to "Other Linker Flags".

2. Vungle requires the following frameworks:  
* VungleSDK.embeddedframework (you can use the one in this repo)
* AdSupport.framework
* AudioToolbox.framework
* AVFoundation.framework
* CFNetwork.framework
* CoreGraphics.framework
* CoreMedia.framework
* Foundation.framework
* libz.dylib
* libsqlite3.dylib
* MediaPlayer.framework
* QuartzCore.framework
* StoreKit.framework
* SystemConfiguration.framework
* UIKit.framework

3. Initialize **Soomla** with a secret that you choose to encrypt the user data for SOOMLA. Initialize SoomlaVungle with the appId from Vungle Dashboard:

    ``` Objective-C
     [Soomla initializeWithSecret:@"[YOUR SOOMLA SECRET]"];

     [[SoomlaVungle getInstance] initialize:@"[YOUR VUNGLE APP ID]"];
    ```
    > The secret is your encryption secret for data saved in the DB.

## Playing an Ad

To play a Vungle Ad, just call the "playAd" function on SoomlaVungle. Here is an example of how it's done:

``` Objective-C
 VirtualItemReward* reward = [[VirtualItemReward alloc] initWithRewardId:@"muffins_for_ad" andName:@"Muffins for watching Ad" andAmount:1232 andAssociatedItemId:@"currency_muffin"];
 reward.schedule = [Schedule AnyTimeUnlimited];
 [[SoomlaVungle getInstance] playAd:self withReward:reward andCloseButtonOption:true];
```

Note that in this example we use `VirtualItemReward` which is a special kind of Reward from [ios-store](https://github.com/soomla/ios-store). If you also want to use that reward, go over to that project and follow the integration instructions there.
