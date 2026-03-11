package br.com.fiap.microjob.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.microjob.components.BottomNavDestination
import br.com.fiap.microjob.components.MicroJobBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onHomeClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onProfileClick: () -> Unit
) {
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Nenhum favorito ainda.\nToque em um job para adicionar.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
