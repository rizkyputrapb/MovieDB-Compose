package com.example.moviedb.presentation.detail.viewmodel

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
import com.example.moviedb.domain.model.movie_detail.MovieDetail
import com.example.moviedb.domain.model.review.Review
import com.example.moviedb.domain.model.video.MovieVideo
import com.example.moviedb.domain.usecase.GetMovieDetailUseCase
import com.example.moviedb.domain.usecase.GetMovieReviewsUseCase
import com.example.moviedb.domain.usecase.GetMovieVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieVideoUseCase: GetMovieVideoUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
) : ViewModel() {
    private val _movieDetailState = mutableStateOf(MovieDetailState())
    val movieDetailState: State<MovieDetailState> = _movieDetailState

    private val _movieVideoState = mutableStateOf(MovieVideoState())
    val movieVideoState: State<MovieVideoState> = _movieVideoState

    private val _movieReviewState = mutableStateOf(MovieReviewState())
    val movieReviewState: State<MovieReviewState> = _movieReviewState

    val reviews: SnapshotStateList<Review> = mutableStateListOf()
    private var currentPage by mutableIntStateOf(1)
    private var totalPages by mutableIntStateOf(1)

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getMoviesByGenre(movieId: String) {
        getMovieDetailUseCase(movieId).onEach {
            when (it) {
                is Resource.Success -> {
                    _movieDetailState.value =
                        MovieDetailState(movies = it.data)
                }

                is Resource.Error -> {
                    Log.e("NetworkError", it.message ?: "An unexpected error occurred")
                    _movieDetailState.value =
                        MovieDetailState(error = it.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getMovieVideo(movieId: String) {
        getMovieVideoUseCase(movieId).onEach {
            when (it) {
                is Resource.Success -> {
                    _movieVideoState.value =
                        MovieVideoState(video = it.data?.results?.find { video -> video.type.lowercase() == "trailer" && video.site.lowercase() == "youtube" })
                }

                is Resource.Error -> {
                    Log.e("NetworkError", it.message ?: "An unexpected error occurred")
                    _movieVideoState.value =
                        MovieVideoState(error = it.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getMovieReviews(movieId: String) {
        if (currentPage <= totalPages) {
            getMovieReviewsUseCase(movieId, currentPage).onEach {
                when (it) {
                    is Resource.Success -> {
                        totalPages = it.data?.total_pages ?: 0
                        reviews.addAll(it.data?.results ?: emptyList())
                        _movieReviewState.value =
                            MovieReviewState(reviews = reviews)
                        currentPage += 1
                    }

                    is Resource.Error -> {
                        Log.e("NetworkError", it.message ?: "An unexpected error occurred")
                        _movieReviewState.value =
                            MovieReviewState(error = it.message ?: "An unexpected error occurred")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}

data class MovieDetailState(
    val movies: MovieDetail? = null,
    val error: String = ""
)

data class MovieReviewState(
    val reviews: List<Review>? = emptyList(),
    val error: String = ""
)

data class MovieVideoState(
    val video: MovieVideo? = null,
    val error: String = ""
)