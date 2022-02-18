package dev.illwiz.tada.presentation.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import dev.illwiz.tada.domain.art.Art
import dev.illwiz.tada.domain.art.ArtDataSource
import dev.illwiz.tada.presentation.di.IODispatcher
import dev.illwiz.tada.presentation.ui.BaseViewModel
import dev.illwiz.tada.presentation.ui.Task
import kotlinx.coroutines.CoroutineDispatcher

sealed class DetailTask:Task() {
    object FetchDetail:DetailTask()
}

class DetailViewModel @ViewModelInject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    private val artDataSource: ArtDataSource
):BaseViewModel(ioDispatcher) {

    override fun getTasks(): List<Task> {
        return listOf(DetailTask.FetchDetail)
    }

    fun fetchDetail(id:String) {
        launchTask<DetailTask.FetchDetail,Art>(ioContext) {
            artDataSource.getArtByObjectNumber(id)
        }
    }
}