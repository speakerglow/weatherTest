package com.example.weathertest.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.weathertest.models.ForecastDay
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ForecastAdapter() :
    AsyncListDifferDelegationAdapter<ForecastDay>(
        UserDiffUtilCallback()
    ) {

    init {
        delegatesManager.addDelegate(
            ForecastAdapterDelegate()
        )
    }

    class UserDiffUtilCallback : DiffUtil.ItemCallback<ForecastDay>() {
        override fun areItemsTheSame(oldItem: ForecastDay, newItem: ForecastDay): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: ForecastDay, newItem: ForecastDay): Boolean {
            return oldItem == newItem
        }
    }
}