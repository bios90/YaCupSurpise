package com.bios.yacupsurpise.screens.act_main.subscreens.sended

import androidx.activity.viewModels
import androidx.compose.ui.platform.ComposeView
import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.subscribeState
import com.bios.yacupsurpise.base.view_models.createViewModelFactory
import com.bios.yacupsurpise.screens.act_main.subscreens.IMainSubScreen
import com.bios.yacupsurpise.screens.act_main.subscreens.incoming.SubScreenIncomingCompose

class SubScreenSended(
    private val act: BaseActivity,
    override val rootView: ComposeView
) : IMainSubScreen {

    private val vm: SubScreenSendedVm by act.viewModels {
        createViewModelFactory<SubScreenSendedVm>()
    }

    init {
        subscribeState(
            act = act,
            vm = vm,
            stateConsumer = ::consumeState,
            effectsConsumer = ::handleEffects
        )
    }

    private fun consumeState(state: SubScreenSendedVm.State) {
        rootView.setContent {
            rootView.setContent {
                SubScreenIncomingCompose(
                    surprises = state.surprises,
                    selectedSurprice = null,
                    onSurpriceClicked = vm.Listener()::onSurpriceClicked,
                    onDialogDismissRequest = { },
                    onSurpriseRejectClicked = {},
                    onSurpriseAcceptClicked = {}
                )
            }
        }
    }

    private fun handleEffects(effects: Set<SubScreenSendedVm.Effect>) {
        for (eff in effects) {
            when (eff) {
                is SubScreenSendedVm.Effect.BaseEffectWrapper -> act.handleBaseEffects(eff.data)
            }
        }
    }
}
