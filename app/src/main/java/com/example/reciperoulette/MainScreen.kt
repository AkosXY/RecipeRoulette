package com.example.reciperoulette

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.reciperoulette.navigation.Menu
import com.example.reciperoulette.navigation.NavGraph
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(analytics: FirebaseAnalytics) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController, analytics)
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = { Text(text = "RecipeRoulette") },
                navigationIcon = {

                },
                actions = {
                }

            )
        }
    )
    { innerPadding ->
        NavGraph(navController = navController, modifier = Modifier.padding(innerPadding))
    }


}

@Composable
fun BottomBar(navController: NavHostController, analytics: FirebaseAnalytics) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screens = listOf(
        Menu.Detail,
        Menu.Recipe,
        Menu.Items
    )

    NavigationBar() {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, screen.title) },
                label = { Text(screen.title) },
                selected = currentDestination?.route == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }

                    analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                        param(FirebaseAnalytics.Param.ITEM_ID, screen.route)
                        param(FirebaseAnalytics.Param.ITEM_NAME, screen.title)
                        param(FirebaseAnalytics.Param.CONTENT_TYPE, "navigation")
                    }
                }
            )
        }
    }
}