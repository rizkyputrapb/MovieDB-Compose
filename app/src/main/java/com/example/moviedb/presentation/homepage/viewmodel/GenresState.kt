package com.example.moviedb.presentation.homepage.viewmodel

import com.example.moviedb.domain.model.genre.Genre

data class GenresState(
    val genres: List<Genre> = emptyList(),
    val error: String = ""
)