package dev.illwiz.tada.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @CoroutineApplicationScope
    fun applicationScope(@IODispatcher ioDispatcher: CoroutineDispatcher): CoroutineScope {
        return CoroutineScope(SupervisorJob() + ioDispatcher)
    }

    @Provides
    @Singleton
    @IODispatcher
    fun coroutineIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}