package com.example.moviedb.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviedb.presentation.detail.MovieDetailPage
import com.example.moviedb.presentation.genre.GenreMoviesPage
import com.example.moviedb.presentation.homepage.Homepage
import com.example.moviedb.presentation.shared_viewmodel.DetailSharedViewModel
import com.example.moviedb.presentation.shared_viewmodel.HomeGenreViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun Navigation(navController: NavHostController) {
    val homeGenreViewModel: HomeGenreViewModel = viewModel()
    val movieSharedViewModel : DetailSharedViewModel = viewModel()
    NavHost(navController = navController, startDestination = Routes.Homepage.route) {
        composable(route = Routes.Homepage.route) {
            Homepage(navController = navController, homeGenreViewModel = homeGenreViewModel)
        }
        composable(route = Routes.GenreMoviesPage.route) {
            GenreMoviesPage(
                navController = navController,
                homeGenreViewModel = homeGenreViewModel,
                movieSharedViewModel = movieSharedViewModel
            )
        }
        composable(route = Routes.MovieDetailPage.route) {
            MovieDetailPage(navController = navController, sharedViewModel = movieSharedViewModel)
        }
    }
}