package com.example.weathertest.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertest.models.ApiResult
import com.example.weathertest.models.CitiesApiResponse
import com.example.weathertest.models.Weather
import com.example.weathertest.models.dao.CityDaoEntity
import com.example.weathertest.network.NetProvider
import com.example.weathertest.storage.StorageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val netProvider: NetProvider,
    private val storageProvider: StorageProvider
) : ViewModel() {

    private val sharedFlow = MutableSharedFlow<String>()
    //private var currentJob: Job? = null

    fun setFlowForSearch(queryFlow: Flow<String>) {
        queryFlow.debounce(500)
            .distinctUntilChanged()
            .onEach {  }
            .mapLatest {  }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun load() {
        viewModelScope.launch {
            try {
                val response = CitiesApiResponse.fromJson(netProvider.getCityList())
                if (response.error) throw Exception("Bad request body")
                storageProvider.putCities(response.data.map { CityDaoEntity(it) })
            } catch (ex: Exception) {
                sharedFlow.emit(ex.message ?: "oshibka")
            }
        }
        //currentJob.ch
    }

    fun getFlow(): Flow<String> {
        return sharedFlow
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("Tag", "settingsViewModelScope cleared")
    }

    /*fun clearJob() {
        currentJob?.cancel()
    }*/
}