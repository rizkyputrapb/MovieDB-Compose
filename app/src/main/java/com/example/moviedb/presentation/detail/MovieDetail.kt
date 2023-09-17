package com.example.moviedb.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviedb.presentation.application.BaseScaffold
import com.example.moviedb.presentation.application.YoutubeScreen
import com.example.moviedb.presentation.shared_viewmodel.DetailSharedViewModel

@Composable
fun MovieDetailPage(navController: NavController, sharedViewModel: DetailSharedViewModel) {
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
                        text = "Placeholder Title",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Placeholder Tagline",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
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