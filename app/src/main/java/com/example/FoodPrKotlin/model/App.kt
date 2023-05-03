package com.example.FoodPrKotlin.model

import android.app.Application
import com.example.FoodPrKotlin.db.Database

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Database.build(this)
    }
}