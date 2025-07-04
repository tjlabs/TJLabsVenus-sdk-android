package com.tjlabs.tjlabsvenus_sdk_android.model


data class UserLoginInput(
    var name: String = "",
    var device_model: String = "",
    var os_version: Int = 0,
    var sdk_version: String = ""
)

data class UserLoginOutput(
    val message : String = "",
    val sectors : List<Sector> = listOf()
)

data class Sector(
    val sector_id : Int = 0,
    val sector_name : String = "",
    val description : String = "",
    val card_color : String = "",
    val dead_reckoning : String ="",
    val request_service : String = "",
    val building_level : List<List<String>> = listOf(listOf())
)
