package com.example.weathertest

import android.content.Context
import android.util.Log
import android.widget.Toast

object Logi {
    var context: Context? = null

    fun makeLog(msg: String){
        Log.d("TAG", msg)
    }
}