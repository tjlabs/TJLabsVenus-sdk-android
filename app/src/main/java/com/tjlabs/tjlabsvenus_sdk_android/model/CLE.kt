package com.tjlabs.tjlabsvenus_sdk_android.model

data class CoarseLocationEstInput (
    val tenant_user_name : String = "",
    val mobile_time : Long = 0L,
    val sector_id : Int = 0,
    val operating_system : String = "",
    val dead_reckoning : String = "pdr",
    val search_direction_list : List<Int> = listOf(),
    val normalization_scale : Float = 0f,
    val device_min_rssi : Float = 0f
)

data class CoarseLocationEstOutput (
    val mobile_time: Long = 0L,
    val building_name : String = "",
    val level_name : String = "",
    val scc : Float = 0f,
    val scr : Float = 0f,
    val ccs : Float = 0f,
    val x : Int = 0,
    val y : Int = 0,
    val calculated_time : Float = 0f,
    val ratio : Float = 0f
)
