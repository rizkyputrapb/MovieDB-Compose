package com.example.moviedb.presentation.genre.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.common.Resource
import com.example.moviedb.domain.model.movie.Movie
import com.example.moviedb.domain.usecase.GetMovieByGenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val getMovieByGenreUseCase: GetMovieByGenreUseCase,
) : ViewModel() {
    private val _moviesState = mutableStateOf(GenreMoviesState())
    val moviesState: State<GenreMoviesState> = _moviesState

    val movies: SnapshotStateList<Movie> = mutableStateListOf()
    private var currentPage by mutableIntStateOf(1)
    private var totalPages by mutableIntStateOf(1)

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getMoviesByGenre(genreId: String) {
        if (currentPage <= totalPages) {
            getMovieByGenreUseCase(genreId, currentPage).onEach {
                when (it) {
                    is Resource.Success -> {
                        totalPages = it.data?.total_pages ?: 0
                        movies.addAll(it.data?.results ?: emptyList())
                        _moviesState.value =
                            GenreMoviesState(movies = movies)
                        currentPage += 1
                    }

                    is Resource.Error -> {
                        Log.e("NetworkError", it.message ?: "An unexpected error occurred")
                        _moviesState.value =
                            GenreMoviesState(error = it.message ?: "An unexpected error occurred")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}

data class GenreMoviesState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = "",
    val endReached: Boolean = false,
    val page: Int = 0
)

