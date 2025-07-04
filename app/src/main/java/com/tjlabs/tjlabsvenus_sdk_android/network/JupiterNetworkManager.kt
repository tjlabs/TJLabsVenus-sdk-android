package com.tjlabs.tjlabsvenus_sdk_android.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.tjlabs.tjlabscommon_sdk_android.rfd.ReceivedForce
import com.tjlabs.tjlabscommon_sdk_android.uvd.UserVelocity
import com.tjlabs.tjlabsvenus_sdk_android.api.PostInput
import com.tjlabs.tjlabsvenus_sdk_android.model.CoarseLocationEstInput
import com.tjlabs.tjlabsvenus_sdk_android.model.CoarseLocationEstOutput
import com.tjlabs.tjlabsvenus_sdk_android.model.Sector
import com.tjlabs.tjlabsvenus_sdk_android.model.UserLoginInput
import com.tjlabs.tjlabsvenus_sdk_android.model.UserLoginOutput
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val TAG_USER_LOGIN = "user-login"
internal object JupiterNetworkManager {
    fun isConnectedToInternet(application : Application): Pair<Boolean, String> {
        val connectivityManager = application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return if (networkCapabilities != null &&  (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))) {
            Pair(true, "")
        } else {
            Pair(false, "Network Connection Fail, Check Wifi of Cellular connection")

        }
    }

    fun postUserLogin(url : String = "", id : String = "", deviceModel : String = "", osVersion : Int =0,
                      sdkVersion : String = "", userServerVersion : String, completion: (String, Int, List<Sector>, String) -> Unit) {
        val loginInput = UserLoginInput(id, deviceModel, osVersion, sdkVersion)
        val retrofit = JupiterNetworkConstants.genRetrofit(url)
        val postUserLoginInput = retrofit.create(PostInput::class.java)
        postUserLoginInput.postUserLogin(loginInput, userServerVersion).enqueue(object : Callback<UserLoginOutput> {
            override fun onFailure(call: Call<UserLoginOutput>, t: Throwable) {
                completion(TAG_USER_LOGIN, 500, listOf(), "fail")
            }

            override fun onResponse(call: Call<UserLoginOutput>, response: Response<UserLoginOutput>) {
                val statusCode = response.code()
                Log.d("VenusServiceResult", "statusCode : $statusCode // request : ${call.request()}")

                if (statusCode in 200 until 300) {
                    val result = response.body()
                    if (result != null) {
                        val userSectors = result.sectors
                        Log.d("VenusServiceResult", "postUserLogin code : $statusCode // userSectors : $userSectors")
                        completion(TAG_USER_LOGIN, statusCode, userSectors, "success")
                    }
                } else {
                    completion(TAG_USER_LOGIN, statusCode, listOf(), "fail")
                }
            }
        })
    }



    fun postReceivedForce(url: String, input: List<ReceivedForce>, recServerVersion: String, completion: (Int, String) -> Unit) {
        val retrofit = JupiterNetworkConstants.genRetrofit(url)
        val postReceivedForce = retrofit.create(PostInput::class.java)
        Log.d("VenusServiceResult", "input : $input // recServerVersion : $recServerVersion // url : $url")
        postReceivedForce.postReceivedForce(input, recServerVersion).enqueue(object :
            Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                completion(500, t.localizedMessage ?: "Fail")
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                val statusCode = response.code()
                if (statusCode in 200 until 300) {
                    val resultData = response.body()?.toString() ?: ""
                    completion(statusCode, resultData)
                } else {
                    completion(500, response.message())
                }
            }
        })
    }

    fun postCLE(url : String, input: CoarseLocationEstInput, cleServerVersion: String, completion: (Int, CoarseLocationEstOutput) -> Unit){
        val retrofit = JupiterNetworkConstants.genRetrofit(url)
        val postCLE = retrofit.create(PostInput::class.java)
        postCLE.postCLE(input, cleServerVersion).enqueue(object : Callback<CoarseLocationEstOutput> {
            override fun onFailure(call: Call<CoarseLocationEstOutput>, t: Throwable) {
                completion(501, CoarseLocationEstOutput())
            }
            override fun onResponse(call: Call<CoarseLocationEstOutput>, response: Response<CoarseLocationEstOutput>) {
                val statusCode = response.code()
                Log.d("VenusServiceResult", "call : ${call.request()}")
                Log.d("VenusServiceResult", "statusCode : $statusCode")

                if (statusCode in 200 until 300) {
                    val cleResult = response.body()
                    if (cleResult != null) {
                        completion(statusCode, cleResult)
                    } else {
                        completion(500, CoarseLocationEstOutput())
                    }
                } else {
                    completion(500, CoarseLocationEstOutput())
                }
            }

        })
    }
}