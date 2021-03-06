package ir.mohsenafshar.apps.mytmdb.domain.repo

import ir.mohsenafshar.apps.mytmdb.domain.model.MovieDetail
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetMovieListUseCase

interface IMovieRepository {
    suspend fun getMovieList(params: GetMovieListUseCase.Params): Result<List<MovieItem>>
    suspend fun getTopRated(pageNo: Int): Result<List<MovieItem>>
    suspend fun getDetail(id: Long): Result<MovieDetail>
}