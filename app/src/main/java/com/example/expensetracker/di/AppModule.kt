package com.example.expensetracker.di

import android.app.Application
import androidx.room.Room
import com.example.expensetracker.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "expense_tracker_db"
        ).build()
    }
    
    @Provides
    @Singleton
    fun provideExpenseDao(appDatabase: AppDatabase) = appDatabase.expenseDao()
    
}
