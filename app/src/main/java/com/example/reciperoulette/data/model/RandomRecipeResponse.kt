package com.example.reciperoulette.data.model


import com.google.gson.annotations.SerializedName

data class RandomRecipeResponse(
    @SerializedName("recipes")
    val recipes: List<Recipe>
)