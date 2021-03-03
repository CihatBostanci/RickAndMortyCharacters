package com.example.rickandmortycharacters.di

import android.app.Application
import androidx.room.Room
import com.example.rickandmortycharacters.database.AppDatabase
import com.example.rickandmortycharacters.database.UserDAO
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "eds.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideUserDao(database: AppDatabase): UserDAO {
        return database.userDAO
    }

    single { provideDatabase(androidApplication()) }
    single { provideUserDao(get()) }

}