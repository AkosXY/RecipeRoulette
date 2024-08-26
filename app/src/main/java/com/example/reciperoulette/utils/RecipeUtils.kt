package com.example.reciperoulette.utils

import com.example.reciperoulette.data.model.RecipeEntity

fun createIngredientsString(recipeEntity: RecipeEntity): String {
    return recipeEntity.ingredients
}

fun createInstructionsString(recipeEntity: RecipeEntity): String {
    return recipeEntity.instructions
}

fun createShortSummary(fullSummary: String?): String {
    if (fullSummary == null) return ""
    val cleanedSummary = fullSummary.replace(Regex("<.*?>"), "")
    val sentences = cleanedSummary.split(Regex("(?<=[.!?])\\s+"))
    return sentences.firstOrNull() ?: ""
}
