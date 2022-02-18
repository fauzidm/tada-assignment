package dev.illwiz.tada.domain.usecase

import dev.illwiz.tada.domain.UseCase

abstract class LogoutUseCase: UseCase() {

    abstract suspend fun execute()
}