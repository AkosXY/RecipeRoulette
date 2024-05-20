package com.example.reciperoulette.feature.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RecipeStepsScreen() {

    val viewModel: RecipeStepsViewModel = hiltViewModel()

    val recipeState by viewModel.recipe.collectAsState()
    val recipeSteps = parseSteps(recipeState?.instructions.toString())


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(recipeSteps) { step ->
            RecipeStepItem(step = step)
        }
    }
}

@Composable
fun RecipeStepItem(step: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = step,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


fun parseSteps(instructionsString: String?): List<String> {
    if (instructionsString.isNullOrEmpty()) {
        return emptyList()
    }

    val steps = instructionsString.split(";").map { it.trim() }

    return steps.mapIndexed { index, step -> "${index + 1}. $step" }
}

@Preview
@Composable
fun PreviewRecipeStepsScreen() {
    RecipeStepsScreen()
}
