package com.example.FoodPrKotlin.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Recipe")
data class RecipeEntity (

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val image: String? = null,

    val label: String? = null,

    val calories: Double = 0.0,

    val ingredientLines:String? = null,

    val co2EmissionsClass: String? = null
)