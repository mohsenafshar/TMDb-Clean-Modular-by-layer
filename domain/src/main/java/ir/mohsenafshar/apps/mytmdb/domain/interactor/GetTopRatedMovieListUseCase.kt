package ir.mohsenafshar.apps.mytmdb.domain.interactor

import ir.mohsenafshar.apps.mytmdb.domain.UseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import ir.mohsenafshar.apps.mytmdb.domain.repo.IMovieRepository
import javax.inject.Inject

class GetTopRatedMovieListUseCase @Inject constructor(private val movieRepository: IMovieRepository) : UseCase<List<MovieItem>, GetTopRatedMovieListUseCase.Params>() {

    override suspend fun invoke(params: Params): Result<List<MovieItem>> {
        return movieRepository.getTopRated(params.pageNo)
    }

    class Params(
        val pageNo: Int = 1
    )
}