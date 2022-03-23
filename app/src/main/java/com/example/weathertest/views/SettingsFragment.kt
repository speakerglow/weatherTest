package com.example.weathertest.views

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weathertest.R
import com.example.weathertest.databinding.FragmentSettingsBinding
import com.example.weathertest.viewModels.SettingsViewModel
import com.example.weathertest.viewModels.WeatherByDayViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModels()

}