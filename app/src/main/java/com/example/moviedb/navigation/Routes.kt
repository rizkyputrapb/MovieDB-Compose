package com.example.moviedb.navigation

sealed class Routes(val route: String) {
    object Homepage : Routes("/")
    object GenreMoviesPage : Routes("/genre")
}
