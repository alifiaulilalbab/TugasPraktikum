package org.d3if4077.mobpro2

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val authState = FirebaseUserLiveData()
}