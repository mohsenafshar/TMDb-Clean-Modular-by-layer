package ir.mohsenafshar.apps.mytmdb.presentation.main

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetTopRatedMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieListUseCase: GetMovieListUseCase, private val topRatedMovieListUseCase: GetTopRatedMovieListUseCase) : ViewModel() {
    fun getMovieList(params: GetMovieListUseCase.Params) {
        viewModelScope.launch {
            val result = movieListUseCase.invoke(GetMovieListUseCase.Params(1, ""))
            if (result.isSuccess) {
                _movieList.value = result.getOrDefault(emptyList())
            } else {
                Timber.tag("TIMBER_MY_LIST").d(result.exceptionOrNull()?.message)
                Log.d("MY_LIST", result.exceptionOrNull()?.message.toString())
            }
        }
    }

    private val _movieList = MutableLiveData<List<MovieItem>>()

    private val mCurrentPageIndex: MutableLiveData<Int> = MutableLiveData(1)
    private val mQuery = MutableLiveData("")

    val movies: LiveData<List<MovieItem>> = mCurrentPageIndex.switchMap { pageNo ->
        liveData {
            val data = movieListUseCase.invoke(GetMovieListUseCase.Params(pageNo, mQuery.value)).getOrDefault(emptyList())
            emit(listOf(movieListLiveData.value ?: emptyList(), data).flatten())
        }
    }
    private val movieListLiveData: LiveData<List<MovieItem>> = movies

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