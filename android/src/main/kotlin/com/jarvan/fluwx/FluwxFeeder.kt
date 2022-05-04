package com.jarvan.fluwx

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import com.jarvan.fluwx.handlers.FluwxRequestHandler
import com.jarvan.fluwx.handlers.FluwxResponseHandler
import com.jarvan.fluwx.handlers.WXAPiHandler
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import io.flutter.Log

object FluwxFeeder {

    fun mayInitialWXApi(activity: Activity) {
        if (!WXAPiHandler.wxApiRegistered) {
            val appInfo = activity.packageManager.getApplicationInfo(
                activity.packageName,
                PackageManager.GET_META_DATA
            )
            val wechatAppId = appInfo.metaData.getString("weChatAppId")
            if (wechatAppId != null) {
                WXAPiHandler.setupWxApi(wechatAppId, activity)
                WXAPiHandler.coolBoot = true
                Log.d("FluwxFeeder", "weChatAppId: $wechatAppId")
            } else {
                Log.e("FluwxFeeder", "can't load meta-data weChatAppId")
            }
        }
    }

    fun handleIntent(intent: Intent, handler: IWXAPIEventHandler) {
        WXAPiHandler.wxApi?.handleIntent(intent, handler)
    }

    fun onReq(reqInfo: BaseReq, activity: Activity) {
        FluwxRequestHandler.onReq(reqInfo, activity)
    }

    fun onResp(respInfo: BaseResp) {
        FluwxResponseHandler.handleResponse(respInfo)
    }

}