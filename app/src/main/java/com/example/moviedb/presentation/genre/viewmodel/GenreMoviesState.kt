package com.example.moviedb.presentation.genre.viewmodel

import com.example.moviedb.domain.model.movie.Movie

data class GenreMoviesState(
    val movies: List<Movie> = emptyList(),
    val error: String = ""
)