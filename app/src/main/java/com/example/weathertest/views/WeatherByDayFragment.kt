package com.example.weathertest.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weathertest.R
import com.example.weathertest.databinding.FragmentWeatherByDayBinding
import com.example.weathertest.viewModels.WeatherByDayViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherByDayFragment : Fragment(R.layout.fragment_weather_by_day) {

    private val binding: FragmentWeatherByDayBinding by viewBinding(FragmentWeatherByDayBinding::bind)
    private val viewModel: WeatherByDayViewModel by viewModels()
    private var currentJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentJob = lifecycleScope.launch {
            viewModel.getFlow().collect {
                binding.weatherTv.text = it
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentJob?.cancel()
        viewModel.clearJob()
    }
}