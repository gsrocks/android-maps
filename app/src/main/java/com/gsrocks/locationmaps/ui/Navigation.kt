package com.gsrocks.locationmaps.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gsrocks.locationmaps.feature.userlocation.ui.UserLocationScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { UserLocationScreen(modifier = Modifier.padding(16.dp)) }
        // TODO: Add more destinations
    }
}
