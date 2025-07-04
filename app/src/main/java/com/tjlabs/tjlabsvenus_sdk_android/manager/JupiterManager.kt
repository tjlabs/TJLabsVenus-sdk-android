package com.tjlabs.tjlabsvenus_sdk_android.manager

import android.app.Application
import com.tjlabs.tjlabscommon_sdk_android.uvd.UserMode
import com.tjlabs.tjlabsvenus_sdk_android.callback.VenusCallback
import com.tjlabs.tjlabsvenus_sdk_android.model.JupiterRegion
import com.tjlabs.tjlabsvenus_sdk_android.model.CoarseLocationEstOutput

class JupiterManager(application: Application, tenantUserId : String) {
    private val cleManager = CLEManager(application, tenantUserId)

    fun startCoarseLocationEstimation(region: String = JupiterRegion.KOREA, sectorId : Int, callback: VenusCallback) {
        cleManager.startService(region, sectorId, callback)
    }

    fun stopCoarseLocationEstimation() {
        cleManager.stopService()
    }

    fun getCoarseLocationTrackingEstimation(mode : UserMode, completion: (Int, CoarseLocationEstOutput) -> Unit) {
        cleManager.getVenusResult(mode) { statusCode, result ->
            completion(statusCode, result)
        }
    }

    fun setCLESendRfdLength(length : Int = 2) {
        cleManager.setSendRfdLength(length)
    }

    fun setCLESendUvdLength(length: Int = 4) {
        cleManager.setSendUvdLength(length)
    }



}