package br.com.fiap.microjob.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.microjob.ui.screens.ChatScreen
import br.com.fiap.microjob.ui.screens.CreateJobScreen
import br.com.fiap.microjob.ui.screens.FavoritesScreen
import br.com.fiap.microjob.ui.screens.JobDetailsScreen
import br.com.fiap.microjob.ui.screens.JobFeedScreen
import br.com.fiap.microjob.ui.screens.LoginScreen
import br.com.fiap.microjob.ui.screens.ProfileScreen
import br.com.fiap.microjob.ui.screens.SignUpScreen
import br.com.fiap.microjob.ui.screens.WelcomeScreen
import br.com.fiap.microjob.viewmodel.ChatViewModel
import br.com.fiap.microjob.viewmodel.JobsViewModel
import br.com.fiap.microjob.viewmodel.ProfileViewModel

object Routes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val SIGN_UP = "sign_up"
    const val JOB_FEED = "job_feed"
    const val FAVORITES = "favorites"
    const val CREATE_JOB = "create_job"
    const val JOB_DETAILS = "job_details/{jobId}"
    const val CHAT = "chat/{jobId}"
    const val PROFILE = "profile"

    fun jobDetails(jobId: String) = "job_details/$jobId"
    fun chat(jobId: String) = "chat/$jobId"
}

@Composable
fun MicroJobNavGraph(
    navController: NavHostController = rememberNavController(),
    jobsViewModel: JobsViewModel,
    chatViewModel: ChatViewModel,
    profileViewModel: ProfileViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {
        composable(Routes.WELCOME) {
            WelcomeScreen(
                onLogInClick = { navController.navigate(Routes.LOGIN) },
                onSignUpClick = { navController.navigate(Routes.SIGN_UP) }
            )
        }
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.JOB_FEED) {
                        popUpTo(Routes.WELCOME) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.SIGN_UP) {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(Routes.JOB_FEED) {
                        popUpTo(Routes.WELCOME) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.JOB_FEED) {
            JobFeedScreen(
                onJobClick = { jobId -> navController.navigate(Routes.jobDetails(jobId)) },
                onFabClick = { navController.navigate(Routes.CREATE_JOB) },
                onProfileClick = { navController.navigate(Routes.PROFILE) },
                onFavoritesClick = { navController.navigate(Routes.FAVORITES) },
                onHomeClick = { /* já no feed */ },
                jobsViewModel = jobsViewModel
            )
        }
        composable(Routes.FAVORITES) {
            FavoritesScreen(
                onHomeClick = { navController.popBackStack(Routes.JOB_FEED, inclusive = false) },
                onFavoritesClick = { },
                onProfileClick = { navController.navigate(Routes.PROFILE) }
            )
        }
        composable(Routes.CREATE_JOB) {
            CreateJobScreen(
                onJobCreated = { navController.popBackStack() },
                onBack = { navController.popBackStack() },
                jobsViewModel = jobsViewModel
            )
        }
        composable(Routes.JOB_DETAILS) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId") ?: return@composable
            JobDetailsScreen(
                jobId = jobId,
                onContactClick = { navController.navigate(Routes.chat(jobId)) },
                onBack = { navController.popBackStack() },
                onHomeClick = { navController.navigate(Routes.JOB_FEED) { popUpTo(Routes.JOB_FEED) { inclusive = true } } },
                onFavoritesClick = { navController.navigate(Routes.FAVORITES) },
                onProfileClick = { navController.navigate(Routes.PROFILE) },
                jobsViewModel = jobsViewModel
            )
        }
        composable(Routes.CHAT) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId") ?: return@composable
            ChatScreen(
                jobId = jobId,
                onBack = { navController.popBackStack() },
                jobsViewModel = jobsViewModel,
                chatViewModel = chatViewModel
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onHomeClick = { navController.popBackStack(Routes.JOB_FEED, inclusive = false) },
                onFavoritesClick = { navController.navigate(Routes.FAVORITES) },
                onProfileClick = { },
                profileViewModel = profileViewModel
            )
        }
    }
}
