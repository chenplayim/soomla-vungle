LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := cocos2dx_vungle_static
LOCAL_MODULE_FILENAME := libcocos2dxvungle
LOCAL_SRC_FILES := ../../Vungle/CCVungleService.cpp

LOCAL_C_INCLUDES := $(LOCAL_PATH)/../../Vungle

LOCAL_WHOLE_STATIC_LIBRARIES += cocos2dx_static
LOCAL_WHOLE_STATIC_LIBRARIES += cocos2dx_soomla_common_static

LOCAL_EXPORT_C_INCLUDES += $(LOCAL_PATH)/../../Vungle

include $(BUILD_STATIC_LIBRARY)

$(call import-module,extensions/soomla-cocos2dx-core/android/jni)
