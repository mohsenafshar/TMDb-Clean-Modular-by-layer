package ir.mohsenafshar.apps.mytmdb.presentation.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import coil.compose.AsyncImage
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import ir.mohsenafshar.apps.mytmdb.presentation.R
import ir.mohsenafshar.apps.mytmdb.presentation.ui.MainViewModelCompose
import kotlinx.coroutines.launch
import java.util.Locale

const val homeNavigationGraph = "home_graph"
const val homeNavigationRoute = "home_route"

private const val DEEP_LINK_URI_PATTERN = "https://www.nowinandroid.apps.samples.google.com/home/"

fun NavController.navigateToHomeScreen(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationGraph, navOptions)
}

fun NavGraphBuilder.homeGraph(block: NavGraphBuilder.() -> Unit) {
    navigation(route = homeNavigationGraph, startDestination = homeNavigationRoute) {
        block()
    }
}

fun NavGraphBuilder.homeScreen(showDetailScreen: () -> Unit, onSearchTriggered: (String) -> Unit) {
    composable(
        route = homeNavigationRoute,
        deepLinks = listOf(),
        arguments = listOf(),
    ) {
        HomeScreen(
            showDetailScreen = showDetailScreen, onSearchTriggered = onSearchTriggered
        )
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, showDetailScreen: () -> Unit, onSearchTriggered: (String) -> Unit
) {
    val mainViewModelCompose: MainViewModelCompose = hiltViewModel()
    val searchQuery by mainViewModelCompose.searchQuery.collectAsStateWithLifecycle()
    val movies by mainViewModelCompose.movies.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()
    val shouldShowFab by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            MovieSearchBar(
                onSearchQueryChanged = mainViewModelCompose::onSearchQueryChanged, searchQuery = searchQuery, onSearchTriggered = onSearchTriggered
//              onSearchTriggered = mainViewModelCompose::onSearchTriggered
            )

            if (movies.isNotEmpty()) {
                MovieListSection(titleTextId = R.string.popular_movies, list = movies, loadMore = {
                    showDetailScreen()
                })
                MovieListSection(titleTextId = R.string.top_rated_movies, list = movies)
                MovieListSection(titleTextId = R.string.upcoming_movies, list = movies)
            }
        }

        if (shouldShowFab) {
            MyFloatingActionButton(modifier = Modifier.align(Alignment.BottomEnd), onFabClick = {
                lazyListState.animateScrollToItem(0)
            })
        }
    }
}

@Composable
fun MovieListSection(
    @StringRes titleTextId: Int, list: List<MovieItem>, loadMore: () -> Unit = {}
) {
    MoviesSectionHeadLine(titleTextId)
    MovieList(modifier = Modifier.padding(top = 8.dp), movies = list, loadMore = loadMore)
}

@Composable
fun MoviesSectionHeadLine(@StringRes textId: Int) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 32.dp), text = stringResource(id = textId).uppercase(Locale.getDefault()), style = MaterialTheme.typography.titleLarge
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieList(
    modifier: Modifier = Modifier, movies: List<MovieItem>, loadMore: () -> Unit = {}, listState: LazyListState = rememberLazyListState()
) {
    LazyRow(modifier = modifier, contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically, state = listState, content = {
        items(movies, key = { it.id }) {
            MovieRow(
                modifier = Modifier
                    .animateItemPlacement()
                    .fillParentMaxWidth(0.9f), movie = it
            )
        }

        item {
            LoadMore(modifier = Modifier, loadMore = loadMore)
        }
    })
}

@Composable
fun MovieRow(modifier: Modifier = Modifier, movie: MovieItem) {
    println(movie.imageUrl)
    Card(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(0.dp)
            .clickable {
                println("CLIKEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD ${movie.id}")
            }, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(modifier = Modifier) {
            AsyncImage(
                modifier = Modifier.aspectRatio(0.8f, true),
                model = movie.imageUrl,
                placeholder = painterResource(id = R.drawable.ic_icon_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Column(
                horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
            ) {
                Text(
                    text = movie.title, style = MaterialTheme.typography.titleMedium, overflow = TextOverflow.Ellipsis, maxLines = 1
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = movie.overview,
                    style = MaterialTheme.typography.labelMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    minLines = 3,
                )
            }
        }
    }
}

@Composable
fun LoadMore(modifier: Modifier = Modifier, loadMore: () -> Unit) {
    FilledIconButton(modifier = modifier, colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer), onClick = { loadMore() }) {
        Icon(
            modifier = Modifier.size(32.dp), imageVector = Icons.Default.ArrowRight, contentDescription = null
        )
    }
//    Row(modifier = modifier
//        .clip(CircleShape)
//        .background(MaterialTheme.colorScheme.secondaryContainer)
//        .padding(16.dp)
//        .clickable {
//            loadMore()
//        },
//        verticalAlignment = Alignment.CenterVertically,
//    ) {
//        Icon(
//            modifier = Modifier.size(32.dp),
//            imageVector = Icons.Default.ArrowRight,
//            contentDescription = null
//        )
//    }
}

@Composable
fun MyFloatingActionButton(
    modifier: Modifier = Modifier, onFabClick: suspend () -> Unit
) {
    val scope = rememberCoroutineScope()

    FloatingActionButton(
        onClick = {
            scope.launch {
                onFabClick()
            }
        },
        modifier = modifier.padding(16.dp),
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
    ) {
        Icon(
            imageVector = Icons.Default.ArrowDropUp, contentDescription = stringResource(id = R.string.scroll_up), modifier = Modifier.size(32.dp)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MovieSearchBar(
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit,
    searchQuery: String,
    onSearchTriggered: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchTriggered(searchQuery)
    }

    TextField(value = searchQuery, onValueChange = {
        if (!it.contains("\n")) {
            onSearchQueryChanged(it)
        }
    }, placeholder = {
        Text(text = stringResource(id = R.string.placeholder_search_movie))
    }, colors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
    ), modifier = modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
        .onKeyEvent {
            if (it.key == Key.Enter) {
                onSearchExplicitlyTriggered()
                true
            } else {
                false
            }
        }, shape = RoundedCornerShape(8.dp), keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Search,
    ), keyboardActions = KeyboardActions(
        onSearch = {
            onSearchExplicitlyTriggered()
        },
    ), maxLines = 1, singleLine = true
    )
}