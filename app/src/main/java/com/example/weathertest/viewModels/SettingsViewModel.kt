package com.example.weathertest.viewModels

import androidx.lifecycle.ViewModel
import com.example.weathertest.network.NetProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val netProvider: NetProvider
) : ViewModel() {
}