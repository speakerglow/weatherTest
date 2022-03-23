package com.example.weathertest.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertest.models.Weather
import com.example.weathertest.models.WeatherTest
import com.example.weathertest.network.NetProvider
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeatherOnDayViewModel @Inject constructor(
    private val netProvider: NetProvider
) : ViewModel() {

    private val sharedFlow = MutableSharedFlow<String>()

//    private val flow = flow<String> {
//        emit("Loading")
//        emit(netProvider.getWeather())
//    }.catch { emit(it.message ?: "error") }


    private var currentJob: Job? = null

    fun load() {
        currentJob = viewModelScope.launch {
            sharedFlow.emit("loading")
            val string = WeatherTest.fromJson(netProvider.getWeather()).toString()
            try {
                sharedFlow.emit(string)
            } catch (ex: Exception) {
                sharedFlow.emit(ex.message!!)
            }
        }
    }

    fun getFlow(): Flow<String> {
        return sharedFlow
//        return flow
    }

    fun clearJob() {
        currentJob?.cancel()
    }
}