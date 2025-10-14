/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.makeitso.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.makeitso.common.ext.dropdownSelector

@ExperimentalMaterial3Api
@Composable
fun DangerousCardEditor(
  @StringRes title: Int,
  @DrawableRes icon: Int,
  content: String,
  modifier: Modifier = Modifier,
  onEditClick: () -> Unit
) {
  CardEditor(
    title,
    icon,
    content,
    onEditClick,
    highlightColor = MaterialTheme.colorScheme.primary,
    modifier = modifier
  )
}

@ExperimentalMaterial3Api
@Composable
fun RegularCardEditor(
  @StringRes title: Int,
  @DrawableRes icon: Int,
  content: String,
  modifier: Modifier = Modifier,
  onEditClick: () -> Unit
) {
  CardEditor(
    title,
    icon,
    content,
    onEditClick,
    highlightColor = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier = modifier
  )
}

@ExperimentalMaterial3Api
@Composable
private fun CardEditor(
  @StringRes title: Int,
  @DrawableRes icon: Int,
  content: String,
  onEditClick: () -> Unit,
  highlightColor: Color,
  modifier: Modifier
) {
  Card(
    modifier = modifier,
    onClick = onEditClick,
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(stringResource(title), color = highlightColor)
      }

      if (content.isNotBlank()) {
        Text(text = content, modifier = Modifier.padding(start = 16.dp))
      }

      Icon(
        painter = painterResource(icon),
        contentDescription = "Icon",
        tint = highlightColor,
        modifier = Modifier.padding(start = 16.dp)
      )
    }
  }
}

@ExperimentalMaterial3Api
@Composable
fun CardSelector(
  @StringRes label: Int,
  options: List<String>,
  selection: String,
  modifier: Modifier = Modifier,
  onNewValue: (String) -> Unit
) {
  Card(
    modifier = modifier,
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    DropdownSelector(label, options, selection, Modifier.dropdownSelector(), onNewValue)
  }
}
