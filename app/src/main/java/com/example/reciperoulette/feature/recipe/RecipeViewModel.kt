package com.example.reciperoulette.feature.recipe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciperoulette.data.dao.RecipeDao
import com.example.reciperoulette.data.datasource.RecipeRepository
import com.example.reciperoulette.data.model.RecipeEntity
import com.example.reciperoulette.utils.createIngredientsString
import com.example.reciperoulette.utils.createInstructionsString
import com.example.reciperoulette.utils.createShortSummary
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


    private fun loadRecipe() {
        viewModelScope.launch {
            val loadedRecipe = withContext(Dispatchers.IO) {
                recipeDao.getRecipe()
            }

            if (loadedRecipe != null) {
                recipe = loadedRecipe
                recipeRepository.updateRecipe(loadedRecipe)
            } else {
                getNewRecipe()
            }
        }
    }



}