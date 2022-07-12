package ru.donkids.mobile.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun InputTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onValueCommit: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(colorScheme.primary),
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() }
) {
    val colors = TextFieldDefaults.outlinedTextFieldColors()
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    var hadFocus by remember { mutableStateOf(false) }
    val isImeVisible = WindowInsets.isImeVisible
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isImeVisible) {
        if (!isImeVisible && hadFocus) {
            focusManager.clearFocus()
        }
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.onFocusEvent {
            val hasFocus = it.hasFocus

            if (hadFocus && !hasFocus) {
                onValueCommit()
            }

            hadFocus = hasFocus
        },
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle.copy(
            color = textColor
        ),
        cursorBrush = cursorBrush,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        decorationBox = decorationBox
    )
}
