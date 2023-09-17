package com.example.moviedb.presentation.homepage

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviedb.navigation.Routes
import com.example.moviedb.presentation.application.BaseScaffold
import com.example.moviedb.presentation.homepage.viewmodel.HomeViewModel
import com.example.moviedb.presentation.shared_viewmodel.HomeGenreViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun Homepage(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    homeGenreViewModel: HomeGenreViewModel
) {
    val popularMoviesState = viewModel.popularMovieState.value
    val movieGenresState = viewModel.genresState.value
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp


    BaseScaffold(title = "MovieDB", navController = navController) { innerPadding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                LazyRow(modifier = Modifier.fillMaxWidth(), userScrollEnabled = true) {
                    popularMoviesState.popularMovies.apply {
                        items(this) { movie ->
                            Card(
                                Modifier
                                    .width(screenWidth)
                                    .height(250.dp)
                                    .padding(all = 8.dp)
                            ) {
                                Box(Modifier.fillMaxSize()) {
                                    AsyncImage(
                                        "https://image.tmdb.org/t/p/original${movie.backdrop_path}",
                                        modifier = Modifier.fillParentMaxSize(),
                                        contentDescription = "Movie Backdrop",
                                        contentScale = ContentScale.FillHeight
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(
                                                Brush.verticalGradient(
                                                    listOf(Color.Transparent, Color.Black),
                                                    0f,
                                                    700f,
                                                )
                                            )
                                    )
                                    Column(
                                        Modifier
                                            .fillParentMaxWidth()
                                            .align(Alignment.BottomCenter)
                                            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            text = movie.title,
                                            fontSize = 28.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Start,
                                        )
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            text = movie.overview,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            lineHeight = 14.sp,
                                            textAlign = TextAlign.Start,
                                            maxLines = 3,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            item {
                Text(
                    "Genres",
                    modifier = Modifier.padding(all = 8.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            movieGenresState.genres.apply {
                items(this) { genre ->
                    Card(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(all = 4.dp)
                            .clickable {
                                homeGenreViewModel.addGenre(genre)
                                navController.navigate(Routes.GenreMoviesPage.route)
                            },
                        elevation = CardDefaults.cardElevation(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                    ) {
                        Text(
                            modifier = Modifier.padding(all = 4.dp),
                            text = genre.name ?: "",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}