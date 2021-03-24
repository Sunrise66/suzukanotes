package com.sunrise.suzukanotes.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val btnCharClicked = MutableLiveData(false)
    val btnCardClicked = MutableLiveData(false)
    val btnRaceClicked = MutableLiveData(false)
}