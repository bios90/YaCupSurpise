package com.bios.yacupsurpise.screens.act_welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bios.yacupsurpise.R
import com.bios.yacupsurpise.ui.common.subviews.AppSpacer
import com.bios.yacupsurpise.ui.common.subviews.BaseProgress
import com.bios.yacupsurpise.ui.common.subviews.ButtonBlueSecondary
import com.bios.yacupsurpise.ui.common.subviews.ButtonOrange
import com.bios.yacupsurpise.ui.common.subviews.InputMy
import com.bios.yacupsurpise.ui.common.theme.AnimatedVisibilityApp
import com.bios.yacupsurpise.ui.common.theme.alignStart
import com.bios.yacupsurpise.ui.common.theme.navBarHeightCompose
import com.bios.yacupsurpise.ui.common.theme.secondaryTextColor
import com.bios.yacupsurpise.ui.common.utils.ScreenState
import com.test.yacupsurprise.ui.common.theme.AppTheme
import com.test.yacupsurprise.ui.common.theme.TextSize

@Composable
fun ActWelcomeCompose(
    state: ActWelcomeVm.State,
    onLoginClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    onDismissDialogs: () -> Unit,
    onLoginDialogConfirmed: (String, String) -> Unit,
    onRegisterDialogConfirmed: (String, String, String) -> Unit,
) {
    var showLoginDialog by remember {
        mutableStateOf(false)
    }
    showLoginDialog = state.showLoginDialog
    var showRegisterDialog by remember() {
        mutableStateOf(false)
    }
    showRegisterDialog = state.showRegisterDialog
    var screenState by remember {
        mutableStateOf(ScreenState.SUCCESS)
    }
    screenState = state.screenState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.color.white)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(10f)
            .padding(horizontal = AppTheme.dimens.x6)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(10f)
            ) {
                Image(
                    modifier = Modifier
                        .size(122.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.ic_cup),
                    contentDescription = "img_cup_logo"
                )
            }
            Text(
                style = AppTheme.typography.RegM.secondaryTextColor().alignStart(),
                text = "Yandex Cup 2022",
            )
            Text(
                style = AppTheme.typography.RegXxl.copy(
                    fontSize = TextSize.ExtraLarge.sp
                ),
                text = "Удивлятор",
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
                .padding(horizontal = AppTheme.dimens.x6)
        ) {
            Spacer(modifier = Modifier.weight(10f))
            ButtonOrange(
                modifier = Modifier.fillMaxWidth(),
                text = "Войти",
                onClick = onLoginClicked
            )
            AppSpacer(height = AppTheme.dimens.x2)
            ButtonBlueSecondary(
                modifier = Modifier.fillMaxWidth(),
                text = "Регистрация",
                onClick = onRegisterClicked
            )
            AppSpacer(height = AppTheme.dimens.x6)
            AppSpacer(height = navBarHeightCompose)
        }
    }

    if (showLoginDialog) {
        LoginDialog(
            onLoginDialogConfirmed = onLoginDialogConfirmed,
            onDismissRequest = onDismissDialogs,
            isProgress = screenState == ScreenState.LOADING,
        )
    }

    if (showRegisterDialog) {
        RegisterDialog(
            onRegisterDialogConfirmed = onRegisterDialogConfirmed,
            onDismissRequest = onDismissDialogs,
            isProgress = screenState == ScreenState.LOADING,
        )
    }

    AnimatedVisibilityApp(visible = screenState == ScreenState.LOADING) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.color.white)
        )
    }
}

@Composable
private fun LoginDialog(
    onLoginDialogConfirmed: (String, String) -> Unit,
    onDismissRequest: () -> Unit,
    isProgress: Boolean,
) {
    Dialog(
        onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(AppTheme.dimens.x4))
                .background(AppTheme.color.white)
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.x6)
        ) {
            var emailInputText by remember {
                mutableStateOf("")
            }
            var passwordInputText by remember {
                mutableStateOf("")
            }
            AppSpacer(height = AppTheme.dimens.x6)
            Text(
                style = AppTheme.typography.RegExtraLarge,
                text = "Войти"
            )
            AppSpacer(height = AppTheme.dimens.x2)
            InputMy(
                textAlign = TextAlign.Center,
                value = emailInputText,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                hint = "E-mail",
                onValueChange = {
                    emailInputText = it
                }
            )
            AppSpacer(height = AppTheme.dimens.x2)
            InputMy(
                textAlign = TextAlign.Center,
                visualTransformation = PasswordVisualTransformation(),
                value = passwordInputText,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                hint = "Пароль",
                onValueChange = {
                    passwordInputText = it
                }
            )
            AppSpacer(height = AppTheme.dimens.x4)
            ButtonOrange(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                text = "Вход",
                showProgress = isProgress,
                onClick = {
                    onLoginDialogConfirmed.invoke(emailInputText, passwordInputText)
                }
            )
            AppSpacer(height = AppTheme.dimens.x6)
        }
    }
}

@Composable
private fun RegisterDialog(
    onRegisterDialogConfirmed: (String, String, String) -> Unit,
    onDismissRequest: () -> Unit,
    isProgress: Boolean,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(AppTheme.dimens.x4))
                .background(AppTheme.color.white)
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.x6)
        ) {
            var nameInputText by remember {
                mutableStateOf("")
            }
            var emailInputText by remember {
                mutableStateOf("")
            }
            var passwordInputText by remember {
                mutableStateOf("")
            }
            AppSpacer(height = AppTheme.dimens.x6)
            Text(
                style = AppTheme.typography.RegExtraLarge,
                text = "Регистрация"
            )
            AppSpacer(height = AppTheme.dimens.x2)
            InputMy(
                textAlign = TextAlign.Center,
                value = nameInputText,
                hint = "Имя",
                onValueChange = {
                    nameInputText = it
                }
            )
            AppSpacer(height = AppTheme.dimens.x2)
            InputMy(
                textAlign = TextAlign.Center,
                value = emailInputText,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                hint = "E-mail",
                onValueChange = {
                    emailInputText = it
                }
            )
            AppSpacer(height = AppTheme.dimens.x2)
            InputMy(
                textAlign = TextAlign.Center,
                visualTransformation = PasswordVisualTransformation(),
                value = passwordInputText,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                hint = "Пароль",
                onValueChange = {
                    passwordInputText = it
                }
            )
            AppSpacer(height = AppTheme.dimens.x4)
            ButtonOrange(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                text = "Вперед!",
                showProgress = isProgress,
                onClick = {
                    onRegisterDialogConfirmed.invoke(
                        nameInputText,
                        emailInputText,
                        passwordInputText
                    )
                }
            )
            AppSpacer(height = AppTheme.dimens.x6)
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ActWelcomeCompose(
        state = ActWelcomeVm.State(
            screenState = ScreenState.SUCCESS,
            showLoginDialog = false,
            showRegisterDialog = false
        ),
        onLoginDialogConfirmed = { _, _ -> },
        onRegisterDialogConfirmed = { _, _, _ -> },
        onRegisterClicked = { },
        onLoginClicked = { },
        onDismissDialogs = { },
    )
}