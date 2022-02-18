package dev.illwiz.tada.data.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(obj: List<T>): LongArray

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(obj: T)

    @Delete
    suspend fun delete(obj: T)
}