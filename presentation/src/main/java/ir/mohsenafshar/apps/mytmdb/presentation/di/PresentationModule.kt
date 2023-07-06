package ir.mohsenafshar.apps.mytmdb.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.mohsenafshar.apps.mytmdb.domain.interactor.FunInterfaceGetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.repo.IMovieRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PresentationModule {

    @Provides
    @Singleton
    fun provideFunInterface(repository: IMovieRepository, getMovieListUseCase: GetMovieListUseCase) : FunInterfaceGetMovieListUseCase {
        return FunInterfaceGetMovieListUseCase(getMovieListUseCase::invoke)
    }
}