package com.bios.yacupsurpise.screens.act_reaction

import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.Consts
import com.bios.yacupsurpise.base.Toast
import com.bios.yacupsurpise.base.view_models.BaseEffectsData
import com.bios.yacupsurpise.base.view_models.BaseEffectsData.*
import com.bios.yacupsurpise.base.view_models.BaseViewModel
import com.bios.yacupsurpise.base.view_models.makeActionDelayed
import com.bios.yacupsurpise.data.MainBus
import com.bios.yacupsurpise.data.models.ModelReactionResult
import com.bios.yacupsurpise.data.models.ModelSurprise
import com.bios.yacupsurpise.di.ActReactionInjector
import com.bios.yacupsurpise.screens.act_main.subscreens.incoming.SubScreenIncomingVm
import com.bios.yacupsurpise.screens.act_reaction.ActReactionVm.*
import com.bios.yacupsurpise.screens.act_reaction.ActReactionVm.Effect.*
import com.bios.yacupsurpise.ui.common.utils.ScreenState
import com.bios.yacupsurpise.utils.CameraRecordManager

class ActReactionVm(private val args: ActReaction.Args) : BaseViewModel<State, Effect>() {

    data class State(
        val screenState: ScreenState,
        val surprise: ModelSurprise,
        val isRecordStarted: Boolean,
        val showProgress: Boolean,
        val isRecordMade: Boolean
    )

    override val initialState = State(
        surprise = args.surprise,
        isRecordStarted = false,
        showProgress = false,
        isRecordMade = false,
        screenState = ScreenState.SUCCESS
    )

    sealed class Effect {
        data class BaseEffectWrapper(val data: BaseEffectsData) : Effect()
    }

    private val injector = ActReactionInjector()
    private var recordManager: CameraRecordManager? = null
    override fun onCreate(act: BaseActivity) {
        super.onCreate(act)
        recordManager = injector.provideCameraRecordManager(act as ActReaction)
        if (currentState.surprise.hasImage().not() && currentState.surprise.hasVideo().not()) {
            startRecording()
        }
    }

    private fun startRecording() {
        setStateResult(
            state = currentState.copy(
                isRecordStarted = true,
                showProgress = true
            )
        )
        recordManager?.startRecording()
        makeActionDelayed(Consts.RECORD_DURATION) {
            val result = recordManager?.stop()

            setStateResult(
                state = currentState.copy(
                    screenState = ScreenState.LOADING
                )
            )
            if (result != null) {
                makeActionDelayed(10000, { //Duump crutch to wait video saved fully, I'm sorry, no time(
                    val resultData = ModelReactionResult(
                        surprise = args.surprise,
                        reaction = result
                    )
                    MainBus.flowReactionMade.tryEmit(resultData)
                    setStateResult(
                        state = currentState,
                        effect = BaseEffectWrapper(Finish)
                    )
                })
            }
        }
    }

    private fun handleMediaReady() {
        if (currentState.isRecordStarted) {
            return
        }
        startRecording()
    }

    inner class Listener {
        fun onVideoStarted() = handleMediaReady()

        fun onImageLoaded() = handleMediaReady()

        fun onBackPressed() {
            if (currentState.isRecordStarted.not() || currentState.isRecordMade) {
                setStateResult(
                    state = currentState,
                    effect = BaseEffectWrapper(Finish)
                )
            }
        }
    }


}
