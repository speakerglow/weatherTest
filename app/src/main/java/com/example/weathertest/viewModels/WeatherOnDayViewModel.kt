package com.example.weathertest.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertest.models.ApiResult
import com.example.weathertest.models.ForecastDay
import com.example.weathertest.models.Weather
import com.example.weathertest.network.NetProvider
import com.example.weathertest.storage.StorageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.NullPointerException
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class WeatherOnDayViewModel @Inject constructor(
    private val netProvider: NetProvider,
    private val storageProvider: StorageProvider
) : ViewModel() {

    private val sharedFlow = MutableSharedFlow<ApiResult>()

    private var currentJob: Job? = null

    fun load() {
        currentJob = viewModelScope.launch {
            sharedFlow.emit(ApiResult.Loading())
            delay(5_000)
            Log.e("Tag", "weatherOnDay viewModel job work")
            try {
                if (storageProvider.getCurrentCity() == null) sharedFlow.emit(ApiResult.Error("No current city"))
                else sharedFlow.emit(
                    ApiResult.Success(
                        Weather.fromJson(
                            netProvider.getForecast(
                                storageProvider.getCurrentCity()!!.name
                            )
                        )
                    )
                )
            } catch (ex: Exception) {
                sharedFlow.emit(ApiResult.Error(ex.message ?: "oshibka"))
            }
        }
    }

    fun getFlow(): Flow<ApiResult> {
        return sharedFlow
    }

    fun clearJob() {
        currentJob?.cancel()
    }
}