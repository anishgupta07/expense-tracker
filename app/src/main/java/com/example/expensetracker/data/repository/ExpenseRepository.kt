package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.data.local.entity.ExpenseSummary
import com.example.expensetracker.data.local.models.MonthlyExpense
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {

    suspend fun insertExpense(expense: ExpenseEntity) {
        expenseDao.insertExpense(expense)
    }

    fun getExpensesForDate(date: LocalDate): Flow<List<ExpenseEntity>> {
        return expenseDao.getExpensesForDate(date)
    }

    fun getTotalExpenseForDate(date: LocalDate): Flow<Double> {
        return expenseDao.getTotalExpenseForDate(date)
    }

    fun getTotalIncomeForDate(date: LocalDate): Flow<Double> {
        return expenseDao.getTotalIncomeForDate(date)
    }

    fun getMonthlyExpenses(month: String): Flow<List<MonthlyExpense>> {
        return expenseDao.getMonthlyExpenses(month)
    }

    fun getCategoryExpensesByMonth(month: String): Flow<List<ExpenseSummary>> {
        return expenseDao.getCategoryExpensesByMonth(month)
    }

}
