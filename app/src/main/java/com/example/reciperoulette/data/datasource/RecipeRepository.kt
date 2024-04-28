package com.example.reciperoulette.data.datasource

import com.example.reciperoulette.data.model.RandomRecipeResponse
import com.example.reciperoulette.network.RecipeApiService
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val recipeApiService: RecipeApiService) {
     suspend fun getRandomRecipe(): RandomRecipeResponse? {
        return recipeApiService.getRandomRecipe()?.body()
    }
}