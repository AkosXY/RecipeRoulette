package com.example.reciperoulette.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val dishType: String,
    val summary: String,
    val ingredients: String,
    val instructions: String,
    val image: String

)
