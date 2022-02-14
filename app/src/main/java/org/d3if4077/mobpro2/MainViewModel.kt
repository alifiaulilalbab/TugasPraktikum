package org.d3if4077.mobpro2

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val authState = FirebaseUserLiveData()

    init {
        checkToken()
    }
    private fun checkToken() {
        val tokenTask = FirebaseMessaging.getInstance().token
        tokenTask.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "FCM token failed.", task.exception)
                return@OnCompleteListener
            }
            Log.d("FCM", "Token: ${task.result}")
        })
    }
}