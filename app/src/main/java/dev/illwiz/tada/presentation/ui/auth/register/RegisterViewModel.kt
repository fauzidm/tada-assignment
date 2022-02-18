package dev.illwiz.tada.presentation.ui.auth.register

import androidx.hilt.lifecycle.ViewModelInject
import dev.illwiz.tada.domain.usecase.RegisterUseCase
import dev.illwiz.tada.presentation.di.IODispatcher
import dev.illwiz.tada.presentation.ui.BaseViewModel
import dev.illwiz.tada.presentation.ui.Task
import kotlinx.coroutines.CoroutineDispatcher

sealed class RegisterTask:Task() {
    object Register:RegisterTask()
}

class RegisterViewModel @ViewModelInject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    private val registerUseCase: RegisterUseCase
):BaseViewModel(ioDispatcher) {

    override fun getTasks(): List<Task> {
        return listOf(RegisterTask.Register)
    }

    fun register(username:String,password:String) {
        launchTask<RegisterTask.Register,Unit>(ioContext) {
            registerUseCase.execute(username, password)
        }
    }
}