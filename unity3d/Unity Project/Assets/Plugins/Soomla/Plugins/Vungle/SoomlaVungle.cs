/// Copyright (C) 2012-2014 Soomla Inc.
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///      http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.

using UnityEngine;
using System;
using System.Runtime.InteropServices;

namespace Soomla.Vungle
{
	/// <summary>
	/// This class holds the basic assets needed to operate the Store.
	/// You can use it to purchase products from the mobile store.
	/// This is the only class you need to initialize in order to use the SOOMLA SDK.
	/// </summary>
	public class SoomlaVungle
	{
#if UNITY_IOS && !UNITY_EDITOR
		[DllImport ("__Internal")]
		private static extern void soomlaVungle_Init(string appId);
		[DllImport ("__Internal")]
		private static extern void soomlaVungle_PlayAd(bool enableBackButton, string rewardJSON);
#endif

#if UNITY_ANDROID && !UNITY_EDITOR
		private static AndroidJavaObject jniSoomlaVungle = null;
#endif

		/// <summary>
		/// Initializes the SOOMLA Vungle SDK.
		/// </summary>
		public static void Initialize(string appId) {
			SoomlaUtils.LogDebug(TAG, "Starting Vungle for: " + appId);
#if UNITY_ANDROID && !UNITY_EDITOR
			AndroidJNI.PushLocalFrame(100);
			using(AndroidJavaClass jniSoomlaVungleClass = new AndroidJavaClass("com.soomla.plugins.ads.vungle.SoomlaVungle")) {
				jniSoomlaVungle = jniSoomlaVungleClass.CallStatic<AndroidJavaObject>("getInstance");
				jniSoomlaVungle.Call("initialize", appId);
			}
			AndroidJNI.PopLocalFrame(IntPtr.Zero);
#elif UNITY_IOS && !UNITY_EDITOR
			soomlaVungle_Init(appId);
#endif
		}

		/// <summary>
		/// Starts a purchase process in the market.
		/// </summary>
		/// <param name="productId">id of the item to buy.</param>
		/// <param name="payload">some text you want to get back when the purchasing process is completed. NOTE: This is not supported on iOS !</param>
		public static void PlayAd(Reward reward, bool enableBackButton) {
			SoomlaUtils.LogDebug(TAG, "Playing Vungle Ad");
#if UNITY_ANDROID && !UNITY_EDITOR
			AndroidJNI.PushLocalFrame(100);
			jniSoomlaVungle.Call("playIncentivisedAd", enableBackButton, true, (reward == null ? null : reward.toJNIObject()));
			AndroidJNI.PopLocalFrame(IntPtr.Zero);
#elif UNITY_IOS && !UNITY_EDITOR
			soomlaVungle_PlayAd(enableBackButton, (reward == null ? null : reward.toJSONObject().print()));
#endif
		}

		/// <summary> Class Members </summary>

		private const string TAG = "SOOMLA SoomlaVungle";

	}
}
