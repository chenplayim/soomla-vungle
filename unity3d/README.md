This project works alongside with SOOMLA for Unity3d.

* If you haven't already done so, head over to Vungle's [dashboard](https://v.vungle.com/dashboard/login) and add your app to your account. You need to do this so that you can get your App ID that you’ll be initializing `SoomlaVungle` with. It’s in **red** on your app’s page.


## Getting Started - Unity3d

1. Double-Click on `soomla-unity3d-vungle.unitypackage` to add it to your Unity3d project. It'll add all the necessary files including the Vungle SDK (You don't need to import it yourself).
> If you see some "File could not be read" errors in the unity log while importing the "unitypackage" ignore    them. Unity does that when it loads those png resources but it won't affect your build.

2. Open the SOOMLA settings panel: Window > Soomla > Edit Settings.  Fill in the fields named:
**Vungle App ID - Android**
**Vungle App ID - iOS**
with the app IDs from the Vungle dashboard (you can fill either or both, depending on the platforms you're targeting).
3. You'll need to initialize SoomlaVungle AFTER you initialized SOOMLA (or SoomlaStore if you use [unity3d-store](https://github.com/soomla/unity3d-store)).

    ```csharp
    using Soomla.Vungle;

    ...

    void Start () {
        ...
        
        SoomlaStore.Initialize(new MuffinRushAssets());
        SoomlaVungle.Initialize();
        
        ...
    }

    ...
    ```

## Playing an Ad

To play a Vungle Ad, just call the "playAd" function on SoomlaVungle. Here is an example of how it's done:

```csharp
VirtualItemReward reward = new VirtualItemReward("muffins_for_ad", "Muffins for watching Ad", "currency_muffin", 1232);
reward.Repeatable = true;
SoomlaVungle.PlayAd(reward, true);
```

Note that in this example we use `VirtualItemReward` which is a special kind of Reward from [unity3d-store](https://github.com/soomla/unity3d-store).
