package dev.illwiz.tada.presentation.di

import javax.inject.Qualifier

// Qualifier
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutineApplicationScope