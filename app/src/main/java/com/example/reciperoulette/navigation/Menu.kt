package com.example.reciperoulette.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Menu(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Recipe : Menu(
        route = "recipe",
        title = "Recipe",
        icon = Icons.Default.Search
    )

    object Detail : Menu(
        route = "detail",
        title = "Detail",
        icon = Icons.Default.Menu
    )

    object Items : Menu(
        route = "items",
        title = "Items",
        icon = Icons.Default.Check
    )

}