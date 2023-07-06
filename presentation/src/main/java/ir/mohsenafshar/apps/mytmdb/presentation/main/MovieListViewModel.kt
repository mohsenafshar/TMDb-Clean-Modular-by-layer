package ir.mohsenafshar.apps.mytmdb.presentation.main

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mohsenafshar.apps.mytmdb.domain.interactor.FunInterfaceGetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetTopRatedMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListUseCase: GetMovieListUseCase,
    private val topRatedMovieListUseCase: GetTopRatedMovieListUseCase,
    private val funInterfaceGetMovieListUseCase: FunInterfaceGetMovieListUseCase,
) : ViewModel() {

    private val mCurrentPageIndex: MutableLiveData<Int> = MutableLiveData(1)
    private val mQuery = MutableLiveData("")

    val movies: LiveData<List<MovieItem>> = mCurrentPageIndex.switchMap { pageNo ->
        liveData {
            funInterfaceGetMovieListUseCase.run(GetMovieListUseCase.Params(pageNo, mQuery.value)).collect {
                val data = it.getOrDefault(emptyList())
                emit(movieList + data)
                movieList.addAll(data)
            }

            funInterfaceGetMovieListUseCase.doLog()
        }
    }
    private val movieList: MutableList<MovieItem> = mutableListOf()

    fun setQuery(originalInput: String) {
        if (originalInput == mQuery.value) {
            return
        }
        mQuery.value = originalInput
        mCurrentPageIndex.value = 1
    }

    fun loadNextPage() {
        mCurrentPageIndex.value = mCurrentPageIndex.value!!.plus(1)
    }
}