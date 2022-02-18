package dev.illwiz.tada.presentation.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.illwiz.tada.data.pref.CurrentUserPref
import dev.illwiz.tada.data.repository.AppDatabase
import dev.illwiz.tada.data.repository.art.ArtRepository
import dev.illwiz.tada.data.repository.remote.ArtAPI
import dev.illwiz.tada.data.repository.user.UserRepository
import dev.illwiz.tada.data.usecase.LoginUseCaseImpl
import dev.illwiz.tada.data.usecase.LogoutUseCaseImpl
import dev.illwiz.tada.data.usecase.RegisterUseCaseImpl
import dev.illwiz.tada.domain.art.ArtDataSource
import dev.illwiz.tada.domain.usecase.LoginUseCase
import dev.illwiz.tada.domain.usecase.LogoutUseCase
import dev.illwiz.tada.domain.usecase.RegisterUseCase
import dev.illwiz.tada.domain.user.UserDataSource
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    companion object {

        @Provides
        @Singleton
        fun appDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DB_NAME
            ).build()
        }

        @Provides
        @Singleton
        fun currentUserPref(@ApplicationContext context: Context,gson: Gson): CurrentUserPref {
            return CurrentUserPref(context, gson)
        }

        @Provides
        @Singleton
        fun appAPI(retrofit: Retrofit): ArtAPI {
            return retrofit.create(ArtAPI::class.java)
        }
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun userDataSource(userRepository: UserRepository):UserDataSource

    @Binds
    abstract fun artDataSource(artRepository: ArtRepository):ArtDataSource

}

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun loginUseCase(loginUseCaseImpl: LoginUseCaseImpl): LoginUseCase

    @Binds
    abstract fun registerUseCase(registerUseCaseImpl: RegisterUseCaseImpl): RegisterUseCase

    @Binds
    abstract fun logoutUseCase(logoutUseCaseImpl: LogoutUseCaseImpl): LogoutUseCase

}