package com.example.weathertest.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertest.models.ApiCityResult
import com.example.weathertest.models.CitiesApiResponse
import com.example.weathertest.models.dao.CityDaoEntity
import com.example.weathertest.network.NetProvider
import com.example.weathertest.storage.StorageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val netProvider: NetProvider,
    private val storageProvider: StorageProvider
) : ViewModel() {

    private val sharedFlow = MutableSharedFlow<ApiCityResult>()
    private val currentJob = Job()

    fun setFlowForSearch(queryFlow: Flow<String>) {
        viewModelScope.launch(currentJob) {
            queryFlow.debounce(500)
                .distinctUntilChanged()
                .onEach {
                    sharedFlow.emit(ApiCityResult.Loading())
                    //delay(10_000)
                }
                .mapLatest {
                    Log.e("Tag", "query flow worked with    $it")
                    sharedFlow.emit(ApiCityResult.Success(searchCity(it)))
                }
                .catch { sharedFlow.emit(ApiCityResult.Error(it.message ?: "search error")) }
                .collect()
        }
    }

    fun saveCity(city: CityDaoEntity) {
        viewModelScope.launch(currentJob) {
            storageProvider.setNewCurrentCity(city)
        }
    }

    fun load() {
        viewModelScope.launch(currentJob) {
            try {
                Log.e("Tag", "load flow worked")
                if (storageProvider.getCurrentCity() == null) {
                    val response = CitiesApiResponse.fromJson(netProvider.getCityList())
                    if (response.error) throw Exception("Bad request body")
                    storageProvider.putCities(response.data.map { CityDaoEntity(it) })
                }
            } catch (ex: Exception) {
                sharedFlow.emit(ApiCityResult.Error(ex.message ?: "load error"))
            }
        }
    }

    fun getFlow(): Flow<ApiCityResult> {
        return sharedFlow
    }

    private suspend fun searchCity(query: String): List<CityDaoEntity> {
        return if (query.isNotEmpty()) storageProvider.getCities(query)
        else listOf()
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("Tag", "settingsViewModelScope cleared")
    }

    fun clearJob() {
        Log.e("Tag", "job canceled")
        currentJob.cancel()
    }
}