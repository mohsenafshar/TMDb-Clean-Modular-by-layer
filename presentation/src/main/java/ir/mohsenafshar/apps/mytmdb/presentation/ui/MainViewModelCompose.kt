package ir.mohsenafshar.apps.mytmdb.presentation.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mohsenafshar.apps.mytmdb.domain.interactor.FunInterfaceGetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SEARCH_QUERY = "searchQuery"

@HiltViewModel
class MainViewModelCompose @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val funInterfaceGetMovieListUseCase: FunInterfaceGetMovieListUseCase
): ViewModel() {
    val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies : StateFlow<List<MovieItem>> = searchQuery.flatMapConcat { query ->
        funInterfaceGetMovieListUseCase.run(GetMovieListUseCase.Params(1, query)).map { moviesAsResult ->
            moviesAsResult.getOrDefault(emptyList())
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun onSearchTriggered(query: String) {
        viewModelScope.launch {
            funInterfaceGetMovieListUseCase.run(GetMovieListUseCase.Params(1, query))
        }
    }
}