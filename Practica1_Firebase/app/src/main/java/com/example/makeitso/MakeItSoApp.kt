package com.example.makeitso

import android.Manifest
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.makeitso.common.composable.PermissionDialog
import com.example.makeitso.common.composable.RationaleDialog
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.makeitso.screens.edit_task.EditTaskScreen
import com.example.makeitso.screens.login.LoginScreen
import com.example.makeitso.screens.settings.SettingsScreen
import com.example.makeitso.screens.sign_up.SignUpScreen
import com.example.makeitso.screens.splash.SplashScreen
import com.example.makeitso.screens.tasks.TasksScreen
import com.example.makeitso.theme.MakeItSoTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.CoroutineScope

@Composable
@ExperimentalMaterial3Api
fun MakeItSoApp() {
  MakeItSoTheme {
    // Handle notification permission on Android 13+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      RequestNotificationPermissionDialog()
    }

    val appState = rememberAppState()
    val snackbarHostState = remember { SnackbarHostState() }

    Surface(color = MaterialTheme.colorScheme.background) {
      Scaffold(
        snackbarHost = {
          SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(8.dp)
          )
        }
      ) { innerPadding ->
        NavHost(
          navController = appState.navController,
          startDestination = SPLASH_SCREEN,
          modifier = Modifier.padding(innerPadding)
        ) {
          makeItSoGraph(appState)
        }
      }
    }
  }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermissionDialog() {
  val permissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

  if (!permissionState.status.isGranted) {
    if (permissionState.status.shouldShowRationale) RationaleDialog()
    else PermissionDialog { permissionState.launchPermissionRequest() }
  }
}

@Composable
fun rememberAppState(
  navController: NavHostController = rememberNavController(),
  snackbarManager: SnackbarManager = SnackbarManager,
  resources: Resources = resources(),
  coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
  remember(navController, snackbarManager, resources, coroutineScope) {
    MakeItSoAppState(navController, snackbarManager, resources, coroutineScope)
  }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
  LocalConfiguration.current
  return LocalContext.current.resources
}

@ExperimentalMaterial3Api
fun NavGraphBuilder.makeItSoGraph(appState: MakeItSoAppState) {
  composable(SPLASH_SCREEN) {
    SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
  }

  composable(SETTINGS_SCREEN) {
    SettingsScreen(
      restartApp = { route -> appState.clearAndNavigate(route) },
      openScreen = { route -> appState.navigate(route) }
    )
  }

  composable(LOGIN_SCREEN) {
    LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
  }

  composable(SIGN_UP_SCREEN) {
    SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
  }

  composable(TASKS_SCREEN) { TasksScreen(openScreen = { route -> appState.navigate(route) }) }

  composable(
    route = "$EDIT_TASK_SCREEN$TASK_ID_ARG",
    arguments = listOf(navArgument(TASK_ID) {
      nullable = true
      defaultValue = null
    })
  ) {
    EditTaskScreen(
      popUpScreen = { appState.popUp() }
    )
  }
}
