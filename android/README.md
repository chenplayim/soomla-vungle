This project works alongside with SOOMLA for Android.

* If you haven't already done so, head over to Vungle's [dashboard](https://v.vungle.com/dashboard/login) and add your app to your account. You need to do this so that you can get your App ID that you’ll be initializing SoomlaVungle with. It’s in **red** on your app’s page.


## Getting Started - Android

1. All the jars you need are in [build](https://github.com/soomla/soomla-vungle/tree/master/android/build) folder. Add them to your project.

2. Make the following changes to your AndroidManifest.xml:

    ```xml
      <manifest>

        ...

        <!-- permissions to download and cache video ads for playback -->
        <uses-permission android:name="android.permission.INTERNET" />
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

        <application ...
                     android:name="com.soomla.SoomlaApp">

          ...

          <!--
            Required Activity for playback of Vungle video ads
          -->
          <activity
            android:name="com.vungle.publisher.FullScreenAdActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
          />


          <service android:name="com.vungle.publisher.VungleService"
            android:exported="false"
          />

        </application>

      </manifest>
    ```

3. Initialize **Soomla** with a secret that you choose to encrypt the user data for SOOMLA. Initialize SoomlaVungle with the appId from Vungle Dashboard:

    ```Java
     Soomla.initialize("[YOUR CUSTOM GAME SECRET HERE]");
     SoomlaVungle.getInstance().initialize("[YOUR VUNGLE APP ID]");
    ```
    > The secret is your encryption secret for data saved in the DB.

4. Vungle requires that you override the `onPause` and `onResume` methods in each `Activity` (including the first) to notify the Publisher SDK when your application gains or loses focus. Do it on SoomlaVungle:

    ```java
    import com.vungle.publisher.VunglePub;

    public class EachActivity extends android.app.Activity {

      @Override
      protected void onPause() {
          super.onPause();
          SoomlaVungle.getInstance().onPause();
      }

      @Override
      protected void onResume() {
          super.onResume();
          SoomlaVungle.getInstance().onResume();
      }
    }
```

## Playing an Ad

To play a Vungle Ad, just call the "playAd" function on SoomlaVungle. Here is an example of how it's done:

``` Java
SoomlaVungle.getInstance().playAd(true, true, new VirtualItemReward("muffins_for_ad", "Muffins for watching Ad", 1232, "currency_muffin"));
```

Note that in this example we use `VirtualItemReward` which is a special kind of Reward from [android-store](https://github.com/soomla/android-store).
