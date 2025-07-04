package com.tjlabs.tjlabsjupiter_sdk_android.data.constant

import com.tjlabs.tjlabsjupiter_sdk_android.data.constant.JupiterTime.SECOND_TO_MILLIS

internal object JupiterMode {
    //LT : Less Than
    //GTE : Greater Than or Equal
    const val RQ_IDX_PDR: Int = 4
    const val RQ_IDX_DR: Int = 10

    const val USER_TRAJECTORY_LENGTH_DR: Float = 60f
    const val USER_TRAJECTORY_LENGTH_PDR: Float = 20f

    const val REQUIRED_LENGTH_FOR_MAJOR_HEADING: Float = 10f

    const val UVD_INPUT_NUM_GTE_PHASE4_PDR: Int = 6
    const val INDEX_THRESHOLD_GTE_PDR = 21

    const val UVD_INPUT_NUM_LT_PHASE4_PDR: Int = 4
    const val INDEX_THRESHOLD_LT_PHASE4_PDR = 11


    const val UVD_INPUT_NUM_GTE_PHASE4_DR: Int = 10
    const val INDEX_THRESHOLD_GTE_DR = (UVD_INPUT_NUM_GTE_PHASE4_DR * 2) + 1 // 21

    const val UVD_INPUT_NUM_LT_PHASE4_DR: Int = 5
    const val INDEX_THRESHOLD_LT_PHASE4_DR = UVD_INPUT_NUM_LT_PHASE4_DR + 1 //6

    const val HEADING_RANGE: Float = 46f
    val DEFAULT_HEADINGS: List<Float> = listOf(0f, 90f, 180f, 270f)

    const val PADDING_VALUE_SMALL: Float = 10f
    const val PADDING_VALUE_LARGE: Float = 20f

    val PADDING_VALUES_PDR = List(4) { PADDING_VALUE_SMALL }
    val PADDING_VALUE_DR = List(4) { PADDING_VALUE_LARGE }

    const val DR_HEADING_CORR_NUM_IDX: Int = 10

    val HEADING_UNCERTANTIY: Float = 2.0f

    const val SLEEP_THRESHOLD: Float = 600f * SECOND_TO_MILLIS

}