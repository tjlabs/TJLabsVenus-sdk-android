package com.tjlabs.tjlabsvenus_sdk_android.network

import TokenResult
import android.util.Log
import com.tjlabs.tjlabsauth_sdk_android.TJLabsAuthManager
import com.tjlabs.tjlabsvenus_sdk_android.model.JupiterRegion
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import java.io.IOException


const val TIMEOUT_VALUE_PUT = 5L

internal object JupiterNetworkConstants {
    private const val USER_LOGIN_SERVER_VERSION = "2025-03-26"
    private const val USER_RC_SERVER_VERSION = "2024-06-12"

    private const val REC_RFD_SERVER_VERSION = "2025-04-02"
    private const val REC_UVD_SERVER_VERSION = "2025-04-02"
    private const val REC_USER_MASK_SERVER_VERSION = "2025-04-02"
    private const val REC_MOBILE_RESULT_SERVER_VERSION = "2025-04-02"


    private const val CALC_CLE_SERVER_VERSION = "2025-06-04"
    private const val CALC_FLT_SERVER_VERSION = "2024-12-12"
    private const val CALC_OSR_SERVER_VERSION = "2024-08-30"
    private const val CALC_OSA_SERVER_VERSION = "2025-12-19"

    private const val HTTP_PREFIX = "https://"
    private var REGION_PREFIX = "ap-northeast-2."
    private const val JUPITER_SUFFIX = ".jupiter.tjlabs.dev"
    private var REGION_NAME = "Korea"
    private var USER_URL = HTTP_PREFIX + REGION_PREFIX + "user" + JUPITER_SUFFIX
    private var IMAGE_URL =  HTTP_PREFIX + REGION_PREFIX + "img" + JUPITER_SUFFIX
    private var CSV_URL =  HTTP_PREFIX + REGION_PREFIX + "csv" + JUPITER_SUFFIX
    private var REC_URL =  HTTP_PREFIX + REGION_PREFIX + "rec" + JUPITER_SUFFIX
    private var CALC_URL =  HTTP_PREFIX + REGION_PREFIX + "calc" + JUPITER_SUFFIX
    private var CLIENT_URL =  HTTP_PREFIX + REGION_PREFIX + "client" + JUPITER_SUFFIX

    private var token = ""

    fun genRetrofit(url : String) : Retrofit {
        TJLabsAuthManager.getAccessToken() {
            tokenResult ->
            when(tokenResult) {
                is TokenResult.Success -> {
                    token = tokenResult.token
                    Log.d("VenusServiceResult", "token : $token")
                }

                is TokenResult.Failure -> {

                }
            }
        }

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_VALUE_PUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_VALUE_PUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_VALUE_PUT, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor(token))
            .build()

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun setServerURL(region: String) {
        when (region) {
            JupiterRegion.KOREA -> {
                REGION_PREFIX = "ap-northeast-2."
                REGION_NAME = "Korea"
            }
            JupiterRegion.CANADA -> {
                REGION_PREFIX = "ca-central-1."
                REGION_NAME = "Canada"
            }
            JupiterRegion.US_EAST -> {
                REGION_PREFIX = "us-east-1."
                REGION_NAME = "US"
            }
        }

        USER_URL = HTTP_PREFIX + REGION_PREFIX + "user" + JUPITER_SUFFIX
        IMAGE_URL = HTTP_PREFIX + REGION_PREFIX + "img" + JUPITER_SUFFIX
        CSV_URL = HTTP_PREFIX + REGION_PREFIX + "csv" + JUPITER_SUFFIX
        REC_URL =  HTTP_PREFIX + REGION_PREFIX + "rec" + JUPITER_SUFFIX
        CALC_URL =  HTTP_PREFIX + REGION_PREFIX + "calc" + JUPITER_SUFFIX
        CLIENT_URL =  HTTP_PREFIX + REGION_PREFIX + "client" + JUPITER_SUFFIX
    }

    fun getUserLoginVersion(): String = USER_LOGIN_SERVER_VERSION

    fun getUserRcVersion(): String = USER_RC_SERVER_VERSION

    fun getUserBaseURL(): String = USER_URL

    fun getRecBaseURL(): String = REC_URL

    fun getRecRfdServerVersion(): String = REC_RFD_SERVER_VERSION

    fun getRecUvdServerVersion(): String = REC_UVD_SERVER_VERSION

    fun getRecUserMaskServerVersion(): String = REC_USER_MASK_SERVER_VERSION

    fun getRecMobileResultServerVersion(): String = REC_MOBILE_RESULT_SERVER_VERSION

    fun getClientBaseURL(): String = CLIENT_URL

    fun getCalcBaseURL(): String = CALC_URL

    fun getCLEServerVersion(): String = CALC_CLE_SERVER_VERSION

    fun getFLTServerVersion(): String = CALC_FLT_SERVER_VERSION

    fun getOSRServerVersion(): String = CALC_OSR_SERVER_VERSION

    fun getOSAServerVersion() : String = CALC_OSA_SERVER_VERSION
    
    class HeaderInterceptor (private val token: String) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val token = "Bearer $token"
            val newRequest = chain.request().newBuilder()
                .addHeader("authorization", token)
                .build()
            return chain.proceed(newRequest)
        }
    }
}
