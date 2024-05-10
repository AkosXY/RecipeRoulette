package com.example.reciperoulette.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.reciperoulette.data.model.RecipeEntity
import javax.inject.Singleton

@Dao
@Singleton
interface RecipeDao {

    @Upsert
    suspend fun upsertRecipe(recipeEntity: RecipeEntity)

    @Query("SELECT * FROM recipes ORDER BY id DESC LIMIT 1")
    suspend fun getRecipe(): RecipeEntity?

}