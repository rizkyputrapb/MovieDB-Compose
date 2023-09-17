package com.example.moviedb.presentation.detail

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviedb.presentation.application.BaseScaffold
import com.example.moviedb.presentation.application.YoutubeScreen
import com.example.moviedb.presentation.detail.viewmodel.MovieDetailViewModel
import com.example.moviedb.presentation.shared_viewmodel.DetailSharedViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun MovieDetailPage(
    navController: NavController,
    sharedViewModel: DetailSharedViewModel,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val movieDetailState = viewModel.movieDetailState.value
    val movie = sharedViewModel.movie

    LaunchedEffect(Unit) {
        viewModel.getMoviesByGenre(movie?.id.toString())
    }
    BaseScaffold(
        title = sharedViewModel.movie?.title ?: "MovieDB",
        navController = navController
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                YoutubeScreen(videoId = "aLAKJu9aJys", modifier = Modifier.fillParentMaxWidth())
            }
            item {
                Column(
                    Modifier
                        .fillParentMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = movie?.title ?: "",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                    )
                    if (movieDetailState.movies?.tagline != null) Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = movieDetailState.movies.tagline,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Start,
                    )
                    if (movieDetailState.movies?.overview != null) Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = movieDetailState.movies.overview,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 14.sp,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}