package com.example.reciperoulette.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reciperoulette.feature.ingredients.IngredientsScreen
import com.example.reciperoulette.feature.recipe.RecipeScreen
import com.example.reciperoulette.feature.steps.RecipeStepsScreen


@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {


    NavHost(
        navController = navController,
        startDestination = Menu.Recipe.route,
        modifier = modifier
    ) {
        composable(route = Menu.Detail.route) {
            RecipeStepsScreen()
        }
        composable(route = Menu.Recipe.route) {
            RecipeScreen()
        }
        composable(route = Menu.Items.route) {
            IngredientsScreen()
        }
    }
}
