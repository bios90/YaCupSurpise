package com.bios.yacupsurpise.ui.common.subviews

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.bios.yacupsurpise.base.App
import com.bios.yacupsurpise.ui.common.theme.withAlpha
import com.test.yacupsurprise.ui.common.theme.AppTheme
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    sheetShape: Shape = RoundedCornerShape(topStart = AppTheme.dimens.x4, topEnd = AppTheme.dimens.x4),
    sheetBackgroundColor: Color = AppTheme.color.white,
    scrimColor: Color = AppTheme.color.black.withAlpha(0.5f),
    initialState: ModalBottomSheetValue = ModalBottomSheetValue.Expanded,
    content: @Composable (ModalBottomSheetState, CoroutineScope) -> Unit = { _, _ -> },
    bottomContent: @Composable ColumnScope.() -> Unit,
) {
    val scope = rememberCoroutineScope()

    val modalBottomSheetState =
        rememberModalBottomSheetState(
            initialValue = initialState
        )

    LaunchedEffect(modalBottomSheetState.targetValue) {
        if (modalBottomSheetState.targetValue == ModalBottomSheetValue.Hidden) {
            onClose()
        }
    }

    ModalBottomSheetLayout(
        modifier = modifier,
        sheetContent = bottomContent,
        sheetState = modalBottomSheetState,
        sheetShape = sheetShape,
        sheetBackgroundColor = sheetBackgroundColor,
        scrimColor = scrimColor,
        content = { content(modalBottomSheetState, scope) }
    )
}
