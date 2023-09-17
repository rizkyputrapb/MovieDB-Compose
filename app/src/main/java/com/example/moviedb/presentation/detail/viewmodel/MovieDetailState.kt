package com.example.moviedb.presentation.detail.viewmodel

import com.example.moviedb.domain.model.movie_detail.MovieDetail

data class MovieDetailState(
    val movies: MovieDetail? = null,
    val error: String = ""
)