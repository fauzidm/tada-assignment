package dev.illwiz.tada.data.usecase

import dev.illwiz.tada.data.pref.CurrentUserPref
import dev.illwiz.tada.domain.usecase.LogoutUseCase
import javax.inject.Inject

class LogoutUseCaseImpl @Inject constructor(
    private val currentUserPref: CurrentUserPref
): LogoutUseCase() {

    override suspend fun execute() {
        currentUserPref.clear()
    }
}