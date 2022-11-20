package com.bios.yacupsurpise.screens.act_main.subscreens.incoming

import androidx.activity.viewModels
import androidx.compose.ui.platform.ComposeView
import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.subscribeState
import com.bios.yacupsurpise.base.view_models.createViewModelFactory
import com.bios.yacupsurpise.screens.act_main.subscreens.IMainSubScreen

class SubScreenIncoming(
    private val act: BaseActivity,
    override val rootView: ComposeView
) : IMainSubScreen {

    private val vm: SubScreenIncomingVm by act.viewModels {
        createViewModelFactory<SubScreenIncomingVm>()
    }

    init {
        subscribeState(
            act = act,
            vm = vm,
            stateConsumer = ::consumeState,
            effectsConsumer = ::handleEffects
        )
    }

    private fun consumeState(state: SubScreenIncomingVm.State) {
        rootView.setContent {
            SubScreenIncomingCompose(
                surprises = state.surprises,
                selectedSurprice = state.selectedSurprice,
                onSurpriceClicked = vm.Listener()::onSurpriceClicked,
                onDialogDismissRequest = vm.Listener()::onDialogDismissRequest,
                onSurpriseRejectClicked = vm.Listener()::onSurpriseRejected,
                onSurpriseAcceptClicked = vm.Listener()::onSurpriseAccepted
            )
        }
    }

    private fun handleEffects(effects: Set<SubScreenIncomingVm.Effect>) {
        for (eff in effects) {
            when (eff) {
                is SubScreenIncomingVm.Effect.BaseEffectWrapper -> act.handleBaseEffects(eff.data)
            }
        }
    }
}
