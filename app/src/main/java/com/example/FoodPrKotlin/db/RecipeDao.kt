package com.example.FoodPrKotlin.db

import androidx.room.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity):Long

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM Recipe")
    suspend fun getRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM Recipe WHERE id = :newRecipeId")
    suspend fun getRecipe(newRecipeId: Long): RecipeEntity

    @Query("DELETE FROM Recipe")
    fun deleteAll()

    @Query("SELECT * FROM Recipe WHERE label LIKE :title")
    suspend fun getRecipeByTitle(title: String): RecipeEntity

    @Query("SELECT COUNT(id) FROM Recipe")
    suspend fun getCountRecords(): Long

}