package com.example.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.data.local.entity.ExpenseSummary
import com.example.expensetracker.data.local.models.MonthlyExpense
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses WHERE date = :date ORDER BY id DESC")
    fun getExpensesForDate(date: LocalDate): Flow<List<ExpenseEntity>>

    @Query("SELECT COALESCE(SUM(amount), 0.0) FROM expenses WHERE date = :date AND type = 'Expense'")
    fun getTotalExpenseForDate(date: LocalDate): Flow<Double>

    @Query("SELECT COALESCE(SUM(amount), 0.0) FROM expenses WHERE date = :date AND type = 'Income'")
    fun getTotalIncomeForDate(date: LocalDate): Flow<Double>

    @Query("""
    SELECT date as label, COALESCE(SUM(amount), 0.0) as total 
    FROM expenses 
    WHERE type = 'Expense' 
    GROUP BY date 
    ORDER BY date
""")
    fun getDailyExpenseSummary(): Flow<List<ExpenseSummary>>


    @Query("""
    SELECT category as label, COALESCE(SUM(amount), 0.0) as total 
    FROM expenses 
    WHERE type = 'Expense'
    GROUP BY category
""")
    fun getCategoryExpenseSummary(): Flow<List<ExpenseSummary>>

    @Query("""
    SELECT 
        CAST(COALESCE(strftime('%d', date), '0') AS INTEGER) AS day,
        COALESCE(SUM(amount), 0.0) AS total
    FROM expenses
    WHERE type = 'Expense'
      AND strftime('%Y-%m', date) = :month
    GROUP BY day
    ORDER BY day
""")
    fun getMonthlyExpenses(month: String): Flow<List<MonthlyExpense>>

    @Query("""
    SELECT category as label, COALESCE(SUM(amount), 0.0) as total 
    FROM expenses 
    WHERE type = 'Expense'
      AND strftime('%Y-%m', date) = :month
    GROUP BY category
    ORDER BY total DESC
""")
    fun getCategoryExpensesByMonth(month: String): Flow<List<ExpenseSummary>>

}
