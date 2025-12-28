package com.example.expensetracker.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItems(
    val route : String,
    val icon : ImageVector,
    val title : String
){
    object Home : BottomNavItems("Home", Icons.Filled.Home, "Home")
    object Tracker : BottomNavItems ("Tracker", Icons.Filled.AutoGraph, "Tracker")
}
