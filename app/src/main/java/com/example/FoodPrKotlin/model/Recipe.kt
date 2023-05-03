package com.example.FoodPrKotlin.model

class Recipe {
    @JvmField
    var image: String? = null
    @JvmField
    var label: String? = null
    @JvmField
    var calories = 0.0
    @JvmField
    var ingredientLines: List<String>? = null
    @JvmField
    var co2EmissionsClass: String? = null

    override fun toString(): String {
        return label + " " + calories + " " + ingredientLines.toString()
    }
}