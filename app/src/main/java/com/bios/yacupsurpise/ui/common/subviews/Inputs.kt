package com.bios.yacupsurpise.ui.common.subviews

import com.test.yacupsurprise.ui.common.theme.AppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MultilineInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String? = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textAlign: TextAlign = TextAlign.Start,
    bgColor: Color = AppTheme.color.gray1,
    strokeColor: Color = Color.Transparent,
    strokeWidth: Dp = 0.dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    contentPadding: PaddingValues = PaddingValues(horizontal = AppTheme.dimens.x4, vertical = AppTheme.dimens.x3),
) = BaseInput(
    modifier = modifier.defaultMinSize(minHeight = 100.dp),
    value = value,
    onValueChange = onValueChange,
    hint = hint,
    maxLines = 12,
    visualTransformation = visualTransformation,
    textAlign = textAlign,
    bgColor = bgColor,
    strokeColor = strokeColor,
    strokeWidth = strokeWidth,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    contentPadding = contentPadding
)

@Composable
fun InputMy(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String? = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textAlign: TextAlign = TextAlign.Start,
    bgColor: Color = AppTheme.color.gray1,
    strokeColor: Color = Color.Transparent,
    strokeWidth: Dp = 0.dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    contentPadding: PaddingValues = PaddingValues(horizontal = AppTheme.dimens.x4, vertical = AppTheme.dimens.x3),
) = BaseInput(
    modifier = modifier.defaultMinSize(minHeight = 22.dp),
    value = value,
    onValueChange = onValueChange,
    hint = hint,
    maxLines = 1,
    visualTransformation = visualTransformation,
    textAlign = textAlign,
    bgColor = bgColor,
    strokeColor = strokeColor,
    strokeWidth = strokeWidth,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    contentPadding = contentPadding
)


@Composable
fun BaseInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String?,
    maxLines: Int,
    visualTransformation: VisualTransformation,
    textAlign: TextAlign,
    strokeColor: Color,
    strokeWidth: Dp,
    bgColor: Color,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    contentPadding: PaddingValues,
) {
    TextFieldMy(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .border(width = strokeWidth, color = strokeColor, shape = RoundedCornerShape(6.dp)),
        textStyle = AppTheme.typography.RegM.copy(
            textAlign = textAlign
        ),
        visualTransformation = visualTransformation,
        value = value,
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = hint ?: "",
                style = AppTheme.typography.RegM.copy(
                    textAlign = textAlign,
                    color = AppTheme.color.gray2
                ),
            )
        },
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            textColor = AppTheme.color.black,
            cursorColor = AppTheme.color.orange,
            placeholderColor = AppTheme.color.gray2,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
        ),
        maxLines = maxLines,
        singleLine = maxLines == 1,
        keyboardOptions = keyboardOptions.copy(
            capitalization = KeyboardCapitalization.Sentences
        ),
        keyboardActions = keyboardActions,
        contentPadding = contentPadding
    )
}

@Composable
fun TextFieldMy(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape =
        MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    @OptIn(ExperimentalMaterialApi::class)
    (BasicTextField(
        value = value,
        modifier = modifier
            .background(colors.backgroundColor(enabled).value, shape)
            .indicatorLine(enabled, isError, interactionSource, colors)
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            ),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            // places leading icon, text field with label and placeholder, trailing icon
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = singleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = contentPadding
            )
        }
    ))
}
