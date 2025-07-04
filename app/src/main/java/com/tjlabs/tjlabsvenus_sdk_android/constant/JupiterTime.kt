package com.tjlabs.tjlabsjupiter_sdk_android.data.constant

internal object JupiterTime {
    const val SECOND_TO_MILLIS = 1000
    const val OUTPUT_INTERVAL: Float = 1f / 5f * SECOND_TO_MILLIS// second
    const val OSR_INTERVAL: Float = 2f * SECOND_TO_MILLIS // second
    const val RFD_INTERVAL = 500L // millis
    const val BLE_SCAN_WINDOW_TIME_MILLIS = 1000L // millis
    const val MINIMUM_BUILDING_LEVEL_CHANGE_TIME = 7000
    const val TIME_INIT_THRESHOLD: Float = 25f * SECOND_TO_MILLIS// seconds
    const val TIME_INIT: Float = TIME_INIT_THRESHOLD + SECOND_TO_MILLIS

}