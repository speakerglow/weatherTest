package com.example.weathertest.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertest.models.ApiCityResult
import com.example.weathertest.models.CitiesApiResponse
import com.example.weathertest.models.dao.CityDaoEntity
import com.example.weathertest.network.NetProvider
import com.example.weathertest.storage.StorageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val netProvider: NetProvider,
    private val storageProvider: StorageProvider
) : ViewModel() {

    private val sharedFlow =
        MutableSharedFlow<ApiCityResult>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private val currentJob = Job()

    fun setFlowForSearch(queryFlow: Flow<String>) {
        viewModelScope.launch(currentJob) {
            withContext(Dispatchers.IO) {
                queryFlow.debounce(1_000)
                    .distinctUntilChanged()
                    .onEach {
                        sharedFlow.emit(ApiCityResult.Loading())
                    }
                    .mapLatest {
                        sharedFlow.emit(ApiCityResult.Success(searchCity(it).take(20)))
                    }
                    .catch { sharedFlow.emit(ApiCityResult.Error(it.message ?: "search error")) }
                    .collect()
            }
        }
    }

    suspend fun getCurrentCity(): CityDaoEntity? {
        return storageProvider.getCurrentCity()
    }

    fun saveCity(city: CityDaoEntity) {
        viewModelScope.launch(currentJob) {
            storageProvider.setNewCurrentCity(city)
        }
    }

    fun load() {
        viewModelScope.launch(currentJob) {
            try {
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

    fun clearJob() {
        currentJob.cancel()
    }
}