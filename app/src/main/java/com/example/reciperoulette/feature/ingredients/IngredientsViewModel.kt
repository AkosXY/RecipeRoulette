package com.example.reciperoulette.feature.ingredients

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reciperoulette.data.dao.RecipeDao
import com.example.reciperoulette.data.datasource.RecipeRepository
import com.example.reciperoulette.data.model.RecipeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class IngredientsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val recipeDao: RecipeDao
) : ViewModel() {
    private val _selectedIngredients = mutableStateOf<List<String>>(emptyList())
    val selectedIngredients = _selectedIngredients

    val recipe: StateFlow<RecipeEntity?> = recipeRepository.recipe

}