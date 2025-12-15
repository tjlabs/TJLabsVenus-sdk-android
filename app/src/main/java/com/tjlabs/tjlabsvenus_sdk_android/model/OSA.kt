package com.tjlabs.tjlabsvenus_sdk_android.model

data class OnSpotAuthorizationInput(
    val tenant_user_name : String = "",
    val mobile_time : Long = 0L,
    val operating_system : String = "",
)


data class OnSpotAuthorizationOutput (
    val spots : List<Spot> = listOf()
)

data class Spot(
    val mobile_time: Long = 0L,
    val sector_name: String = "",
    val building_name: String = "",
    val level_name: String = "",
    val id: Int = 0,
    val number: Int = 0,
    val name: String = "",
    val x: Int = 0,
    val y: Int = 0,
    val ccs: Float = 1f,
    val calculated_time: Float = 0f
)