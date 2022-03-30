package com.example.weathertest.views

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weathertest.R
import com.example.weathertest.databinding.FragmentWeatherByDayBinding
import com.example.weathertest.models.ApiWeatherResult
import com.example.weathertest.utils.ForecastAdapter
import com.example.weathertest.utils.autoCleared
import com.example.weathertest.viewModels.WeatherByDayViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherByDayFragment : Fragment(R.layout.fragment_weather_by_day) {

    private val binding: FragmentWeatherByDayBinding by viewBinding(FragmentWeatherByDayBinding::bind)
    private val viewModel: WeatherByDayViewModel by viewModels()
    private var forecastAdapter: ForecastAdapter by autoCleared()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpFlow()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearJob()
    }

    private fun init() {
        forecastAdapter = ForecastAdapter()

        binding.forecastRecyclerView.apply {
            adapter = forecastAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.load()
    }

    private fun setUpFlow() {
        lifecycleScope.launch {
            viewModel.getFlow().flowWithLifecycle(
                lifecycle, Lifecycle.State.STARTED
            ).collect { result ->
                when (result) {
                    is ApiWeatherResult.Loading -> {
                        showProgressBar(true)
                    }
                    is ApiWeatherResult.Success -> {
                        forecastAdapter.items = result.weather?.forecast?.forecastday?.asList()
                        showProgressBar(false)
                    }
                    is ApiWeatherResult.Error -> {
                        showProgressBar(false)
                        showErrorSnackBar(result.exception) {
                            viewModel.load()
                        }
                    }
                }
            }
        }
    }

    private fun showErrorSnackBar(errorMsg: String, callBack: () -> Unit) {
        Snackbar.make(
            binding.frameLayoutContainer,
            errorMsg,
            Snackbar.LENGTH_LONG
        ).setAction(
            "retry"
        ) {
            it.visibility = View.INVISIBLE
            callBack()
        }.show()
    }

    private fun showProgressBar(loading: Boolean) {
        binding.progressBar.isVisible = loading
        binding.forecastRecyclerView.isVisible = !loading
    }
}