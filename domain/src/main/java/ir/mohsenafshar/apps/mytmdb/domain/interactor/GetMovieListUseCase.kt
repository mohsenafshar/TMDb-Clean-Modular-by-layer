package ir.mohsenafshar.apps.mytmdb.domain.interactor

import ir.mohsenafshar.apps.mytmdb.domain.UseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import ir.mohsenafshar.apps.mytmdb.domain.repo.IMovieRepository
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(private val movieRepository: IMovieRepository) :
    UseCase<List<MovieItem>, GetMovieListUseCase.Params>() {
    override suspend operator fun invoke(params: Params): Result<List<MovieItem>> {
        return movieRepository.getMovieList(params)
    }

    class Params(
        val pageNo: Int = 1,
        val dateFilter: String?,
    )
}