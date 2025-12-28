package com.example.expensetracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    val dates: StateFlow<List<LocalDate>> =
        MutableStateFlow(getDates())

    val expenses: StateFlow<List<ExpenseEntity>> =
        _selectedDate.flatMapLatest { date ->
            expenseRepository.getExpensesForDate(date)
                .flowOn(kotlinx.coroutines.Dispatchers.IO)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val totalExpense: StateFlow<Double> =
        _selectedDate.flatMapLatest { date ->
            expenseRepository.getTotalExpenseForDate(date)
                .flowOn(kotlinx.coroutines.Dispatchers.IO)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0.0)

    val totalIncome: StateFlow<Double> =
        _selectedDate.flatMapLatest { date ->
            expenseRepository.getTotalIncomeForDate(date)
                .flowOn(kotlinx.coroutines.Dispatchers.IO)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0.0)

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
    }

    private fun getDates(): List<LocalDate> =
        (0..6).map { LocalDate.now().minusDays(it.toLong()) }.reversed()
}
