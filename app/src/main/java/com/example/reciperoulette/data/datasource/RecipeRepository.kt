package com.example.reciperoulette.data.datasource

import com.example.reciperoulette.data.dao.RecipeDao
import com.example.reciperoulette.data.model.Recipe
import com.example.reciperoulette.data.model.RecipeEntity
import com.example.reciperoulette.network.RecipeApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val recipeApiService: RecipeApiService,
    private val recipeDao: RecipeDao
) {

    private val _recipe = MutableStateFlow<RecipeEntity?>(null)
    val recipe: StateFlow<RecipeEntity?> = _recipe

    fun updateRecipe(newRecipe: RecipeEntity) {
        _recipe.value = newRecipe
    }

    suspend fun getRandomRecipe(): RecipeEntity? {


        val remoteRecipeResponse = recipeApiService.getRandomRecipe()?.body()

        remoteRecipeResponse?.let {
            val recipe = it.recipes.first()
            val recipeEntity = RecipeEntity(
                title = recipe.title,
                dishType = recipe.dishTypes.first(),
                summary = recipe.summary,
                ingredients = createIngredientsString(recipe),
                instructions = createInstructionsString(recipe),
                image = recipe.image
            )
            recipeDao.upsertRecipe(recipeEntity)
            return recipeEntity
        }

        return null
    }

    private fun createIngredientsString(recipe: Recipe): String {
        val ingredients = recipe.extendedIngredients
        return ingredients.joinToString(separator = ";") { "${it.nameClean} ${it.amount} ${it.unit}" }
    }

    private fun createInstructionsString(recipe: Recipe): String {
        val instructions = recipe.analyzedInstructions ?: return ""
        val steps = instructions.flatMap { it.steps }
        return steps.joinToString(separator = ";") { it.step }
    }
}