package br.com.fiap.microjob.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.microjob.components.BottomNavDestination
import br.com.fiap.microjob.components.JobCard
import br.com.fiap.microjob.components.MicroJobBottomBar
import br.com.fiap.microjob.viewmodel.JobsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onHomeClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onProfileClick: () -> Unit,
    onJobClick: (String) -> Unit,
    jobsViewModel: JobsViewModel
) {
    val jobs by jobsViewModel.jobs.collectAsState()
    val favoriteIds by jobsViewModel.favoriteIds.collectAsState()
    val favoriteJobs = jobs.filter { it.id in favoriteIds }

    Scaffold(
        bottomBar = {
            MicroJobBottomBar(
                currentDestination = BottomNavDestination.FAVORITES,
                onDestinationClick = { dest ->
                    when (dest) {
                        BottomNavDestination.HOME -> onHomeClick()
                        BottomNavDestination.FAVORITES -> onFavoritesClick()
                        BottomNavDestination.PROFILE -> onProfileClick()
                    }
                }
            )
        }
    ) { innerPadding ->
        if (favoriteJobs.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhum favorito ainda.\nToque no coração em um job para adicionar.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoriteJobs, key = { it.id }) { job ->
                    JobCard(
                        job = job,
                        onClick = { onJobClick(job.id) },
                        isFavorite = true,
                        onFavoriteClick = { jobsViewModel.toggleFavorite(job.id) }
                    )
                }
            }
        }
    }
}
