package io.github.mvettosi.plantreminder.presentation.shared.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.mvettosi.plantreminder.presentation.shared.R
import io.github.mvettosi.plantreminder.presentation.shared.theme.AppTheme

@Composable
fun BigButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    text: String
) {
  Button(
      onClick = onClick,
      modifier = modifier.height(48.dp).width(262.dp),
      enabled = enabled,
      shape = RoundedCornerShape(corner = CornerSize(10.dp)),
      colors =
          ButtonColors(
              containerColor = MaterialTheme.colorScheme.secondary,
              contentColor = MaterialTheme.colorScheme.onSecondary,
              disabledContainerColor = MaterialTheme.colorScheme.tertiary,
              disabledContentColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.5f),
          ),
      elevation = elevation,
      border = border,
      contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
      interactionSource = interactionSource,
      content = {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary)
      })
}

@Composable
fun SmallButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    @DrawableRes iconId: Int? = null,
    text: String
) {
  Button(
      onClick = onClick,
      modifier = modifier.height(36.dp).width(159.dp),
      enabled = enabled,
      shape = RoundedCornerShape(corner = CornerSize(10.dp)),
      colors =
          ButtonColors(
              containerColor = MaterialTheme.colorScheme.secondary,
              contentColor = MaterialTheme.colorScheme.onSecondary,
              disabledContainerColor = MaterialTheme.colorScheme.tertiary,
              disabledContentColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.5f),
          ),
      elevation = elevation,
      border = border,
      contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
      interactionSource = interactionSource,
      content = {
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
          iconId?.let {
            Icon(
                modifier = Modifier.padding(end = 8.dp),
                painter = painterResource(id = R.drawable.upload),
                contentDescription = "button icon")
          }
          Text(
              text = text,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSecondary)
        }
      })
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ButtonPreviews() {
  AppTheme {
    Column(
        modifier = Modifier.wrapContentSize().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
      BigButton(onClick = {}, text = "Add Your First Plant")
      BigButton(onClick = {}, enabled = false, text = "Add Your First Plant")
      SmallButton(onClick = {}, text = "Change Image", iconId = R.drawable.upload)
      SmallButton(onClick = {}, enabled = false, text = "Change Image", iconId = R.drawable.upload)
    }
  }
}
