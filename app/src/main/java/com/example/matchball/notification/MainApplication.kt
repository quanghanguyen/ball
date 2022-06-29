package com.example.matchball.notification

import android.app.Application
import com.onesignal.OneSignal

class MainApplication : Application() {

    val ONESIGNAL_APP_ID = "941305e5-eeea-4475-baba-429118a8e62c"

    override fun onCreate() {
        super.onCreate()

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }

}