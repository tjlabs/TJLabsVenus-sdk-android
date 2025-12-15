package com.tjlabs.tjlabsvenus_sdk_android.api

import com.tjlabs.tjlabscommon_sdk_android.rfd.ReceivedForce
import com.tjlabs.tjlabscommon_sdk_android.uvd.UserVelocity
import com.tjlabs.tjlabsvenus_sdk_android.model.CoarseLocationEstInput
import com.tjlabs.tjlabsvenus_sdk_android.model.CoarseLocationEstOutput
import com.tjlabs.tjlabsvenus_sdk_android.model.OnSpotAuthorizationInput
import com.tjlabs.tjlabsvenus_sdk_android.model.OnSpotAuthorizationOutput
import com.tjlabs.tjlabsvenus_sdk_android.model.UserLoginInput
import com.tjlabs.tjlabsvenus_sdk_android.model.UserLoginOutput
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

internal interface PostInput {
    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )

    @POST("/{user_version}/tenants/user")
    fun postUserLogin(@Body param: UserLoginInput, @Path("user_version") userVersion : String) : Call<UserLoginOutput>

    @POST("/{rec_server}/received-force")
    fun postReceivedForce(@Body param: List<ReceivedForce>, @Path("rec_server") recVersion : String) : Call<Any>

    @POST("/{calc_version}/cle")
    fun postCLE(@Body param : CoarseLocationEstInput, @Path("calc_version") calc_version: String) : Call<CoarseLocationEstOutput>

    @POST("/{calc_version}/osa")
    fun postOSA(@Body param : OnSpotAuthorizationInput, @Path("calc_version") calc_version: String) : Call<ResponseBody>

}