package com.tjlabs.tjlabsvenus_sdk_android.calc

import com.tjlabs.tjlabscommon_sdk_android.rfd.ReceivedForce
import com.tjlabs.tjlabsvenus_sdk_android.network.JupiterNetworkConstants.getRecBaseURL
import com.tjlabs.tjlabsvenus_sdk_android.network.JupiterNetworkConstants.getRecRfdServerVersion
import com.tjlabs.tjlabsvenus_sdk_android.network.JupiterNetworkManager.postReceivedForce

internal object JupiterDataBatchSender {
    private val inputReceivedForceList: MutableList<ReceivedForce> = mutableListOf()

    var sendRfdLength = 2

    fun sendRfd(rfd : ReceivedForce) {
        inputReceivedForceList.add(rfd)
        if (inputReceivedForceList.size >= sendRfdLength && rfd.rfs.isNotEmpty()) {
            postReceivedForce(getRecBaseURL(), inputReceivedForceList.toList(), getRecRfdServerVersion()) {
                    statusCode, returnedString ->
            }
            inputReceivedForceList.clear()
        }
    }
}