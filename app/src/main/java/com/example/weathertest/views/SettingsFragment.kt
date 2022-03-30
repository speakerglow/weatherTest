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
import com.example.weathertest.databinding.FragmentSettingsBinding
import com.example.weathertest.models.ApiCityResult
import com.example.weathertest.utils.CityAdapter
import com.example.weathertest.utils.autoCleared
import com.example.weathertest.utils.textChangedFlow
import com.example.weathertest.viewModels.SettingsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModels()
    private var cityAdapter: CityAdapter by autoCleared()

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

    private fun setUpFlow() {
        lifecycleScope.launch {
            viewModel.getFlow().flowWithLifecycle(
                lifecycle, Lifecycle.State.STARTED
            ).collect { result ->
                when (result) {
                    is ApiCityResult.Loading -> {
                        showProgressBar(true)
                    }
                    is ApiCityResult.Success -> {
                        cityAdapter.items = result.cities
                        showProgressBar(false)
                    }
                    is ApiCityResult.Error -> {
                        showProgressBar(false)
                        showErrorSnackBar(result.exception) {
                            viewModel.load()
                        }
                    }
                }
            }
        }
    }

    private fun init() {
        cityAdapter = CityAdapter { position ->
            val city = cityAdapter.items.get(position).apply { isCurrent = true }
            viewModel.saveCity(city)
            binding.currentCityTV.text = "current city: ${city.name}"
        }

        binding.citiesRecyclerView.apply {
            adapter = cityAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        viewModel.setFlowForSearch(binding.cityEditText.textChangedFlow())

        lifecycleScope.launch {
            val currentCity = viewModel.getCurrentCity()
            if (currentCity != null)
                binding.currentCityTV.text = "current city: ${currentCity.name}"
            else viewModel.load()
        }
    }

    private fun showErrorSnackBar(errorMsg: String, callBack: () -> Unit) {
        Snackbar.make(
            binding.mainContainer,
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
        binding.citiesRecyclerView.isVisible = !loading
    }
}