package com.gsrocks.locationmaps.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalFocusManager

fun Modifier.clearFocus() = composed {
    val focusManager = LocalFocusManager.current
    then(
        clickable(
            MutableInteractionSource(),
            indication = null,
            onClick = { focusManager.clearFocus(true) }
        )
    )
}
