package dev.illwiz.tada.domain.usecase

import dev.illwiz.tada.domain.UseCase

abstract class RegisterUseCase: UseCase() {

    abstract suspend fun execute(username:String,password:String)
}