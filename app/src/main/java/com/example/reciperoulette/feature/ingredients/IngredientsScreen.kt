package com.example.reciperoulette.feature.ingredients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun IngredientsScreen() {
    val viewModel: IngredientsViewModel = hiltViewModel()

    val recipeState by viewModel.recipe.collectAsState()
    val ingredientsList = parseIngredients(recipeState?.ingredients.toString())

    var selectedIngredients by remember { viewModel.selectedIngredients }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(ingredientsList) { ingredient ->
                val isChecked = selectedIngredients.contains(ingredient)

                IngredientItem(
                    ingredient = ingredient,
                    isSelected = isChecked,
                    onCheckedChange = { isChecked ->
                        selectedIngredients = if (isChecked) {
                            selectedIngredients + ingredient
                        } else {
                            selectedIngredients - ingredient
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun IngredientItem(
    ingredient: String,
    isSelected: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = onCheckedChange
        )
        Text(text = ingredient)
    }
}

fun parseIngredients(ingredientsString: String?): List<String> {
    return ingredientsString?.split(";")?.map { it.trim() } ?: emptyList()
}
