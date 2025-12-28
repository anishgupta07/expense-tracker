package com.example.expensetracker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.example.expensetracker.ui.components.BottomNavBar
import com.example.expensetracker.ui.components.CustomTopBar
import com.example.expensetracker.ui.screens.add_expense.AddExpenseScreen
import com.example.expensetracker.ui.screens.home.HomeScreen
import com.example.expensetracker.ui.screens.tracker.TrackerScreen
import com.example.expensetracker.ui.theme.PurplePrimary
import com.example.expensetracker.utils.BottomNavItems

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        BottomNavItems.Home.route,
        BottomNavItems.Tracker.route
    )

    val title = when (currentRoute) {
        BottomNavItems.Home.route -> "Home"
        BottomNavItems.Tracker.route -> "Tracker"
        else -> "Add Expense"
    }

    Scaffold(
        topBar = {
            if (showBottomBar) {
                CustomTopBar(title = title)
            } else if (currentRoute == "add_expense") {
                TopAppBar(
                    title = { Text("Add Transaction", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = PurplePrimary,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController)
            }
        },
        floatingActionButton = {
            if (currentRoute == BottomNavItems.Home.route) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("add_expense")
                    },
                    containerColor = PurplePrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Expense",
                        tint = Color.White
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = BottomNavItems.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(
                route = BottomNavItems.Home.route
            ) {
                HomeScreen()
            }

            composable(
                route = BottomNavItems.Tracker.route
            ) {
                TrackerScreen()
            }

            composable(
                route = "add_expense"
            ) {
                AddExpenseScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
