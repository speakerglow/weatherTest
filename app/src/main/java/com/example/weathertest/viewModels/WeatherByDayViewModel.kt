package com.example.weathertest.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertest.models.ApiWeatherResult
import com.example.weathertest.models.Weather
import com.example.weathertest.network.NetProvider
import com.example.weathertest.storage.StorageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherByDayViewModel @Inject constructor(
    private val netProvider: NetProvider,
    private val storageProvider: StorageProvider
) : ViewModel() {

    private val sharedFlow =
        MutableSharedFlow<ApiWeatherResult>(replay = 1, extraBufferCapacity = 1)
    private var currentJob: Job? = null

    fun load() {
        currentJob = viewModelScope.launch {
            sharedFlow.emit(ApiWeatherResult.Loading())
            try {
                if (storageProvider.getCurrentCity() == null) sharedFlow.emit(
                    ApiWeatherResult.Error(
                        "No current city"
                    )
                )
                else sharedFlow.emit(
                    ApiWeatherResult.Success(
                        Weather.fromJson(
                            netProvider.getForecast(
                                storageProvider.getCurrentCity()!!.name
                            )
                        )
                    )
                )
            } catch (ex: Exception) {
                sharedFlow.emit(ApiWeatherResult.Error(ex.message ?: "oshibka"))
            }
        }
    }

    fun getFlow(): Flow<ApiWeatherResult> = sharedFlow


    fun clearJob() {
        currentJob?.cancel()
    }
}