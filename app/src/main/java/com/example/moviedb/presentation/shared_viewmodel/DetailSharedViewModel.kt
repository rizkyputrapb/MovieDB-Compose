package com.example.moviedb.presentation.shared_viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.moviedb.domain.model.Movie

class DetailSharedViewModel {
    var movie by mutableStateOf<Movie?>(null)
        private set

    fun addMovie(newMovie : Movie) {
        movie = newMovie
    }
}