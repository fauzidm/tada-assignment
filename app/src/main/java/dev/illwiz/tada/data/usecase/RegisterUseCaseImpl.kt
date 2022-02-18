package dev.illwiz.tada.data.usecase

import dev.illwiz.tada.domain.usecase.RegisterUseCase
import dev.illwiz.tada.domain.user.User
import dev.illwiz.tada.domain.user.UserDataSource
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val userDataSource: UserDataSource
): RegisterUseCase() {

    override suspend fun execute(username: String, password: String) {
        if(username.isBlank()) {
            throw IllegalArgumentException("Username should not be empty")
        }
        if(password.isBlank()) {
            throw IllegalArgumentException("Password should not be empty")
        }
        val user = userDataSource.getUserByUsername(username)
        if(user != null) {
            throw throw IllegalArgumentException("User already registered")
        }
        val avatar = "https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png"
        userDataSource.saveUser(User(username = username,password = password,avatar = avatar))
    }
}