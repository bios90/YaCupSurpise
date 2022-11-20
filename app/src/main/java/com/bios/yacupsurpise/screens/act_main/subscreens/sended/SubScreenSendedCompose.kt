package com.bios.yacupsurpise.screens.act_main.subscreens.sended

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bios.yacupsurpise.ui.common.utils.ScreenState
import com.test.yacupsurprise.ui.common.theme.AppTheme

@Composable
fun SubScreenSendedCompose(
    state: SubScreenSendedVm.State
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.color.green)
    )
}
