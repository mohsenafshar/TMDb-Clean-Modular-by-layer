package ir.mohsenafshar.apps.mytmdb.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.mohsenafshar.apps.mytmdb.data.IMovieDataSource
import ir.mohsenafshar.apps.mytmdb.data.MovieRepository
import ir.mohsenafshar.apps.mytmdb.data.remote.RemoteMovieDataSource
import ir.mohsenafshar.apps.mytmdb.domain.repo.IMovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class QRemoteMovieDataSource

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

//    @QRemoteMovieDataSource
    @Singleton
    @Provides
    fun provideRemoteMovieDataSource(remoteMovieDataSource: RemoteMovieDataSource): IMovieDataSource = remoteMovieDataSource

    @Singleton
    @Provides
    fun provideMovieRepository(movieRepository: MovieRepository): IMovieRepository = movieRepository
}

@Qualifier
annotation class IODispatcher

@Qualifier
annotation class MainDispatcher

@Qualifier
annotation class CpuDispatcher

@InstallIn(SingletonComponent::class)
@Module
class DispatcherModule {

    @IODispatcher
    @Singleton
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Singleton
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @CpuDispatcher
    @Singleton
    @Provides
    fun provideCpuDispatcher(): CoroutineDispatcher = Dispatchers.Default
}