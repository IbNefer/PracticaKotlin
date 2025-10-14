package com.example.makeitso

import android.content.res.Resources
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavHostController
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.makeitso.common.snackbar.SnackbarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MakeItSoAppState(
  val navController: NavHostController,
  val snackbarManager: SnackbarManager,
  val resources: Resources,
  val coroutineScope: CoroutineScope
) {
  val snackbarHostState = SnackbarHostState()

  init {
    coroutineScope.launch {
      snackbarManager.snackbarFlow.collect { message ->
        message?.let {
          val text = it.toMessage(resources)
          snackbarHostState.showSnackbar(text)
          snackbarManager.clearSnackbarState()
        }
      }
    }
  }

  fun navigate(route: String) {
    navController.navigate(route)
  }

  fun navigateAndPopUp(route: String, popUp: String) {
    navController.navigate(route) {
      popUpTo(popUp) { inclusive = true }
    }
  }

  fun clearAndNavigate(route: String) {
    navController.navigate(route) {
      popUpTo(0)
    }
  }

  fun popUp() {
    navController.popBackStack()
  }
}
