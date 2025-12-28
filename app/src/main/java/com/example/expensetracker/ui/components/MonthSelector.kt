package com.example.expensetracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.YearMonth
import java.util.Locale

@Composable
fun MonthSelector(
    selectedMonth: String,
    onMonthChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentMonth = remember { YearMonth.now() }
    val months = remember {
        (0..11).map { currentMonth.minusMonths(it.toLong()) }
    }

    var expanded by remember { mutableStateOf(false) }

    val formattedMonth = remember(selectedMonth) {
        try {
            val yearMonth = YearMonth.parse(selectedMonth)
            val monthName = yearMonth.month.getDisplayName(
                java.time.format.TextStyle.SHORT,
                Locale.getDefault()
            )
            val year = yearMonth.year.toString().takeLast(2)
            "$monthName $year"
        } catch (e: Exception) {
            selectedMonth
        }
    }

    Box(modifier = modifier) {

        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formattedMonth,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select month",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            months.forEach { month ->
                val formattedDisplayMonth = remember(month) {
                    val monthName = month.month.getDisplayName(
                        java.time.format.TextStyle.SHORT,
                        Locale.getDefault()
                    )
                    val year = month.year.toString().takeLast(2)
                    "$monthName $year"
                }
                DropdownMenuItem(
                    text = { 
                        Text(
                            text = formattedDisplayMonth,
                            modifier = Modifier.fillMaxWidth()
                        ) 
                    },
                    onClick = {
                        onMonthChange(month.toString())
                        expanded = false
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

