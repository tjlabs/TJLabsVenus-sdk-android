package com.tjlabs.tjlabsvenus_sdk_android.callback

interface VenusCallback {
    fun onVenusSuccess(success: Boolean)

    fun onVenusError(code: Int, msg: String)
}