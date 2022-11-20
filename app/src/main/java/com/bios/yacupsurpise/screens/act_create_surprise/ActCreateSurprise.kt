package com.bios.yacupsurpise.screens.act_create_surprise

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.getArgs
import com.bios.yacupsurpise.base.subscribeState
import com.bios.yacupsurpise.base.view_models.createViewModelFactory
import com.bios.yacupsurpise.data.models.ModelUser
import com.bios.yacupsurpise.ui.common.utils.ScreenWindowData
import java.io.Serializable

class ActCreateSurprise : BaseActivity() {
    data class Args(
        val receiver: ModelUser
    ) : Serializable

    override val screenWindowData: ScreenWindowData
        get() = ScreenWindowData.fullScreenTrans(
            isLightStatusBarIcons = false,
            isLightNavBarIcons = false
        )

    private val args: Args by lazy { requireNotNull(getArgs()) }

    private val vm: ActCreateSurpriseVm by viewModels {
        createViewModelFactory<ActCreateSurpriseVm, Args>(args)
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

    private fun consumeState(state: ActCreateSurpriseVm.State) {
        setContent {
            ActCreateSurpriseCompose(
                state = state,
                onDeleteFileClicked = vm.Listener()::onDeleteFileClicked,
                onAddFileClicked = vm.Listener()::onAddFileClicked,
                onInputTextChanged = vm.Listener()::onInputTextChanged,
                onCreateClicked = vm.Listener()::onCreateClicked
            )
        }
    }

    private fun handleEffects(effects: Set<ActCreateSurpriseVm.Effect>) {
        for (eff in effects) {
            when (eff) {
                is ActCreateSurpriseVm.Effect.BaseEffectWrapper -> handleBaseEffects(eff.data)
            }
        }
    }
}
