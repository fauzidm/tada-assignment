package dev.illwiz.tada.data.repository.user

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import dev.illwiz.tada.data.repository.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao: BaseDao<UserDB> {

    @Query("delete from UserDB")
    suspend fun deleteAll()

    @Query("select * from UserDB")
    fun findAllPaging(): PagingSource<Int, UserDB>

    @Query("select * from UserDB")
    suspend fun findAll(): List<UserDB>

    @Query("select * from UserDB ce limit :pageSize offset :page")
    fun findAll(page:Int,pageSize:Int): Flow<List<UserDB>>

    @Query("select * from UserDB ce where ce.id=:id limit 1")
    fun findByIdFlow(id:Long): Flow<UserDB>

    @Query("select * from UserDB ce where ce.id=:id limit 1")
    suspend fun findById(id:Long): UserDB?

    @Query("select * from UserDB ce where ce.username=:username limit 1")
    suspend fun findByUsername(username:String): UserDB?

    @Query("select count(*) from UserDB")
    suspend fun count(): Int
}