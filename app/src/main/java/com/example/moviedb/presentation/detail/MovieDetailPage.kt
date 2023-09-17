package com.example.moviedb.presentation.detail

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
    val movieVideoState = viewModel.movieVideoState.value
    val movieReviewState = viewModel.movieReviewState.value
    val movie = sharedViewModel.movie

    LaunchedEffect(Unit) {
        viewModel.getMoviesByGenre(movie?.id.toString())
        viewModel.getMovieVideo(movie?.id.toString())
        viewModel.getMovieReviews(movie?.id.toString(), page = null)
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
            if (movieVideoState.video?.key != null) item {
                YoutubeScreen(
                    videoId = movieVideoState.video.key,
                    modifier = Modifier.fillParentMaxWidth()
                )
            }
            item {
                LazyRow() {
                    movieDetailState.movies?.genres.apply {
                        items(this ?: emptyList()) { genre ->
                            OutlinedCard(
                                Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                                colors = CardDefaults.cardColors(
                                    contentColor = MaterialTheme.colorScheme.primary,
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                )
                            ) {
                                Text(
                                    modifier = Modifier.padding(
                                        vertical = 4.dp,
                                        horizontal = 8.dp
                                    ),
                                    text = genre.name!!,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                )
                            }
                        }
                    }
                }
            }
            item {
                Column(
                    Modifier
                        .fillParentMaxWidth()
                        .padding(all = 8.dp)
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
                    Spacer(modifier = Modifier.height(8.dp))
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
            if (movieReviewState.reviews != null) movieReviewState.reviews.apply {
                items(this) { review ->
                    Card(
                        Modifier
                            .fillParentMaxWidth()
                            .padding(all = 8.dp), colors = CardDefaults.cardColors(
                            contentColor = MaterialTheme.colorScheme.primaryContainer,
                            containerColor = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        Column(Modifier.padding(8.dp)) {
                            Text(
                                text = review.author,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = review.author_details.username,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal
                            )
                            Divider(
                                Modifier
                                    .fillParentMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .height(1.5.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                            Text(text = review.content, fontSize = 14.sp, lineHeight = 14.sp)
                        }
                    }
                }
            }
        }
    }
}