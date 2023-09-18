package com.example.moviedb.presentation.genre

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviedb.navigation.Routes
import com.example.moviedb.presentation.application.BaseScaffold
import com.example.moviedb.presentation.genre.viewmodel.GenreViewModel
import com.example.moviedb.presentation.shared_viewmodel.DetailSharedViewModel
import com.example.moviedb.presentation.shared_viewmodel.HomeGenreViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun GenreMoviesPage(
    navController: NavController,
    homeGenreViewModel: HomeGenreViewModel,
    viewModel: GenreViewModel = hiltViewModel(),
    movieSharedViewModel: DetailSharedViewModel,
) {
    val movieState = viewModel.moviesState.value
    val movies = viewModel.movies.toList()
    val genre = homeGenreViewModel.genre

    LaunchedEffect(Unit) {
        viewModel.getMoviesByGenre(genreId = genre?.id.toString())
    }

    BaseScaffold(title = genre?.name ?: "MovieDB", navController = navController) { innerPadding ->
        LazyVerticalGrid(
            contentPadding = innerPadding,
            columns = GridCells.Fixed(count = 2),
        ) {
            items(movies.size) { idx ->
                val movie = movies[idx]
                if(idx >= movies.size - 1) {
                    LaunchedEffect(Unit) {
                        viewModel.getMoviesByGenre(genreId = genre?.id.toString())
                    }
                }
                Card(
                    Modifier
                        .height(270.dp)
                        .padding(all = 4.dp)
                        .clickable {
                            movieSharedViewModel.addMovie(movie)
                            navController.navigate(Routes.MovieDetailPage.route)
                        },
                    colors = CardDefaults.cardColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            Modifier.fillMaxSize()
                        ) {
                            AsyncImage(
                                "https://image.tmdb.org/t/p/original${movie.poster_path}",
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = "Movie Backdrop",
                                contentScale = ContentScale.FillBounds
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color.Transparent, Color.Black),
                                            0f,
                                            1000f,
                                        )
                                    )
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(all = 8.dp)
                                    .align(Alignment.BottomCenter),
                                text = movie.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }

        }
    }
}