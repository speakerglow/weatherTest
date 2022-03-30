package com.example.weathertest.utils

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertest.R
import com.example.weathertest.models.ForecastDay
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastAdapterDelegate() :
    AbsListItemAdapterDelegate<ForecastDay, ForecastDay, ForecastAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: ForecastDay,
        items: MutableList<ForecastDay>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            parent.inflate(R.layout.item_forecast)
        )
    }

    override fun onBindViewHolder(
        item: ForecastDay,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(item: ForecastDay) {
            containerView.item_forecast_mintemp.text = item.day.mintemp_c.toString()
            containerView.item_forecast_maxtemp.text = item.day.maxtemp_c.toString()
            containerView.item_forecast_precip.text = item.day.totalprecip_mm.toString()
            containerView.item_forecast_date.text = item.date
        }
    }
}