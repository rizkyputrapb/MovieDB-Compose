package com.example.moviedb.presentation.shared_viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.moviedb.domain.model.genre.Genre

class HomeGenreViewModel : ViewModel() {
    var genre by mutableStateOf<Genre?>(null)
        private set

    fun addGenre(newGenre : Genre) {
        genre = newGenre
    }
}