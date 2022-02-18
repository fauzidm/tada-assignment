package dev.illwiz.tada.presentation.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dev.illwiz.tada.domain.art.Art
import dev.illwiz.tada.domain.art.ArtDataSource
import dev.illwiz.tada.presentation.di.IODispatcher
import dev.illwiz.tada.presentation.ui.BaseViewModel
import dev.illwiz.tada.presentation.ui.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

sealed class HomeContentTask:Task() {
    object GetArtCollection:HomeContentTask()
}

class HomeContentViewModel @ViewModelInject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    private val artDataSource: ArtDataSource
):BaseViewModel(ioDispatcher) {

    override fun getTasks(): List<Task> {
        return listOf(HomeContentTask.GetArtCollection)
    }

    fun getArtCollection(): Flow<PagingData<Art>> {
        val pageSize = 10
        val pagingSource = object : PagingSource<Int, Art>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Art> {
                try {
                    // Start refresh at page 0 if undefined.
                    val pageNumber = params.key ?: 0
                    val response = artDataSource.getAllArt(pageNumber, params.loadSize)
                    val nextPageNumber = if(response.isNotEmpty() && response.size>=params.loadSize) pageNumber+1 else null
                    return LoadResult.Page(
                        data = response,
                        prevKey = null, // Only paging forward.
                        nextKey = nextPageNumber
                    )
                } catch (e: Exception) {
                    return LoadResult.Error(e)
                }
            }
            override fun getRefreshKey(state: PagingState<Int, Art>): Int? {
                return null
            }
        }
        return Pager(
            PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize
            )
        ) { pagingSource }.flow.cachedIn(viewModelScope)
    }
}