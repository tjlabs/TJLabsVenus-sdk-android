package com.tjlabs.tjlabsvenus_sdk_android.manager

import android.app.Application
import android.os.Build
import com.tjlabs.tjlabsvenus_sdk_android.BuildConfig
import com.tjlabs.tjlabscommon_sdk_android.uvd.UserMode
import com.tjlabs.tjlabsvenus_sdk_android.network.JupiterNetworkManager.isConnectedToInternet
import com.tjlabs.tjlabsvenus_sdk_android.network.JupiterNetworkManager.postUserLogin
import com.tjlabs.tjlabsvenus_sdk_android.calc.CLECalcManager
import com.tjlabs.tjlabsvenus_sdk_android.callback.VenusCallback
import com.tjlabs.tjlabsvenus_sdk_android.network.JupiterNetworkConstants.getUserBaseURL
import com.tjlabs.tjlabsvenus_sdk_android.network.JupiterNetworkConstants.getUserLoginVersion
import com.tjlabs.tjlabsvenus_sdk_android.network.JupiterNetworkConstants.setServerURL
import com.tjlabs.tjlabsvenus_sdk_android.model.JupiterRegion
import com.tjlabs.tjlabsvenus_sdk_android.model.CoarseLocationEstOutput
import com.tjlabs.tjlabsvenus_sdk_android.model.OnSpotAuthorizationOutput
import com.tjlabs.tjlabsvenus_sdk_android.model.Spot
import com.tjlabs.tjlabsvenus_sdk_android.until.TJUtilFunctions

internal class CLEManager(private val application: Application, private val tenantUserId : String) {
    private lateinit var venusCalcManager : CLECalcManager

    private var isStartService = false
    private var deviceModel = Build.MODEL
    private var deviceIdentifier = Build.MANUFACTURER
    private var deviceOsVersion = Build.VERSION.SDK_INT

    private var sendRfdLength = 2
    private var sendUvdLength = 4

    private val sdkVersion: String = BuildConfig.TJLABS_SDK_VERSION

    fun startService(region: String = JupiterRegion.KOREA, sectorId : Int, callback: VenusCallback) {
        val (isNetworkAvailable, msgCheckNetworkAvailable) = isConnectedToInternet(application)
        val (isIdAvailable, msgCheckIdAvailable) = TJUtilFunctions.checkIdIsAvailable(tenantUserId)

        if (!isNetworkAvailable) {
            callback.onVenusSuccess(false)
            callback.onVenusError(0, msgCheckNetworkAvailable)
            return
        }

        if (!isIdAvailable) {
            callback.onVenusSuccess(false)
            callback.onVenusError(0, msgCheckIdAvailable)
            return
        }

        if (isStartService) {
            callback.onVenusSuccess(false)
            callback.onVenusError(0, "The service is already starting.")
            return
        }

        setServerURL(region)

        val tasks = listOf<(completion: (String, Int, Any, String) -> Unit) -> Unit> { completion ->
            postUserLogin(url = getUserBaseURL(), id = tenantUserId, deviceModel = deviceModel, osVersion = deviceOsVersion,
                sdkVersion = sdkVersion, userServerVersion = getUserLoginVersion(), completion = completion)
        }

        TJUtilFunctions.performTasksWithCounter(tasks,
            onComplete = {
                isStartService = true
                venusCalcManager = CLECalcManager(application, region, tenantUserId, sectorId)
                venusCalcManager.setSendRfdLength(sendRfdLength)
                startGenerator {
                        isSuccess, msg ->
                    if (isSuccess) {
                        callback.onVenusSuccess(true)
                    } else {
                        callback.onVenusSuccess(false)
                        callback.onVenusError(0, msg)
                    }
                }
            },
            onError = { msg ->
                callback.onVenusSuccess(false)
                callback.onVenusError(0, msg)
            }
        )
    }

    fun getVenusResult(mode : UserMode, completion: (Int, CoarseLocationEstOutput) -> Unit) {
        venusCalcManager.calcVenusResult(mode) { statusCode, result ->
            completion(statusCode, result)
        }
    }

    fun getOsaResult(mode : UserMode, ratio : Float, completion: (Int, CoarseLocationEstOutput) -> Unit) {
        venusCalcManager.calcOsaResult(mode) { statusCode, result ->
            completion(statusCode, result)
        }
    }

    fun stopService() {
        stopGenerator()
    }

    fun setSendRfdLength(length : Int = 2) {
        sendRfdLength = length
    }

    fun setSendUvdLength(length : Int = 4) {
        sendUvdLength = length
    }


    private fun startGenerator(completion : (Boolean, String) -> Unit ) {
        venusCalcManager.startGenerator {
                isSuccess, msg ->
            completion(isSuccess, msg)
        }
    }

    private fun stopGenerator() {
        if (isStartService) {
            venusCalcManager.stopGenerator()
            isStartService = false
        }
    }
}