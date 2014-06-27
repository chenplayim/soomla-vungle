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
	/// This class provides the API for working with the SOOMLA Vungle plugin.
	/// Use it to initialize Vungle and play video ads in return for rewards.
	/// It must be initialized after <c>SoomlaStore</c>.
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
		/// Initializes the SOOMLA Vungle plugin.
		/// </summary>
		public static void Initialize() {

#if UNITY_ANDROID && !UNITY_EDITOR
			SoomlaUtils.LogDebug(TAG, "Starting Vungle for: " + VungleSettings.VungleAppIdAndroid);
			AndroidJNI.PushLocalFrame(100);
			using(AndroidJavaClass jniSoomlaVungleClass = new AndroidJavaClass("com.soomla.plugins.ads.vungle.SoomlaVungle")) {
				jniSoomlaVungle = jniSoomlaVungleClass.CallStatic<AndroidJavaObject>("getInstance");
				jniSoomlaVungle.Call("initialize", VungleSettings.VungleAppIdAndroid);
			}
			AndroidJNI.PopLocalFrame(IntPtr.Zero);
#elif UNITY_IOS && !UNITY_EDITOR
			SoomlaUtils.LogDebug(TAG, "Starting Vungle for: " + VungleSettings.VungleAppIdiOS);
			soomlaVungle_Init(VungleSettings.VungleAppIdiOS);
#endif
		}

		/// <summary>
		/// Plays a video ad and grants the user a reward for watching it.
		/// </summary>
		/// <param name="reward">The reward that will be given to users for watching the video ad.</param>
		/// <param name="enableBackButton">Determines whether you would like to give the user the
		/// option to skip out of the video. <c>true</c> means a close button will be displayed.</param>
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
