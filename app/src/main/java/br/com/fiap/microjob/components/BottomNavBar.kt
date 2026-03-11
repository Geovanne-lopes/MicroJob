package br.com.fiap.microjob.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import br.com.fiap.microjob.ui.theme.SecondaryYellow

enum class BottomNavDestination(
    val title: String,
    val icon: ImageVector
) {
    HOME("Home", Icons.Default.Home),
    FAVORITES("Favoritos", Icons.Default.Favorite),
    PROFILE("Perfil", Icons.Default.Person)
}

@Composable
fun MicroJobBottomBar(
    currentDestination: BottomNavDestination,
    onDestinationClick: (BottomNavDestination) -> Unit
) {
    NavigationBar(
        containerColor = SecondaryYellow,
        contentColor = androidx.compose.ui.graphics.Color(0xFF1C1B1F)
    ) {
        BottomNavDestination.entries.forEach { destination ->
            val selected = currentDestination == destination
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(destination.title) },
                selected = selected,
                onClick = { onDestinationClick(destination) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = SecondaryYellow,
                    selectedIconColor = androidx.compose.ui.graphics.Color(0xFF1C1B1F),
                    selectedTextColor = androidx.compose.ui.graphics.Color(0xFF1C1B1F),
                    unselectedIconColor = androidx.compose.ui.graphics.Color(0xFF1C1B1F).copy(alpha = 0.7f),
                    unselectedTextColor = androidx.compose.ui.graphics.Color(0xFF1C1B1F).copy(alpha = 0.7f)
                )
            )
        }
    }
}
