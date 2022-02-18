package dev.illwiz.tada.presentation.ui.auth.login

import androidx.hilt.lifecycle.ViewModelInject
import dev.illwiz.tada.domain.usecase.LoginUseCase
import dev.illwiz.tada.presentation.di.IODispatcher
import dev.illwiz.tada.presentation.ui.BaseViewModel
import dev.illwiz.tada.presentation.ui.Task
import kotlinx.coroutines.CoroutineDispatcher

sealed class LoginTask:Task() {
    object Login:LoginTask()
}

class LoginViewModel @ViewModelInject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    private val loginUseCase: LoginUseCase
):BaseViewModel(ioDispatcher) {

    override fun getTasks(): List<Task> {
        return listOf(LoginTask.Login)
    }

    fun login(username:String,password:String) {
        launchTask<LoginTask.Login,Unit>(ioContext) {
            loginUseCase.execute(username, password)
        }
    }
}