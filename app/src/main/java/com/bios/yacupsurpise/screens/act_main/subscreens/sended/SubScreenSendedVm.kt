package com.bios.yacupsurpise.screens.act_main.subscreens.sended

import androidx.lifecycle.viewModelScope
import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.view_models.BaseEffectsData
import com.bios.yacupsurpise.base.view_models.BaseViewModel
import com.bios.yacupsurpise.data.MainBus
import com.bios.yacupsurpise.data.models.ModelSurprise
import com.bios.yacupsurpise.di.SubScreensInjector
import com.bios.yacupsurpise.local_data.SharedPrefsManager
import com.bios.yacupsurpise.screens.act_main.subscreens.incoming.SubScreenIncomingVm
import com.bios.yacupsurpise.screens.act_main.subscreens.sended.SubScreenSendedVm.*
import com.bios.yacupsurpise.screens.act_reaction.ActReaction
import com.bios.yacupsurpise.screens.act_video_fullscreen.ActVideoFullscreen
import com.bios.yacupsurpise.ui.common.utils.ScreenState
import com.bios.yacupsurpise.utils.BuilderAlerter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class SubScreenSendedVm : BaseViewModel<State, Effect>() {

    override val initialState = State(
        screenState = ScreenState.SUCCESS,
        surprises = emptyList()
    )

    data class State(
        val surprises: List<ModelSurprise>,
        val screenState: ScreenState
    )

    sealed class Effect {
        data class BaseEffectWrapper(val data: BaseEffectsData) : Effect()
    }

    private val injector = SubScreensInjector()
    init {
        setEvents()
    }

    override fun onCreate(act: BaseActivity) {
        super.onCreate(act)
        reloadSurprises()
    }

    private fun setEvents() {
        MainBus.flowSurpriseCreated
            .onEach {
                withContext(Dispatchers.Main) {
                    reloadSurprises()
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun reloadSurprises() {
        val userId = SharedPrefsManager.getLoggedUser()?.id ?: return
        setStateResult(
            state = currentState.copy(
                screenState = ScreenState.LOADING
            )
        )
        injector.provideBaseNetworker(this).getSendedSurprises(
            id = userId,
            onErrorAction = { errorMessage ->
                setStateResult(
                    state = currentState.copy(
                        screenState = ScreenState.SUCCESS,
                    ),
                    effect = Effect.BaseEffectWrapper(
                        data = BaseEffectsData.Alerter(
                            alerterBuilder = BuilderAlerter.getRedBuilder(errorMessage)
                        )
                    )
                )
            },
            onSuccessAction = {
                setStateResult(
                    state = currentState.copy(
                        screenState = ScreenState.SUCCESS,
                        surprises = it
                    )
                )
            }
        )
    }

    inner class Listener {
        fun onSurpriceClicked(surprise: ModelSurprise) {
            val reactionUrl = surprise.reaction?.url ?: return
            setStateResult(
                state = currentState,
                effect = Effect.BaseEffectWrapper(
                    data = BaseEffectsData.NavigateTo(
                        clazz = ActVideoFullscreen::class.java,
                        args = ActVideoFullscreen.Args(reactionUrl)
                    )
                )
            )
        }
    }
}
