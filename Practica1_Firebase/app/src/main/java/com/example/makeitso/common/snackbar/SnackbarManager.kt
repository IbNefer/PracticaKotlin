package com.example.makeitso.common.snackbar

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object SnackbarManager {
  private val _snackbarFlow: MutableStateFlow<SnackbarMessage?> = MutableStateFlow(null)
  val snackbarFlow: StateFlow<SnackbarMessage?> = _snackbarFlow.asStateFlow()

  fun showMessage(message: SnackbarMessage) {
    _snackbarFlow.value = message
  }

  fun showMessage(message: String) {
    _snackbarFlow.value = SnackbarMessage.StringSnackbar(message)
  }

  fun showMessage(@androidx.annotation.StringRes resId: Int) {
    _snackbarFlow.value = SnackbarMessage.ResourceSnackbar(resId)
  }

  fun clearSnackbarState() {
    _snackbarFlow.value = null
  }
}
