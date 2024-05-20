package com.example.reciperoulette.data.di

import com.example.reciperoulette.data.dao.RecipeDao
import com.example.reciperoulette.data.datasource.RecipeRepository
import com.example.reciperoulette.feature.ingredients.IngredientsViewModel
import com.example.reciperoulette.feature.recipe.RecipeViewModel
import com.example.reciperoulette.feature.steps.RecipeStepsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    @Singleton
    fun provideRecipeViewModel(recipeRepository: RecipeRepository, recipeDao: RecipeDao): RecipeViewModel {
        return RecipeViewModel(recipeRepository, recipeDao)
    }

    @Provides
    @Singleton
    fun provideIngredientsViewModel(recipeRepository: RecipeRepository, recipeDao: RecipeDao): IngredientsViewModel {
        return IngredientsViewModel(recipeRepository, recipeDao)
    }

    @Provides
    @Singleton
    fun provideRecipeStepsViewModel(recipeRepository: RecipeRepository, recipeDao: RecipeDao): RecipeStepsViewModel {
        return RecipeStepsViewModel(recipeRepository, recipeDao)
    }
}
