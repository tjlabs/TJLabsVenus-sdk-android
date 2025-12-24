package com.tjlabs.tjlabsvenus_sdk_android.calc

import android.util.Log
import com.tjlabs.tjlabsvenus_sdk_android.model.CoarseLocationEstInput
import com.tjlabs.tjlabsvenus_sdk_android.model.CoarseLocationEstOutput
import com.tjlabs.tjlabsvenus_sdk_android.model.OnSpotAuthorizationInput
import com.tjlabs.tjlabsvenus_sdk_android.model.OnSpotAuthorizationOutput
import com.tjlabs.tjlabsvenus_sdk_android.model.Spot
import com.tjlabs.tjlabsvenus_sdk_android.network.JupiterNetworkConstants
import com.tjlabs.tjlabsvenus_sdk_android.network.JupiterNetworkManager
import tjlabs.android.jupiter_android_v2.data.spots

internal object CLECalculator {
    fun calculation(
        input: CoarseLocationEstInput,
        completion: (Int, CoarseLocationEstOutput) -> Unit
    ) {
        val cleInput = input.copy()
        JupiterNetworkManager.postCLE(
            JupiterNetworkConstants.getCalcBaseURL(),
            cleInput,
            JupiterNetworkConstants.getCLEServerVersion(),
        )
        { statusCode, result ->
            completion(statusCode, result)
        }
    }

    fun calculationOsa(
        input: OnSpotAuthorizationInput, ratioTh : Float,
        completion: (Int, CoarseLocationEstOutput) -> Unit
    ) {
        val osaInput = input.copy()
        JupiterNetworkManager.postOSA(
            JupiterNetworkConstants.getCalcBaseURL(),
            osaInput,
            JupiterNetworkConstants.getOSAServerVersion(),
        )
        { statusCode, result ->
            if (statusCode == 200) {
                val spots = result.spots
                val sorted = spots.sortedByDescending { it.ccs }
                Log.d("VenusServiceResult", "spots : $spots ")

                if (spots.size > 1) {
                    val first = sorted[0]
                    val second = sorted[1]

                    val ratio = second.ccs / first.ccs

                    Log.d("VenusServiceResult", "spots : $spots // ratio : $ratioTh // $ratio ")

                    if (ratio <= ratioTh) {
                        completion(statusCode, CoarseLocationEstOutput(
                            mobile_time = first.mobile_time,
                            building_name = first.building_name,
                            level_name = first.level_name,
                            x = first.x,
                            y = first.y,
                            calculated_time = first.calculated_time,
                            ratio = ratio
                        ))
                    } else {
                        completion(statusCode, CoarseLocationEstOutput(
                            mobile_time = first.mobile_time,
                            building_name = first.building_name,
                            level_name = first.level_name,
                            x = first.x,
                            y = first.y,
                            calculated_time = first.calculated_time,
                            ratio = ratio))
                    }
                } else if (spots.size == 1) {
                    val first = sorted[0]
                    completion(statusCode, CoarseLocationEstOutput(
                        mobile_time = first.mobile_time,
                        building_name = first.building_name,
                        level_name = first.level_name,
                        x = first.x,
                        y = first.y,
                        calculated_time = first.calculated_time,
                    ))
                } else {
                    completion(statusCode, CoarseLocationEstOutput(
                        x = -1,
                        y = -1))
                }
            } else {
                completion(statusCode, CoarseLocationEstOutput(
                    x = -1,
                    y = -1))
            }
        }
    }
}