package io.github.mvettosi.plantreminder.presentation.shared.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.mvettosi.plantreminder.presentation.shared.R
import io.github.mvettosi.plantreminder.presentation.shared.theme.Accent500
import io.github.mvettosi.plantreminder.presentation.shared.theme.AppTheme
import io.github.mvettosi.plantreminder.presentation.shared.theme.Neutralus100
import io.github.mvettosi.plantreminder.presentation.shared.theme.Neutralus300
import io.github.mvettosi.plantreminder.presentation.shared.theme.Neutralus500

@Composable
fun InternalTextField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    value: String = "",
    onValueChange: (String) -> Unit,
    isFocused: Boolean = false,
    onFocusChanged: (FocusState) -> Unit,
) {
  BasicTextField(
      modifier =
          modifier
              .background(
                  Neutralus100,
                  RoundedCornerShape(corner = CornerSize(10.dp)),
              )
              .border(
                  width = 1.dp,
                  color = if (isFocused) Accent500 else Neutralus100,
                  shape = RoundedCornerShape(corner = CornerSize(10.dp)))
              .fillMaxWidth()
              .padding(vertical = 10.dp, horizontal = 12.dp)
              .onFocusChanged(onFocusChanged),
      value = value,
      onValueChange = onValueChange,
      singleLine = true,
      cursorBrush = SolidColor(Neutralus500),
      textStyle = MaterialTheme.typography.bodySmall.copy(color = Neutralus500),
      decorationBox = { innerTextField ->
        Row(
            modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
          if (leadingIcon != null) leadingIcon()
          Box(Modifier.weight(1f)) {
            if (value.isEmpty())
                Text(
                    placeholderText,
                    style = MaterialTheme.typography.bodySmall,
                    color = Neutralus300)
            innerTextField()
          }
          if (trailingIcon != null) trailingIcon()
        }
      })
}

@Composable
fun InputText(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIcon: Int? = null,
    placeholderText: String = "Placeholder",
    value: String = "",
    forceFocus: Boolean = false,
) {
  var text by rememberSaveable { mutableStateOf(value) }
  var isFocused by remember { mutableStateOf(false) }
  InternalTextField(
      modifier = modifier,
      leadingIcon = {
        if (leadingIcon != null) {
          Icon(
              painter = painterResource(id = leadingIcon),
              contentDescription = "leading icon",
              tint = Neutralus300)
        }
      },
      trailingIcon = {
        if (text.isNotEmpty() && (!isFocused && !forceFocus)) {
          Icon(
              painter = painterResource(id = R.drawable.delete),
              contentDescription = "trailing icon",
              modifier = Modifier.clickable { text = "" },
              tint = Neutralus300)
        }
      },
      placeholderText = placeholderText,
      value = value,
      onValueChange = { text = it },
      isFocused = isFocused || forceFocus,
      onFocusChanged = { isFocused = it.isFocused })
}

@Composable
fun InputPopup(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIcon: Int? = null,
    placeholderText: String = "Placeholder",
    value: String = "",
    forceFocus: Boolean = false,
) {
  var text by rememberSaveable { mutableStateOf(value) }
  var isFocused by remember { mutableStateOf(false) }
  var expanded by remember { mutableStateOf(false) }
  InternalTextField(
      modifier = modifier.clickable { expanded = true },
      leadingIcon = {
        if (leadingIcon != null) {
          Icon(
              painter = painterResource(id = leadingIcon),
              contentDescription = "leading icon",
              tint = Neutralus300)
        }
      },
      trailingIcon = {
        Icon(
            painter = painterResource(id = R.drawable.chevron_down),
            contentDescription = "trailing icon",
            tint = Neutralus500)
      },
      placeholderText = placeholderText,
      value = value,
      onValueChange = { text = it },
      isFocused = isFocused || forceFocus,
      onFocusChanged = { isFocused = it.isFocused })

  if (expanded) {
    Dialog(onDismissRequest = { expanded = false }) {
      Card(
          modifier = Modifier.fillMaxWidth().height(200.dp).padding(16.dp),
          shape = RoundedCornerShape(16.dp),
      ) {
        Text(
            text = "This is a minimal dialog",
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
            textAlign = TextAlign.Center,
        )
      }
    }
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun InputTexts() {
  AppTheme {
    Column(
        modifier = Modifier.wrapContentSize().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
      InputText(
          modifier = Modifier.height(40.dp).width(350.dp),
          placeholderText = "Search",
          leadingIcon = R.drawable.search)
      InputText(
          modifier = Modifier.height(40.dp).width(350.dp),
          leadingIcon = R.drawable.search,
          value = "Mons|",
          forceFocus = true)
      InputText(
          modifier = Modifier.height(40.dp).width(350.dp),
          leadingIcon = R.drawable.search,
          value = "Monstera")
    }
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun InputPopups() {
  AppTheme {
    Column(
        modifier = Modifier.wrapContentSize().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
      InputPopup(modifier = Modifier.height(40.dp).width(350.dp), placeholderText = "Search")
      InputPopup(
          modifier = Modifier.height(40.dp).width(350.dp), value = "Mons|", forceFocus = true)
      InputPopup(modifier = Modifier.height(40.dp).width(350.dp), value = "Monstera")
    }
  }
}
