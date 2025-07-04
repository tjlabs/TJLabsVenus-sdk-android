package com.tjlabs.tjlabsvenus_sdk_android.until

import android.content.SharedPreferences
import android.util.Log
import com.tjlabs.tjlabsvenus_sdk_android.network.TAG_USER_LOGIN


internal object TJUtilFunctions{
    fun checkIdIsAvailable(id : String) : Pair<Boolean, String> {
        if (id.isEmpty() || id.contains(" ")) {
            val msg = "(Olympus) Error : User ID(input = $id) cannot be empty or contain space"
            return Pair(false, msg)
        } else {
            return Pair(true, "")
        }
    }


    fun performTasksWithCounter(
        tasks: List<(completion: (String, Int, Any, String) -> Unit) -> Unit>,
        onComplete: () -> Unit, onError: (String) -> Unit
    ) {
        var completedCount = 0
        var isErrorOccurred = false

        for (task in tasks) {
            task.invoke { tag, success, result, msg ->
                synchronized(this) {
                    if (isErrorOccurred) return@synchronized
                    if (success != 200) {
                        isErrorOccurred = true
                        onError("tag : $tag // msg : $msg")
                    } else {
                        completedCount++
                        if (tag == TAG_USER_LOGIN) { taskForUserLogin(result) }

                        if (completedCount == tasks.size) {
                            onComplete()
                        }
                    }
                }
            }
        }
    }

    fun taskForUserLogin(result : Any) {
        Log.d("CheckToken", "taskForUserLogin : $result")
    }

}
