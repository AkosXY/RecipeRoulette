package com.example.reciperoulette.feature.recipe

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciperoulette.data.dao.RecipeDao
import com.example.reciperoulette.data.model.RecipeEntity
import com.example.reciperoulette.data.datasource.RecipeRepository
import com.example.reciperoulette.data.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val recipeDao: RecipeDao
): ViewModel() {
    fun getNewRecipe() {
        viewModelScope.launch {
            val recipe = recipeRepository.getRandomRecipe()?.recipes?.get(0)

            val title = recipe?.title ?: ""
            val dishType = recipe?.dishTypes?.get(0) ?: ""
            val summary = createShortSummary(recipe?.summary)
            val ingredientsString = createIngredientsString(recipe)
            val instructionsString = createInstructionsString(recipe)

            Log.i("my_tag", "Title: $title")
            Log.i("my_tag", "Dish Type: $dishType")
            Log.i("my_tag", "Summary: $summary")
            Log.i("my_tag", "Ingredients: $ingredientsString")
            Log.i("my_tag", "Instructions: $instructionsString")

            val recipeEntity = RecipeEntity(
                title = title,
                dishType = dishType,
                summary = summary,
                ingredients = ingredientsString,
                instructions = instructionsString
            )

           recipeDao.upsertRecipe(recipeEntity)

        }
    }

    fun loadRecipe() {
        viewModelScope.launch {

            val loadedRecipe = withContext(Dispatchers.IO) {
                recipeDao.getRecipe()
            }
            Log.i("my_tag", "Title: ${loadedRecipe.title}")
            Log.i("my_tag", "Dish Type: ${loadedRecipe.dishType}")
            Log.i("my_tag", "Summary: ${loadedRecipe.summary}")
            Log.i("my_tag", "Ingredients: ${loadedRecipe.ingredients}")
            Log.i("my_tag", "Instructions: ${loadedRecipe.instructions}")
        }
    }



    private fun createIngredientsString(recipe: Recipe?): String {
        val ingredients = recipe?.extendedIngredients ?: return ""
        return ingredients.joinToString(separator = ";") { "${it.nameClean} ${it.amount} ${it.unit}" }
    }

    private fun createInstructionsString(recipe: Recipe?): String {
        val instructions = recipe?.analyzedInstructions ?: return ""
        val steps = instructions.flatMap { it.steps }
        return steps.joinToString(separator = ";") { it.step }
    }

    private fun createShortSummary(fullSummary: String?): String {
        if (fullSummary == null) return ""
        val cleanedSummary = fullSummary.replace(Regex("<.*?>"), "")
        val sentences = cleanedSummary.split(Regex("(?<=[.!?])\\s+"))
        return sentences.firstOrNull() ?: ""
    }


}