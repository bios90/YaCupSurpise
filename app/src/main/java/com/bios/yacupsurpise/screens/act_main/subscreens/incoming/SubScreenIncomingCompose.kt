package com.bios.yacupsurpise.screens.act_main.subscreens.incoming

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bios.yacupsurpise.R
import com.bios.yacupsurpise.base.safe
import com.bios.yacupsurpise.data.models.ModelSurprise
import com.bios.yacupsurpise.ui.common.subviews.AppSpacer
import com.bios.yacupsurpise.ui.common.subviews.AppSpacerHorizontal
import com.bios.yacupsurpise.ui.common.subviews.AppSpacerItem
import com.bios.yacupsurpise.ui.common.subviews.ButtonBlueSecondary
import com.bios.yacupsurpise.ui.common.subviews.ButtonOrange
import com.bios.yacupsurpise.ui.common.subviews.GlideImageApp
import com.bios.yacupsurpise.ui.common.theme.alignStart
import com.bios.yacupsurpise.ui.common.theme.debounceClickable
import com.bios.yacupsurpise.ui.common.theme.secondaryTextColor
import com.bios.yacupsurpise.ui.common.theme.top
import com.bios.yacupsurpise.ui.common.utils.ScreenState
import com.test.yacupsurprise.ui.common.theme.AppTheme

@Composable
fun SubScreenIncomingCompose(
    surprises: List<ModelSurprise>,
    selectedSurprice: ModelSurprise?,
    onSurpriceClicked: (ModelSurprise) -> Unit,
    onDialogDismissRequest: () -> Unit,
    onSurpriseAcceptClicked: () -> Unit,
    onSurpriseRejectClicked: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppTheme.dimens.x6),
            content = {
                AppSpacerItem(height = AppTheme.dimens.x6)
                for (surprise in surprises) {
                    item(
                        key = surprise.id
                    ) {
                        ItemSurpriceIncomming(
                            modifier = Modifier.fillMaxWidth(),
                            surprise = surprise,
                            onClick = { onSurpriceClicked.invoke(surprise) }
                        )
                    }
                    AppSpacerItem(height = AppTheme.dimens.x2)
                }
            }
        )
        var selectedSurprise: ModelSurprise? by remember {
            mutableStateOf(null)
        }
        selectedSurprise = selectedSurprice
        selectedSurprise?.let {
            ReactionDialog(
                surprise = selectedSurprise!!,
                onDialogDismissRequest = onDialogDismissRequest,
                onAcceptClicked = onSurpriseAcceptClicked,
                onRejectClicked = onSurpriseRejectClicked
            )
        }
    }
}

//@Preview
//@Composable
//private fun Preview() {
//    SubScreenIncomingCompose(
//        state = SubScreenIncomingVm.State(
//            screenState = ScreenState.LOADING,
//            surprises = emptyList()
//        ),
//        onSurpriceClicked = {},
//        onSurpriseAcceptClicked = {},
//        onSurpriseRejectClicked = {}
//    )
//}

@Composable
private fun ItemSurpriceIncomming(
    surprise: ModelSurprise,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
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
            .debounceClickable(
                onClick = onClick
            )
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.x4, vertical = AppTheme.dimens.x3),
    ) {
        var iconColor by remember {
            mutableStateOf(AppTheme.color.orange)
        }

        var icon by remember {
            mutableStateOf(R.string.faw_sand_clock)
        }
        when {
            surprise.isRejected.safe() -> {
                iconColor = AppTheme.color.red
                icon = R.string.faw_times
            }
            surprise.reaction != null -> {
                iconColor = AppTheme.color.green
                icon = R.string.faw_check
            }
            else -> {
                iconColor = AppTheme.color.orange
                icon = R.string.faw_sand_clock
            }
        }
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = stringResource(id = icon),
            color = iconColor,
            style = AppTheme.typography.FawLightXxl
        )
        AppSpacerHorizontal(width = AppTheme.dimens.x3)
        Column(modifier = Modifier.weight(10f)) {
            Text(
                style = AppTheme.typography.RegM,
                maxLines = 1,
                text = "Автор: ${surprise.sender?.name}"
            )
            AppSpacer(height = AppTheme.dimens.x1)
            Text(
                maxLines = 1,
                style = AppTheme.typography.RegS.secondaryTextColor(),
                text = surprise.text ?: ""
            )
        }
        AppSpacerHorizontal(width = AppTheme.dimens.x3)
        surprise.imageUrl?.let { imageUrl ->
//            GlideImageApp(
//                modifier = Modifier
//                    .size(36.dp)
//                    .clip(RoundedCornerShape(AppTheme.dimens.x1)),
//                errorDrawableId = R.drawable.ic_avatar,
//                src = surprise.imageUrl
//            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ReactionDialog(
    surprise: ModelSurprise,
    onDialogDismissRequest: () -> Unit,
    onAcceptClicked: () -> Unit,
    onRejectClicked: () -> Unit
) {
    Dialog(
        onDismissRequest = onDialogDismissRequest,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(AppTheme.dimens.x6))
                    .background(AppTheme.color.white)
                    .padding(horizontal = AppTheme.dimens.x6)
            ) {
                AppSpacer(height = AppTheme.dimens.x6)
                Text(
                    style = AppTheme.typography.RegM.alignStart(),
                    text = "Автор: ${surprise.sender?.name} предлагает вам посмотреть его сообщение. Посморим или откажемся?"
                )
                Text(
                    style = AppTheme.typography.RegS.alignStart().secondaryTextColor(),
                    text = "P.S. Ваша рекция на сообщение будет записана на фронтальную камеру и отправлена ${surprise.sender?.name}"
                )
                AppSpacer(height = AppTheme.dimens.x4)
                ButtonOrange(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Смотрим!",
                    onClick = onAcceptClicked
                )
                AppSpacer(height = AppTheme.dimens.x2)
                ButtonBlueSecondary(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Отказаться",
                    onClick = onRejectClicked,
                )
                AppSpacer(height = AppTheme.dimens.x6)
            }
        }
    )
}
