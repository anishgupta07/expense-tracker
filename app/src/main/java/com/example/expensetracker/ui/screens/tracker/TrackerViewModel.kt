package com.example.expensetracker.ui.screens.tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.entity.ExpenseSummary
import com.example.expensetracker.data.local.models.MonthlyExpense
import com.example.expensetracker.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _monthlyExpenses =
        MutableStateFlow<List<MonthlyExpense>>(emptyList())
    val monthlyExpenses: StateFlow<List<MonthlyExpense>> = _monthlyExpenses

    private val _categoryExpenses =
        MutableStateFlow<List<ExpenseSummary>>(emptyList())
    val categoryExpenses: StateFlow<List<ExpenseSummary>> = _categoryExpenses

    private val _selectedMonth =
        MutableStateFlow(getCurrentMonth())
    val selectedMonth: StateFlow<String> = _selectedMonth

    init {
        loadMonthlyExpenses(_selectedMonth.value)
    }

    fun loadMonthlyExpenses(month: String) {
        _selectedMonth.value = month

        repository.getMonthlyExpenses(month)
            .flowOn(kotlinx.coroutines.Dispatchers.IO)
            .onEach { _monthlyExpenses.value = it }
            .launchIn(viewModelScope)

        repository.getCategoryExpensesByMonth(month)
            .flowOn(kotlinx.coroutines.Dispatchers.IO)
            .onEach { _categoryExpenses.value = it }
            .launchIn(viewModelScope)
    }

    private fun getCurrentMonth(): String {
        return LocalDate.now().toString().substring(0, 7)
    }
}
