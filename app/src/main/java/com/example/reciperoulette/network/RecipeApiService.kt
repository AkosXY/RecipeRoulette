package com.example.reciperoulette.network

import com.example.reciperoulette.data.model.RandomRecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface RecipeApiService {
    @GET("recipes/random?apiKey=c4b79c481c0045afb523e21db1afb695")
    suspend fun getRandomRecipe(
    ): Response<RandomRecipeResponse>?

}