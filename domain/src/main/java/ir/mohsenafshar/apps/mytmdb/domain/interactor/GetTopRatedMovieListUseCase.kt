package ir.mohsenafshar.apps.mytmdb.domain.interactor

import ir.mohsenafshar.apps.mytmdb.domain.UseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import ir.mohsenafshar.apps.mytmdb.domain.repo.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMovieListUseCase @Inject constructor(private val movieRepository: IMovieRepository)
//    : UseCase<List<MovieItem>, GetTopRatedMovieListUseCase.Params>()
{

     suspend fun invoke(params: Params): Flow<Result<List<MovieItem>>> {
        return movieRepository.getTopRated(params.pageNo)
    }

    class Params(
        val pageNo: Int = 1
    )
}