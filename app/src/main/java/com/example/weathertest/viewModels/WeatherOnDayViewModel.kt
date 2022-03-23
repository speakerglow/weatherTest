package com.example.weathertest.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertest.models.Weather
import com.example.weathertest.models.WeatherTest
import com.example.weathertest.network.NetProvider
import com.example.weathertest.storage.StorageProvider
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class WeatherOnDayViewModel @Inject constructor(
    private val netProvider: NetProvider,
    private val storageProvider: StorageProvider
) : ViewModel() {

    private val sharedFlow = MutableSharedFlow<String>()

//    private val flow = flow<String> {
//        emit("Loading")
//        emit(netProvider.getWeather())
//    }.catch { emit(it.message ?: "error") }


    private var currentJob: Job? = null

    fun load() {

        currentJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                (1..20).forEach {
                    storageProvider.putSome(Random.nextBoolean().toString())
                }
            }
        }

        /*currentJob = viewModelScope.launch {
            sharedFlow.emit("loading")
            val string = WeatherTest.fromJson(netProvider.getWeather()).toString()
            try {
                sharedFlow.emit(string)
            } catch (ex: Exception) {
                sharedFlow.emit(ex.message!!)
            }
        }*/
    }

    fun getFlow(): Flow<String> {
        return sharedFlow
//        return flow
    }

    fun clearJob() {
        currentJob?.cancel()
    }
}