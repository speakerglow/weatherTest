package com.example.weathertest.utils

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertest.R
import com.example.weathertest.models.dao.CityDaoEntity
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_city.view.*

class CitiesAdapterDelegate(private val onItemClick: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<CityDaoEntity, CityDaoEntity, CitiesAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: CityDaoEntity,
        items: MutableList<CityDaoEntity>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            parent.inflate(R.layout.item_city),
            onItemClick
        )
    }

    override fun onBindViewHolder(
        item: CityDaoEntity,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        init {
            containerView.setOnClickListener {
                onItemClick(adapterPosition)
                true
            }
        }

        fun bind(item: CityDaoEntity) {
            containerView.item_city_name.text = item.name
        }
    }
}