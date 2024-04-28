package com.example.reciperoulette.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reciperoulette.data.dao.RecipeDao
import com.example.reciperoulette.data.model.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
abstract class RecipeDatabase: RoomDatabase() {
    abstract val recipeDao: RecipeDao
}