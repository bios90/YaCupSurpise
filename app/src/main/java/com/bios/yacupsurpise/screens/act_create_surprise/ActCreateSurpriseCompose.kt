package com.bios.yacupsurpise.screens.act_create_surprise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bios.yacupsurpise.R
import com.bios.yacupsurpise.data.models.ModelUser
import com.bios.yacupsurpise.ui.common.subviews.AppSpacer
import com.bios.yacupsurpise.ui.common.subviews.AppSpacerHorizontal
import com.bios.yacupsurpise.ui.common.subviews.BaseProgress
import com.bios.yacupsurpise.ui.common.subviews.ButtonBlueDark
import com.bios.yacupsurpise.ui.common.subviews.ButtonBlueSecondary
import com.bios.yacupsurpise.ui.common.subviews.ButtonOrange
import com.bios.yacupsurpise.ui.common.subviews.FawButton
import com.bios.yacupsurpise.ui.common.subviews.MultilineInput
import com.bios.yacupsurpise.ui.common.theme.AnimatedVisibilityApp
import com.bios.yacupsurpise.ui.common.theme.alignStart
import com.bios.yacupsurpise.ui.common.theme.navBarHeightCompose
import com.bios.yacupsurpise.ui.common.theme.secondaryTextColor
import com.bios.yacupsurpise.ui.common.utils.ScreenState
import com.bios.yacupsurpise.utils.files.FileItem
import com.test.yacupsurprise.ui.common.theme.AppTheme

@Composable
fun ActCreateSurpriseCompose(
    state: ActCreateSurpriseVm.State,
    onDeleteFileClicked: () -> Unit,
    onAddFileClicked: () -> Unit,
    onInputTextChanged: (String) -> Unit,
    onCreateClicked: () -> Unit
) {
    var screenState by remember { mutableStateOf(ScreenState.SUCCESS) }
    screenState = state.screenState
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.color.white)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.dimens.x6)
        ) {
            AppSpacer(height = AppTheme.dimens.x6)
            Text(
                text = "Создание",
                style = AppTheme.typography.RegExtraLarge.alignStart()
            )
            AppSpacer(height = AppTheme.dimens.x2)
            Text(
                text = "Создаем сообщение для ${state.receiver.name}. Вы можете добавить текст и прикрепить изображение или видео",
                style = AppTheme.typography.RegM.secondaryTextColor().alignStart()
            )
            AppSpacer(height = AppTheme.dimens.x8)
            if (state.selectedImage != null || state.selectedVideo != null) {
                val icon = if (state.selectedImage != null) {
                    stringResource(id = R.string.faw_image)
                } else {
                    stringResource(id = R.string.faw_video)
                }
                ItemFile(
                    typeIcon = icon,
                    fileName = state.selectedImage?.originalFileName
                        ?: state.selectedVideo?.originalFileName
                        ?: "",
                    fileSize = state.selectedImage?.getSizeString()
                        ?: state.selectedVideo?.getSizeString()
                        ?: "",
                    onDeleteClick = onDeleteFileClicked
                )
            } else {
                ButtonBlueSecondary(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Добавить файл",
                    onClick = onAddFileClicked,
                )
            }
            AppSpacer(height = AppTheme.dimens.x4)
            MultilineInput(
                modifier = Modifier.fillMaxWidth(),
                value = state.inputText ?: "",
                onValueChange = { onInputTextChanged(it) }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            ButtonOrange(
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimens.x6, vertical = AppTheme.dimens.x2)
                    .fillMaxWidth(),
                text = "Создать",
                onClick = onCreateClicked
            )
            AppSpacer(height = navBarHeightCompose)
        }

        AnimatedVisibilityApp(visible = screenState == ScreenState.LOADING) {
            BaseProgress()
        }
    }
}

@Composable
private fun ItemFile(
    modifier: Modifier = Modifier,
    typeIcon: String,
    fileName: String,
    fileSize: String,
    onDeleteClick: () -> Unit,
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
            .padding(horizontal = AppTheme.dimens.x4, vertical = AppTheme.dimens.x3),
    ) {
        Text(
            modifier = Modifier
                .width(36.dp)
                .align(Alignment.CenterVertically),
            text = typeIcon,
            style = AppTheme.typography.FawRegM
        )
        AppSpacerHorizontal(width = AppTheme.dimens.x2)
        Column(modifier = Modifier.weight(10f)) {
            Text(
                style = AppTheme.typography.RegM,
                text = fileName
            )
            AppSpacer(height = AppTheme.dimens.x2)
            Text(
                style = AppTheme.typography.RegS.secondaryTextColor(),
                text = fileSize
            )
        }
        AppSpacerHorizontal(width = AppTheme.dimens.x2)
        FawButton(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.x3),
            text = stringResource(id = R.string.faw_times),
            textColor = colorResource(id = R.color.red),
            onClick = onDeleteClick
        )
    }
}

@Preview
@Composable
fun Preview() {
    ActCreateSurpriseCompose(
        state = ActCreateSurpriseVm.State(
            screenState = ScreenState.SUCCESS,
            receiver = ModelUser(
                name = "UserName"
            ),
            inputText = "Some inputed Text",
            selectedImage = FileItem(
                "DSfsdfsdf",
                originalFileName = "File name",
            ),
            selectedVideo = null
        ),
        onAddFileClicked = {},
        onDeleteFileClicked = {},
        onCreateClicked = {},
        onInputTextChanged = {}
    )
}
