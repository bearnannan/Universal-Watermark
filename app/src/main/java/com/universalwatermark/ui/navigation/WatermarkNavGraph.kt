package com.universalwatermark.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.universalwatermark.ui.screen.dashboard.DashboardScreen
import com.universalwatermark.ui.screen.history.WatermarkHistoryScreen
import com.universalwatermark.ui.screen.settings.SettingsScreen
import com.universalwatermark.ui.screen.settings.WorkflowSettingsScreen
import com.universalwatermark.ui.screen.settings.StyleSettingsScreen
import com.universalwatermark.ui.screen.settings.ContentSettingsScreen
import com.universalwatermark.ui.screen.settings.SystemSettingsScreen

@Composable
fun WatermarkNavGraph() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToHistory = { navController.navigate("history") }
            )
        }
        composable("history") {
            WatermarkHistoryScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("settings") {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToWorkflow = { navController.navigate("settings_workflow") },
                onNavigateToStyle = { navController.navigate("settings_style") },
                onNavigateToContent = { navController.navigate("settings_content") },
                onNavigateToSystem = { navController.navigate("settings_system") }
            )
        }
        composable("settings_workflow") {
            WorkflowSettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("settings_style") {
            StyleSettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("settings_content") {
            ContentSettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("settings_system") {
            SystemSettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
