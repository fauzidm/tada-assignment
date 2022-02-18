package dev.illwiz.tada.presentation.ui.profile

import androidx.hilt.lifecycle.ViewModelInject
import dev.illwiz.tada.domain.usecase.LogoutUseCase
import dev.illwiz.tada.presentation.di.IODispatcher
import dev.illwiz.tada.presentation.ui.BaseViewModel
import dev.illwiz.tada.presentation.ui.Task
import kotlinx.coroutines.CoroutineDispatcher

sealed class ProfileTask:Task() {
    object Logout:ProfileTask()
}

class ProfileViewModel @ViewModelInject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    private val logoutUseCase: LogoutUseCase
):BaseViewModel(ioDispatcher) {

    override fun getTasks(): List<Task> {
        return listOf(ProfileTask.Logout)
    }

    fun logout() {
        launchTask<ProfileTask.Logout,Unit>(ioContext) {
            logoutUseCase.execute()
        }
    }
}