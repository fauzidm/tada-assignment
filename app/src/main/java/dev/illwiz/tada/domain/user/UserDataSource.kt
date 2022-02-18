package dev.illwiz.tada.domain.user

import dev.illwiz.tada.domain.BaseDataSource

abstract class UserDataSource: BaseDataSource() {

    abstract suspend fun saveUser(user: User):Long

    abstract suspend fun getUserByUsername(username:String):User?

    abstract suspend fun getUserById(id:Long):User?
}