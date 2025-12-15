package com.tjlabs.tjlabsvenus_sdk_android.calc

import android.app.Application
import android.util.Log
import com.tjlabs.tjlabscommon_sdk_android.rfd.RFDGenerator
import com.tjlabs.tjlabscommon_sdk_android.rfd.ReceivedForce
import com.tjlabs.tjlabscommon_sdk_android.uvd.UserMode
import com.tjlabs.tjlabsjupiter_sdk_android.data.constant.JupiterTime.BLE_SCAN_WINDOW_TIME_MILLIS
import com.tjlabs.tjlabsjupiter_sdk_android.data.constant.JupiterTime.RFD_INTERVAL
import com.tjlabs.tjlabsvenus_sdk_android.model.CoarseLocationEstInput
import com.tjlabs.tjlabsvenus_sdk_android.model.CoarseLocationEstOutput
import com.tjlabs.tjlabsvenus_sdk_android.model.OnSpotAuthorizationInput
import com.tjlabs.tjlabsvenus_sdk_android.model.OnSpotAuthorizationOutput
import com.tjlabs.tjlabsvenus_sdk_android.model.Spot

internal class CLECalcManager(private val application: Application, private val region : String, userIdInput :String, sectorIdInput : Int) : RFDGenerator.RFDCallback
{
    private var id = ""
    private var sectorId = 0
    private var os = "Android"
    private var phase: Int = 1

    private var currentVelocity = 0f
    private var currentUserMode = UserMode.MODE_PEDESTRIAN
    private var pressure = 0f
    private var rfdEmptyMillis = 0L

    private lateinit var rfdGenerator : RFDGenerator

    init {
        id = userIdInput
        sectorId = sectorIdInput
    }

    fun setSendRfdLength(length : Int = 2) {
        JupiterDataBatchSender.sendRfdLength = length
    }

    fun startGenerator(completion: (Boolean, String) -> Unit) {
        rfdGenerator = RFDGenerator(application, id)
        rfdGenerator.checkIsAvailableRfd(this) { isRfdSuccess, message ->
            if (isRfdSuccess) {
//                rfdGenerator.generateSimulationRfd(
//                    RFD_INTERVAL, BLE_SCAN_WINDOW_TIME_MILLIS, -100,
//                    0, getPressure = { pressure }, baseFileName ="KR_SENSOR_WARD_20251211_144833", this)

                rfdGenerator.generateRfd(
                    RFD_INTERVAL, BLE_SCAN_WINDOW_TIME_MILLIS, -100,
                    0, getPressure = { pressure }, isSaveData = false, "aos_ble", this)

                completion(true, message)
            } else {
                completion(false, message)
            }
        }
    }

    fun calcVenusResult(mode: UserMode, completion: (Int, CoarseLocationEstOutput) -> Unit) {
        var modeStr = "PDR"
        if (mode == UserMode.MODE_VEHICLE) {
            modeStr = "DR"
        }

        val cleInput = CoarseLocationEstInput(
            tenant_user_name = id,
            mobile_time = System.currentTimeMillis(),
            sector_id = sectorId,
            operating_system = "Android",
            dead_reckoning = modeStr,
            search_direction_list = listOf(0, 90, 180, 270),
            normalization_scale = 1f,
            device_min_rssi = -95f
        )

        CLECalculator.calculation(cleInput) { statusCode, result ->
            completion(statusCode, result)
        }
    }


    fun calcOsaResult(mode: UserMode, ratio : Float = 0.7f, completion: (Int, CoarseLocationEstOutput) -> Unit) {
        val cleInput = OnSpotAuthorizationInput(
            tenant_user_name = id,
            mobile_time = System.currentTimeMillis(),
            operating_system = "Android",
        )

        CLECalculator.calculationOsa(cleInput, ratio) { statusCode, result ->
            completion(statusCode, result)
        }
    }


    fun stopGenerator() {
        rfdGenerator.stopRfdGeneration()
    }

    override fun onRfdEmptyMillis(time: Long) {
        rfdEmptyMillis = time
    }

    override fun onRfdError(code: Int, msg: String) {
    }

    override fun onRfdResult(rfd: ReceivedForce) {
        Log.d("VenusServiceResult", "onRfdResult : $rfd")
        JupiterDataBatchSender.sendRfd(rfd)
    }
}