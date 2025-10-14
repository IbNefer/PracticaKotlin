package com.example.makeitso.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextFieldColors


@Composable
@ExperimentalMaterial3Api
fun DropdownContextMenu(
  options: List<String>,
  modifier: Modifier = Modifier,
  onActionClick: (String) -> Unit
) {
  var isExpanded by remember { mutableStateOf(false) }

  ExposedDropdownMenuBox(
    expanded = isExpanded,
    onExpandedChange = { isExpanded = !isExpanded },
    modifier = modifier
  ) {
    Icon(
      imageVector = Icons.Default.MoreVert,
      contentDescription = "More",
      modifier = Modifier.padding(8.dp, 0.dp)
    )

    ExposedDropdownMenu(
      expanded = isExpanded,
      onDismissRequest = { isExpanded = false },
      modifier = Modifier.width(180.dp)
    ) {
      options.forEach { option ->
        DropdownMenuItem(
          text = { Text(option) },
          onClick = {
            onActionClick(option)
            isExpanded = false
          }
        )
      }
    }
  }
}

@Composable
@ExperimentalMaterial3Api
fun DropdownSelector(
  @StringRes label: Int,
  options: List<String>,
  selection: String,
  modifier: Modifier = Modifier,
  onNewValue: (String) -> Unit
) {
  var isExpanded by remember { mutableStateOf(false) }

  ExposedDropdownMenuBox(
    expanded = isExpanded,
    onExpandedChange = { isExpanded = !isExpanded },
    modifier = modifier
  ) {
    TextField(
      value = selection,
      onValueChange = {},
      readOnly = true,
      label = { Text(stringResource(label)) },
      trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(isExpanded) },
      colors = dropdownColors(),
      modifier = Modifier.fillMaxWidth()
    )

    ExposedDropdownMenu(
      expanded = isExpanded,
      onDismissRequest = { isExpanded = false }
    ) {
      options.forEach { option ->
        DropdownMenuItem(
          text = { Text(option) },
          onClick = {
            onNewValue(option)
            isExpanded = false
          }
        )
      }
    }
  }
}

@Composable
@ExperimentalMaterial3Api
private fun dropdownColors(): TextFieldColors {
  return ExposedDropdownMenuDefaults.textFieldColors(
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    focusedLabelColor = MaterialTheme.colorScheme.primary,
    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
    cursorColor = MaterialTheme.colorScheme.primary
  )
}

