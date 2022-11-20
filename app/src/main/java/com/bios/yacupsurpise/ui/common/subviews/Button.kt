package com.bios.yacupsurpise.ui.common.subviews

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bios.yacupsurpise.ui.common.theme.debounced
import com.bios.yacupsurpise.ui.common.theme.dpTextUnit
import com.bios.yacupsurpise.ui.common.theme.toDpLocalDensity
import com.bios.yacupsurpise.ui.common.theme.withAlpha
import com.bios.yacupsurpise.ui.common.utils.RippleTheme
import com.test.yacupsurprise.ui.common.theme.AppTheme

@Composable
fun ButtonOrange(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = AppTheme.typography.BoldM,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    corners: CornerBasedShape = RoundedCornerShape(AppTheme.dimens.x2),
    elevation: ButtonElevation? = null,
    minWidth: Dp = ButtonDefaults.MinWidth,
    minHeight: Dp = ButtonDefaults.MinHeight,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = AppTheme.dimens.x4,
        vertical = AppTheme.dimens.x3
    ),
    imageStart: @Composable RowScope.() -> Unit = {},
    textColor: Color = AppTheme.color.white,
    showProgress: Boolean = false,
) {
    BaseButton(
        text = text,
        textStyle = textStyle,
        modifier = modifier
            .clip(corners),
        onClick = onClick,
        colorBg = AppTheme.color.orange,
        colorRipple = AppTheme.color.black,
        isEnabled = isEnabled,
        elevation = elevation,
        minWidth = minWidth,
        minHeight = minHeight,
        contentPadding = contentPadding,
        imageStart = imageStart,
        textColor = textColor,
        showProgress = showProgress
    )
}

@Composable
fun ButtonBlueSecondary(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = AppTheme.typography.BoldM,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    corners: CornerBasedShape = RoundedCornerShape(AppTheme.dimens.x2),
    elevation: ButtonElevation? = null,
    minWidth: Dp = ButtonDefaults.MinWidth,
    minHeight: Dp = ButtonDefaults.MinHeight,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = AppTheme.dimens.x4,
        vertical = AppTheme.dimens.x3
    ),
    imageStart: @Composable RowScope.() -> Unit = {},
    textColor: Color = AppTheme.color.white,
    showProgress: Boolean = false,
) {
    BaseButton(
        text = text,
        textStyle = textStyle,
        modifier = modifier
            .clip(corners),
        onClick = onClick,
        colorBg = AppTheme.color.blueSecondary,
        colorRipple = AppTheme.color.black,
        isEnabled = isEnabled,
        elevation = elevation,
        minWidth = minWidth,
        minHeight = minHeight,
        contentPadding = contentPadding,
        imageStart = imageStart,
        textColor = textColor,
        showProgress = showProgress
    )
}

@Composable
fun ButtonBlueDark(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = AppTheme.typography.BoldM,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    corners: CornerBasedShape = RoundedCornerShape(AppTheme.dimens.x2),
    elevation: ButtonElevation? = null,
    minWidth: Dp = ButtonDefaults.MinWidth,
    minHeight: Dp = ButtonDefaults.MinHeight,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = AppTheme.dimens.x4,
        vertical = AppTheme.dimens.x3
    ),
    imageStart: @Composable RowScope.() -> Unit = {},
    textColor: Color = AppTheme.color.white,
    showProgress: Boolean = false,
) {
    BaseButton(
        text = text,
        textStyle = textStyle,
        modifier = modifier
            .clip(corners),
        onClick = onClick,
        colorBg = AppTheme.color.blueDark,
        colorRipple = AppTheme.color.black,
        isEnabled = isEnabled,
        elevation = elevation,
        minWidth = minWidth,
        minHeight = minHeight,
        contentPadding = contentPadding,
        imageStart = imageStart,
        textColor = textColor,
        showProgress = showProgress
    )
}

@Composable
fun ButtonGray(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = AppTheme.typography.BoldM,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    corners: CornerBasedShape = RoundedCornerShape(AppTheme.dimens.x2),
    elevation: ButtonElevation? = null,
    minWidth: Dp = ButtonDefaults.MinWidth,
    minHeight: Dp = ButtonDefaults.MinHeight,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = AppTheme.dimens.x4,
        vertical = AppTheme.dimens.x3
    ),
    imageStart: @Composable RowScope.() -> Unit = {},
    textColor: Color = AppTheme.color.black,
    showProgress: Boolean = false,
) {
    BaseButton(
        text = text,
        textStyle = textStyle,
        modifier = modifier
            .clip(corners),
        onClick = onClick,
        colorBg = AppTheme.color.gray1,
        colorRipple = AppTheme.color.black,
        isEnabled = isEnabled,
        elevation = elevation,
        minWidth = minWidth,
        minHeight = minHeight,
        contentPadding = contentPadding,
        imageStart = imageStart,
        textColor = textColor,
        showProgress = showProgress
    )
}

@Composable
fun ButtonGreen(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = AppTheme.typography.BoldM,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    corners: CornerBasedShape = RoundedCornerShape(AppTheme.dimens.x2),
    elevation: ButtonElevation? = null,
    minWidth: Dp = ButtonDefaults.MinWidth,
    minHeight: Dp = ButtonDefaults.MinHeight,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = AppTheme.dimens.x4,
        vertical = AppTheme.dimens.x3
    ),
    imageStart: @Composable RowScope.() -> Unit = {},
    textColor: Color = AppTheme.color.white,
    showProgress: Boolean = false,
) {
    BaseButton(
        text = text,
        textStyle = textStyle,
        modifier = modifier
            .clip(corners),
        onClick = onClick,
        colorBg = AppTheme.color.green,
        colorRipple = AppTheme.color.black.copy(alpha = 0.4f),
        isEnabled = isEnabled,
        elevation = elevation,
        minWidth = minWidth,
        minHeight = minHeight,
        contentPadding = contentPadding,
        imageStart = imageStart,
        textColor = textColor,
        showProgress = showProgress
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    minWidth: Dp,
    minHeight: Dp,
    content: @Composable RowScope.() -> Unit,
) {
    val contentColor by colors.contentColor(enabled)
    Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        color = colors.backgroundColor(enabled).value,
        contentColor = contentColor.copy(alpha = 1f),
        border = border,
        elevation = elevation?.elevation(enabled, interactionSource)?.value ?: 0.dp,
        interactionSource = interactionSource,
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(
                value = AppTheme.typography.RegL
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = minWidth,
                            minHeight = minHeight
                        )
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    text: Any,
    textStyle: TextStyle,
    onClick: () -> Unit,
    colorBg: Color,
    colorBgDisabled: Color = colorBg,
    colorRipple: Color,
    elevation: ButtonElevation?,
    isEnabled: Boolean = true,
    contentPadding: PaddingValues,
    minWidth: Dp,
    minHeight: Dp,
    imageStart: @Composable RowScope.() -> Unit,
    textColor: Color,
    content: (@Composable RowScope.() -> Unit)? = null,
    disabledAlpha: Float = 0.4f,
    showProgress: Boolean,
) {
    CompositionLocalProvider(
        LocalRippleTheme provides RippleTheme(colorRipple),
        LocalMinimumTouchTargetEnforcement provides false
    ) {
        val clickable = debounced(debounceTime = 500, onClick = onClick)
        Button(
            onClick = { clickable.invoke() },
            modifier = modifier
                .defaultMinSize(
                    minWidth = minWidth,
                    minHeight = minHeight
                )
                .alpha(if (isEnabled) 1f else disabledAlpha),
            enabled = isEnabled,
            elevation = elevation,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorBg,
                disabledBackgroundColor = colorBgDisabled
            ),
            minWidth = minWidth,
            minHeight = minHeight,
            contentPadding = contentPadding,
        ) {
            if (content != null) {
                content.invoke(this)
            } else if (showProgress) {
                CircularProgressIndicator(
                    color = textColor,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterVertically)
                )
            } else {
                imageStart()
                val textAsAnnotatedString = text as? AnnotatedString
                val textAsString = text as? String
                if (textAsAnnotatedString != null) {
                    Text(
                        text = textAsAnnotatedString,
                        style = textStyle,
                        color = textColor
                    )
                } else {
                    Text(
                        text = textAsString ?: "",
                        style = textStyle,
                        color = textColor
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colorRipple: Color = AppTheme.color.black.copy(alpha = 0.4f),
    elevation: ButtonElevation? = null,
    corners: CornerBasedShape = RoundedCornerShape(8.dp),
    isEnabled: Boolean = true,
    minWidth: Dp = ButtonDefaults.MinWidth,
    minHeight: Dp = ButtonDefaults.MinHeight,
    disabledAlpha: Float = 0.5f,
    content: @Composable BoxScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalRippleTheme provides RippleTheme(colorRipple),
        LocalMinimumTouchTargetEnforcement provides false,
    ) {
        val clickable = debounced(debounceTime = 500, onClick = onClick)
        Button(
            onClick = clickable,
            modifier = modifier
                .defaultMinSize(
                    minWidth = minWidth,
                    minHeight = minHeight
                )
                .alpha(if (isEnabled) 1f else disabledAlpha)
                .clip(corners),
            shape = corners,
            enabled = isEnabled,
            elevation = elevation,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent,
            ),
            minWidth = minWidth,
            minHeight = minHeight,
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(corners),
                contentAlignment = Alignment.Center,
            ) {
                content.invoke(this)
            }
        }
    }
}

@Composable
fun FawButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    textStyle: TextStyle = AppTheme.typography.FawBoldL,
    corners: CornerBasedShape = RoundedCornerShape(AppTheme.dimens.x2),
    contentPadding: PaddingValues =  PaddingValues(0.dp),
    onClick: () -> Unit,
) {
    BaseButton(
        text = text,
        textStyle = textStyle,
        modifier = modifier
            .clip(corners),
        onClick = onClick,
        colorBg = Color.Transparent,
        colorRipple = AppTheme.color.orange.withAlpha(0.5f),
        isEnabled = true,
        elevation = null,
        minWidth = 0.dp,
        minHeight = 0.dp,
        contentPadding = contentPadding,
        imageStart = { },
        textColor = textColor,
        showProgress = false
    )
}
