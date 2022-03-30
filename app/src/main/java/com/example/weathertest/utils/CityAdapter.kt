package com.example.weathertest.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.weathertest.models.dao.CityDaoEntity
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class CityAdapter(onItemClick: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<CityDaoEntity>(
        UserDiffUtilCallback()
    ) {

    init {
        delegatesManager.addDelegate(
            CitiesAdapterDelegate(onItemClick)
        )
    }

    class UserDiffUtilCallback : DiffUtil.ItemCallback<CityDaoEntity>() {
        override fun areItemsTheSame(oldItem: CityDaoEntity, newItem: CityDaoEntity): Boolean {
            return newItem.name.equals(oldItem.name)
        }

        override fun areContentsTheSame(oldItem: CityDaoEntity, newItem: CityDaoEntity): Boolean {
            return oldItem == newItem
        }
    }
}