package com.tjlabs.tjlabsvenus_sdk_android.calc

import com.tjlabs.tjlabsvenus_sdk_android.model.CoarseLocationEstInput
import com.tjlabs.tjlabsvenus_sdk_android.model.CoarseLocationEstOutput
import com.tjlabs.tjlabsvenus_sdk_android.network.JupiterNetworkConstants
import com.tjlabs.tjlabsvenus_sdk_android.network.JupiterNetworkManager

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
}