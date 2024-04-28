package com.example.reciperoulette.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.reciperoulette.data.model.RecipeEntity

@Dao
interface RecipeDao {

    @Upsert
    suspend fun upsertRecipe(recipeEntity: RecipeEntity)

    @Query("SELECT * FROM recipes ORDER BY id DESC LIMIT 1")
    fun getRecipe(): RecipeEntity

}