package br.com.fiap.microjob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.microjob.navigation.MicroJobNavGraph
import br.com.fiap.microjob.ui.theme.MicroJobTheme
import br.com.fiap.microjob.viewmodel.ChatViewModel
import br.com.fiap.microjob.viewmodel.JobsViewModel
import br.com.fiap.microjob.viewmodel.ProfileViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MicroJobTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val jobsViewModel: JobsViewModel = viewModel()
                    val chatViewModel: ChatViewModel = viewModel()
                    val profileViewModel: ProfileViewModel = viewModel()
                    MicroJobNavGraph(
                        jobsViewModel = jobsViewModel,
                        chatViewModel = chatViewModel,
                        profileViewModel = profileViewModel
                    )
                }
            }
        }
    }
}
