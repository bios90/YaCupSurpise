package com.bios.yacupsurpise.screens.act_main.subscreens.users

import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.subscribeState
import com.bios.yacupsurpise.base.view_models.createViewModelFactory
import com.bios.yacupsurpise.screens.act_main.subscreens.IMainSubScreen
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeCompose
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeVm
import com.test.yacupsurprise.ui.common.theme.AppTheme

class SubScreenUsers(
    private val act: BaseActivity,
    override val rootView: ComposeView,
) : IMainSubScreen {
    private val vm: SubScreenUsersVm by act.viewModels {
        createViewModelFactory<SubScreenUsersVm>()
    }

    init {
        subscribeState(
            act = act,
            vm = vm,
            stateConsumer = ::consumeState,
            effectsConsumer = ::handleEffects
        )
    }

    private fun consumeState(state: SubScreenUsersVm.State) {
        rootView.setContent {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.color.white)) {

            }
        }
    }

    private fun handleEffects(effects: Set<SubScreenUsersVm.Effect>) {
        for (eff in effects) {
            when (eff) {
                is SubScreenUsersVm.Effect.BaseEffectWrapper -> act.handleBaseEffects(eff.data)
            }
        }
    }
}