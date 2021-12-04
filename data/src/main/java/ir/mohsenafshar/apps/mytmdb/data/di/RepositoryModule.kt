package ir.mohsenafshar.apps.mytmdb.data.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.mohsenafshar.apps.mytmdb.data.IMovieDataSource
import ir.mohsenafshar.apps.mytmdb.data.Mapper
import ir.mohsenafshar.apps.mytmdb.data.MovieRepository
import ir.mohsenafshar.apps.mytmdb.data.local.LocalMovieDataSource
import ir.mohsenafshar.apps.mytmdb.data.local.MovieDao
import ir.mohsenafshar.apps.mytmdb.data.local.MovieDatabase
import ir.mohsenafshar.apps.mytmdb.data.remote.RemoteMovieDataSource
import ir.mohsenafshar.apps.mytmdb.domain.repo.IMovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMoveDatabase(@ApplicationContext applicationContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java, "movie.db"
        ).build()
    }

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }
}

@Qualifier
annotation class QRemoteMovieDataSource

@Qualifier
annotation class QLocalMovieDataSource

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @QRemoteMovieDataSource
    @Singleton
    @Provides
    fun provideRemoteMovieDataSource(remoteMovieDataSource: RemoteMovieDataSource): IMovieDataSource = remoteMovieDataSource

    @QLocalMovieDataSource
    @Singleton
    @Provides
    fun provideLocalMovieDataSource(localMovieDataSource: LocalMovieDataSource): IMovieDataSource = localMovieDataSource

    @Singleton
    @Provides
    fun provideMovieRepository(movieRepository: MovieRepository): IMovieRepository = movieRepository

    @Singleton
    @Provides
    fun provideMapper(gson: Gson): Mapper = Mapper(gson)
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