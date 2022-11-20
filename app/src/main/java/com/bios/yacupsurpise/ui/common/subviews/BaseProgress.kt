package com.bios.yacupsurpise.ui.common.subviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bios.yacupsurpise.R
import com.bios.yacupsurpise.ui.common.theme.withAlpha
import com.test.yacupsurprise.ui.common.theme.AppTheme

@Composable
fun BaseProgress() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.color.black.withAlpha(0.7f))
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = AppTheme.color.white,
                modifier = Modifier.size(52.dp)
            )
            AppSpacer(height = AppTheme.dimens.x3)
            Text(
                style = AppTheme.typography.RegL.copy(color = AppTheme.color.white),
                text = stringResource(R.string.loading)
            )
        }
    }
}