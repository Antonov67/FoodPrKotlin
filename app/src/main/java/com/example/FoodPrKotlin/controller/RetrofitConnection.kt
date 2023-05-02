package com.example.FoodPrKotlin.controller

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConnection private constructor() {
    val retrofit: Retrofit?
        get() = Companion.retrofit

    companion object {
        private const val BASE_URL = "https://api.edamam.com"
        var retrofit: Retrofit? = null

        init {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @JvmStatic
        var instance: RetrofitConnection? = null
            get() {
                if (field == null) {
                    field = RetrofitConnection()
                }
                return field
            }
            private set
    }
}