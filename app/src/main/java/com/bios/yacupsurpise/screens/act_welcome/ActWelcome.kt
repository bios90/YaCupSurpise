package com.bios.yacupsurpise.screens.act_welcome

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.subscribeState
import com.bios.yacupsurpise.base.view_models.createViewModelFactory
import com.bios.yacupsurpise.ui.common.utils.ScreenWindowData

class ActWelcome : BaseActivity() {

    override val screenWindowData: ScreenWindowData
        get() = ScreenWindowData.fullScreenTrans(
            isLightStatusBarIcons = false,
            isLightNavBarIcons = false
        )

    private val vm: ActWelcomeVm by viewModels {
        createViewModelFactory<ActWelcomeVm>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        subscribeState(
            act = this,
            vm = vm,
            stateConsumer = ::consumeState,
            effectsConsumer = ::handleEffects
        )
    }

    private fun consumeState(state: ActWelcomeVm.State) {
        setContent {
            ActWelcomeCompose(
                state = state,
                onLoginClicked = vm.Listener()::onLoginClicked,
                onRegisterClicked = vm.Listener()::onRegisterClicked,
                onLoginDialogConfirmed = vm.Listener()::onLoginConfirmed,
                onRegisterDialogConfirmed = vm.Listener()::onRegisterConfirmed,
                onDismissDialogs = vm.Listener()::onDialogsDismissRequest
            )
        }
    }

    private fun handleEffects(effects: Set<ActWelcomeVm.Effect>) {
        for (eff in effects) {
            when (eff) {
                is ActWelcomeVm.Effect.BaseEffectWrapper -> handleBaseEffects(eff.data)
            }
        }
    }
}