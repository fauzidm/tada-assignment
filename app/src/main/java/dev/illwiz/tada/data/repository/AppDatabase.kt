package dev.illwiz.tada.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.illwiz.tada.data.repository.user.UserDB
import dev.illwiz.tada.data.repository.user.UserDao

@Database(entities = [UserDB::class],exportSchema = false,version = 1)
abstract class AppDatabase:RoomDatabase() {
    companion object {
        const val DB_NAME = "AppDatabase.db"
    }

    abstract fun userDao():UserDao
}