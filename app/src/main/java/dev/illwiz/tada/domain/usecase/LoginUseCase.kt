package dev.illwiz.tada.domain.usecase

import dev.illwiz.tada.domain.UseCase

abstract class LoginUseCase: UseCase() {

    abstract suspend fun execute(username:String,password:String)
}