package com.tjlabs.tjlabsjupiter_sdk_android.data.constant

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

internal object JupiterMyApplication {
    private lateinit var mApplication: Application
    private lateinit var mApplicationContext: Context
    private lateinit var mSharedPreference: SharedPreferences

    fun initialize(app: Application) {
        mApplication = app
        mApplicationContext = mApplication.applicationContext
        mSharedPreference = mApplicationContext.getSharedPreferences("TJLabsCache", 0)
    }

    fun getApplication(): Application {
        if (!::mApplication.isInitialized) {
            throw IllegalStateException("MySDK must be initialized before accessing the application context")
        }
        return mApplication
    }

    fun getApplicationContext(): Context {
        if (!::mApplicationContext.isInitialized) {
            throw IllegalStateException("MySDK must be initialized before accessing the application context")
        }
        return mApplicationContext
    }

    fun getSharedPreference(): SharedPreferences {
        if (!::mSharedPreference.isInitialized) {
            throw IllegalStateException("MySDK must be initialized before accessing the application context")
        }
        return mSharedPreference
    }


}