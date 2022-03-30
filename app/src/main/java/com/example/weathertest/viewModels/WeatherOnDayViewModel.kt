package com.example.weathertest.viewModels

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertest.models.ApiWeatherResult
import com.example.weathertest.models.Weather
import com.example.weathertest.network.NetProvider
import com.example.weathertest.storage.StorageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherOnDayViewModel @Inject constructor(
    private val netProvider: NetProvider,
    private val storageProvider: StorageProvider
) : ViewModel() {

    private val sharedFlow = MutableSharedFlow<ApiWeatherResult>(
        replay = 1
    )
    private var currentJob: Job? = null

    fun load() {
        currentJob = viewModelScope.launch {
            Log.e("Tag", "weatherOnDay viewModel send loading")
            sharedFlow.emit(ApiWeatherResult.Loading())
            try {
                if (storageProvider.getCurrentCity() == null)
                    sharedFlow.emit(
                        ApiWeatherResult.Error(
                            "No current city"
                        )
                    )
                else {
                    delay(1000)
                    sharedFlow.emit(
                        ApiWeatherResult.Success(
                            Weather.fromJson(
                                netProvider.getForecast(
                                    storageProvider.getCurrentCity()!!.name
                                )
                            )
                        )
                    )
                    Log.e("Tag", "weather on day viewmodel result sended")
                }
            } catch (ex: Exception) {
                sharedFlow.emit(ApiWeatherResult.Error(ex.message ?: "oshibka"))
            }
        }
    }

    fun searchWeather(context: Context) {
        viewModelScope.launch {
            val intent = Intent().apply {
                action = Intent.ACTION_WEB_SEARCH
                putExtra(
                    SearchManager.QUERY,
                    "${storageProvider.getCurrentCity()?.name.orEmpty()} weather"
                )
            }
            context.startActivity(Intent.createChooser(intent, null))
        }
    }

    fun getFlow(): SharedFlow<ApiWeatherResult> = sharedFlow

    fun clearJob() {
        currentJob?.cancel()
    }
}