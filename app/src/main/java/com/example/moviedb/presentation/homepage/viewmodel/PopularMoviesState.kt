package com.example.moviedb.presentation.homepage.viewmodel

import com.example.moviedb.domain.model.movie.Movie

data class PopularMoviesState(
    val popularMovies: List<Movie> = emptyList(),
    val error : String = ""
)
