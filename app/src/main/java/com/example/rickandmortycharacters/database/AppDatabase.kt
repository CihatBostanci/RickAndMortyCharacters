package com.example.rickandmortycharacters.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmortycharacters.model.UserModel

@Database(entities = [UserModel::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDAO: UserDAO

}