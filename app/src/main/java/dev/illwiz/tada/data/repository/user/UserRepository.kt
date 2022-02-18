package dev.illwiz.tada.data.repository.user

import dev.illwiz.tada.data.repository.AppDatabase
import dev.illwiz.tada.domain.user.User
import dev.illwiz.tada.domain.user.UserDataSource
import javax.inject.Inject

class UserRepository @Inject constructor(
    appDatabase: AppDatabase
):UserDataSource() {
    private val userDao = appDatabase.userDao()

    override suspend fun saveUser(user: User): Long {
        return userDao.save(UserDB(user))
    }

    override suspend fun getUserByUsername(username: String): User? {
        return userDao.findByUsername(username)?.toDomain()
    }

    override suspend fun getUserById(id: Long): User? {
        return userDao.findById(id)?.toDomain()
    }
}