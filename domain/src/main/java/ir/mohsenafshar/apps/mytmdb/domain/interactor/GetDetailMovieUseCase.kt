package ir.mohsenafshar.apps.mytmdb.domain.interactor

import ir.mohsenafshar.apps.mytmdb.domain.UseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieDetail
import ir.mohsenafshar.apps.mytmdb.domain.repo.IMovieRepository
import javax.inject.Inject

class GetDetailMovieUseCase @Inject constructor(private val movieRepository: IMovieRepository) : UseCase<MovieDetail, GetDetailMovieUseCase.Params>() {

    override suspend fun invoke(params: Params): Result<MovieDetail> {
        return movieRepository.getDetail(params.movieId)
    }

    data class Params(
        val movieId: Long
    )
}