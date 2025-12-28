package com.example.expensetracker.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.ui.components.DateChipsRow
import com.example.expensetracker.ui.components.ExpenseCard
import com.example.expensetracker.ui.components.NetBalanceCard


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val expenses by viewModel.expenses.collectAsState()
    val income by viewModel.totalIncome.collectAsState()
    val expense by viewModel.totalExpense.collectAsState()
    val dates by viewModel.dates.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val iconBgColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DateChipsRow(
                dates = dates,
                selectedDate = selectedDate,
                onDateSelected = viewModel::onDateSelected
            )
        }


        item {
            NetBalanceCard(
                expense = expense,
                subtitle = "Selected day summary"
            )
        }

        item {
            Text(
                text = "Recent Transactions",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        items(
            items = expenses,
            key = { expense -> expense.id },
            contentType = { "expense" }
        ) { expense ->
            ExpenseItem(
                expense = expense,
                iconBgColor = iconBgColor,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}


@Composable
private fun ExpenseItem(
    expense: ExpenseEntity,
    iconBgColor: Color,
    modifier: Modifier = Modifier
) {
    val icon = remember(expense.category) {
        categoryIcon(expense.category)
    }
    
    ExpenseCard(
        itemName = expense.itemName,
        category = expense.category,
        time = expense.date.toString(),
        amount = "₹${expense.amount.toInt()}",
        icon = icon,
        iconBgColor = iconBgColor,
        modifier = modifier
    )
}

private fun categoryIcon(category: String): ImageVector {
    return when (category.lowercase()) {
        "food" -> Icons.Outlined.Fastfood
        "transport" -> Icons.Outlined.DirectionsBus
        "shopping" -> Icons.Outlined.ShoppingCart
        "bills" -> Icons.Outlined.ReceiptLong
        "study", "education" -> Icons.Outlined.MenuBook
        else -> Icons.Outlined.Payments
    }
}
