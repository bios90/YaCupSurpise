package com.bios.yacupsurpise.screens.act_main.subscreens.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import com.bios.yacupsurpise.data.models.ModelUser
import com.bios.yacupsurpise.ui.common.subviews.AppSpacer
import com.bios.yacupsurpise.ui.common.theme.debounceClickable
import com.test.yacupsurprise.ui.common.theme.AppTheme

@Composable
fun SubScreenUsersCompose() {
}

@Composable
private fun ItemUser(
    modifier: Modifier = Modifier,
    user: ModelUser,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(AppTheme.dimens.x2)
    Row(
        modifier = modifier
            .shadow(
                elevation = AppTheme.dimens.x2,
                shape = shape,
            )
            .clip(shape)
            .background(AppTheme.color.white)
            .fillMaxWidth()
            .debounceClickable(
                onClick = onClick
            )
            .padding(horizontal = AppTheme.dimens.x4, vertical = AppTheme.dimens.x3),
    ) {

        Column(modifier = Modifier.weight(10f)) {
            Text(
                style = AppTheme.typography.RegM,
                text = user.name ?: ""
            )
            AppSpacer(height = AppTheme.dimens.x2)
            Text(
                style = AppTheme.typography.RegS,
                text = user.email ?: ""
            )
        }

    }
}