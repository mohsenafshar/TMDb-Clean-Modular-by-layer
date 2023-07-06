package ir.mohsenafshar.apps.mytmdb.domain.interactor

import ir.mohsenafshar.apps.mytmdb.domain.UseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import ir.mohsenafshar.apps.mytmdb.domain.repo.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(private val movieRepository: IMovieRepository)
//    : UseCase<List<MovieItem>, GetMovieListUseCase.Params>()
{
    suspend operator fun invoke(params: Params): Flow<Result<List<MovieItem>>> {
        FunInterfaceGetMovieListUseCase {params: Params ->
            flow { }
        }
        return movieRepository.getMovieList(params)
    }

    class Params(
        val pageNo: Int = 1,
        val dateFilter: String?,
    )
}

fun interface FunInterfaceGetMovieListUseCase {
    suspend fun run(params: GetMovieListUseCase.Params): Flow<Result<List<MovieItem>>>

    suspend fun doLog() {
    }
}