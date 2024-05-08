package com.example.reciperoulette.feature.recipe

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciperoulette.data.dao.RecipeDao
import com.example.reciperoulette.data.datasource.RecipeRepository
import com.example.reciperoulette.data.model.RecipeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val recipeDao: RecipeDao
) : ViewModel() {

    var recipe by mutableStateOf<RecipeEntity?>(null)

    init {
        loadRecipe()
    }

    fun getNewRecipe() {
        viewModelScope.launch {
            val randomRecipe = recipeRepository.getRandomRecipe()

            randomRecipe?.let { apiResponse ->
                val title = apiResponse.title
                val dishType = apiResponse.dishType
                val summary = createShortSummary(apiResponse.summary)
                val ingredientsString = createIngredientsString(apiResponse)
                val instructionsString = createInstructionsString(apiResponse)
                val image = apiResponse.image

                Log.i("my_tag", "Title: $title")
                Log.i("my_tag", "Dish Type: $dishType")
                Log.i("my_tag", "Summary: $summary")
                Log.i("my_tag", "Ingredients: $ingredientsString")
                Log.i("my_tag", "Instructions: $instructionsString")


                val newRecipeEntity = RecipeEntity(
                    title = title,
                    dishType = dishType,
                    summary = summary,
                    ingredients = ingredientsString,
                    instructions = instructionsString,
                    image = image
                )

                recipe = newRecipeEntity

                recipeRepository.updateRecipe(newRecipeEntity)

                recipeDao.upsertRecipe(newRecipeEntity)

            }
        }
    }


    fun loadRecipe() {
        viewModelScope.launch {

            val loadedRecipe = withContext(Dispatchers.IO) {
                recipeDao.getRecipe()
            }

            recipe = loadedRecipe

            if (loadedRecipe != null) {
                recipeRepository.updateRecipe(loadedRecipe)
            }

            loadedRecipe.let { recipeEntity ->
                Log.i("my_tag", "Title: ${recipeEntity?.title}")
                Log.i("my_tag", "Dish Type: ${recipeEntity?.dishType}")
                Log.i("my_tag", "Summary: ${recipeEntity?.summary}")
                Log.i("my_tag", "Ingredients: ${recipeEntity?.ingredients}")
                Log.i("my_tag", "Instructions: ${recipeEntity?.instructions}")
                Log.i("my_tag", "Image: ${recipeEntity?.image}")

            }
        }
    }

    private fun createIngredientsString(recipeEntity: RecipeEntity): String {
        return recipeEntity.ingredients
    }


    private fun createInstructionsString(recipeEntity: RecipeEntity): String {
        return recipeEntity.instructions
    }


    private fun createShortSummary(fullSummary: String?): String {
        if (fullSummary == null) return ""
        val cleanedSummary = fullSummary.replace(Regex("<.*?>"), "")
        val sentences = cleanedSummary.split(Regex("(?<=[.!?])\\s+"))
        return sentences.firstOrNull() ?: ""
    }


}