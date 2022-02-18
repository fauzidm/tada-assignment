package dev.illwiz.tada.data.usecase

import dev.illwiz.tada.data.pref.CurrentUserPref
import dev.illwiz.tada.domain.usecase.LoginUseCase
import dev.illwiz.tada.domain.user.UserDataSource
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val currentUserPref: CurrentUserPref
): LoginUseCase() {

    override suspend fun execute(username: String, password: String) {
        if(username.isBlank()) {
            throw IllegalArgumentException("Username should not be empty")
        }
        if(password.isBlank()) {
            throw IllegalArgumentException("Password should not be empty")
        }
        val user = userDataSource.getUserByUsername(username)
        if(user == null || password != user.password) {
            throw throw IllegalArgumentException("Authentication failed")
        }
        currentUserPref.setCurrentUser(user)
    }
}