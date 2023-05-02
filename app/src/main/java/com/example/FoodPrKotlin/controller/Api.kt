package com.example.FoodPrKotlin.controller


import com.example.FoodPrKotlin.model.MyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/api/recipes/v2")
    fun getRecipes(
        @Query("type") type: String?,
        @Query("app_id") appId: String?,
        @Query("app_key") appKey: String?,
        @Query("q") query: String?
    ): Call<MyResponse?>?
}