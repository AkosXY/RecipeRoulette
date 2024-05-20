
package com.example.reciperoulette.feature.steps

import androidx.lifecycle.ViewModel
import com.example.reciperoulette.data.dao.RecipeDao
import com.example.reciperoulette.data.datasource.RecipeRepository
import com.example.reciperoulette.data.model.RecipeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RecipeStepsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val recipeDao: RecipeDao
) : ViewModel() {

    val recipe: StateFlow<RecipeEntity?> = recipeRepository.recipe

}