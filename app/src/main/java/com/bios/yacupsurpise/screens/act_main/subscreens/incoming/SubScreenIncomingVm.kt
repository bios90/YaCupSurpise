package com.bios.yacupsurpise.screens.act_main.subscreens.incoming

import androidx.lifecycle.viewModelScope
import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.safe
import com.bios.yacupsurpise.base.view_models.BaseEffectsData
import com.bios.yacupsurpise.base.view_models.BaseViewModel
import com.bios.yacupsurpise.data.MainBus
import com.bios.yacupsurpise.data.models.ModelReactionResult
import com.bios.yacupsurpise.data.models.ModelSurprise
import com.bios.yacupsurpise.di.SubScreensInjector
import com.bios.yacupsurpise.local_data.SharedPrefsManager
import com.bios.yacupsurpise.screens.act_main.subscreens.incoming.SubScreenIncomingVm.*
import com.bios.yacupsurpise.screens.act_reaction.ActReaction
import com.bios.yacupsurpise.screens.act_video_fullscreen.ActVideoFullscreen
import com.bios.yacupsurpise.ui.common.utils.ScreenState
import com.bios.yacupsurpise.utils.BuilderAlerter
import com.bios.yacupsurpise.utils.PermissionsManager
import com.bios.yacupsurpise.utils.files.FileItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class SubScreenIncomingVm : BaseViewModel<State, Effect>() {

    override val initialState = State(
        screenState = ScreenState.SUCCESS,
        surprises = emptyList(),
        selectedSurprice = null
    )

    data class State(
        val screenState: ScreenState,
        val surprises: List<ModelSurprise>,
        val selectedSurprice: ModelSurprise? = null
    )

    sealed class Effect {
        data class BaseEffectWrapper(val data: BaseEffectsData) : Effect()
    }

    private val injector = SubScreensInjector()
    private var permissionManager: PermissionsManager? = null

    init {
        setEvents()
    }

    override fun onCreate(act: BaseActivity) {
        super.onCreate(act)
        permissionManager = injector.providePermissionsManager(act)
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

        MainBus.flowReactionMade
            .onEach {
                withContext(Dispatchers.Main) {
                    sendSurpriseReaction(it)
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun sendSurpriseReaction(result: ModelReactionResult) {
        setStateResult(
            state = currentState.copy(
                screenState = ScreenState.LOADING
            )
        )

        injector.provideBaseNetworker(this)
            .sendReaction(
                surpriseId = requireNotNull(result.surprise.id),
                reaction = result.reaction,
                onError = {
                    setStateResult(
                        state = currentState.copy(
                            screenState = ScreenState.SUCCESS,
                        ),
                        effect = Effect.BaseEffectWrapper(
                            data = BaseEffectsData.Alerter(
                                alerterBuilder = BuilderAlerter.getRedBuilder("Упс, не удалось отправить реакцию. Можете посмотреть еще раз =)")
                            )
                        )
                    )
                },
                onSuccess = {
                    reloadSurprises()
                    setStateResult(
                        state = currentState.copy(
                            screenState = ScreenState.SUCCESS,
                        ),
                        effect = Effect.BaseEffectWrapper(
                            data = BaseEffectsData.Alerter(
                                alerterBuilder = BuilderAlerter.getGreenBuilder("Реакция отправлена, ${result.surprise.sender?.name} будет рад!")
                            )
                        )
                    )
                }
            )
    }

    private fun reloadSurprises() {
        val userId = SharedPrefsManager.getLoggedUser()?.id ?: return
        setStateResult(
            state = currentState.copy(
                screenState = ScreenState.LOADING
            )
        )
        injector.provideBaseNetworker(this).getReceivedSurprises(
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
            val message = if (surprise.isRejected.safe()) {
                "К сожалений такой шанс выпадает только раз в жизни, больше посмотреть нельзя("
            } else if (surprise.reaction != null) {
                "Первое впечатление самое важное, поэтому посмотреть второй раз нельзя)"
            } else {
                null
            }
            if (message != null) {
                setStateResult(
                    state = currentState.copy(
                        screenState = ScreenState.SUCCESS,
                    ),
                    effect = Effect.BaseEffectWrapper(
                        data = BaseEffectsData.Alerter(
                            alerterBuilder = BuilderAlerter.getRedBuilder(message)
                        )
                    )
                )
            } else {
                setStateResult(
                    state = currentState.copy(
                        selectedSurprice = surprise
                    )
                )
            }
        }

        fun onDialogDismissRequest() {
            setStateResult(
                state = currentState.copy(
                    selectedSurprice = null
                )
            )
        }

        fun onSurpriseAccepted() {
            val surprise = currentState.selectedSurprice ?: return
            permissionManager?.checkPermissions {
                if (permissionManager?.areAllPermissionsGranted().safe()) {
                    setStateResult(
                        state = currentState.copy(selectedSurprice = null),
                        effect = Effect.BaseEffectWrapper(
                            data = BaseEffectsData.NavigateTo(
                                clazz = ActReaction::class.java,
                                args = ActReaction.Args(surprise)
                            )
                        )
                    )
                }
            }
        }

        fun onSurpriseRejected() {
            val surprise = currentState.selectedSurprice ?: return
            setStateResult(
                state = currentState.copy(
                    selectedSurprice = null,
                    screenState = ScreenState.LOADING
                )
            )
            injector.provideBaseNetworker(this@SubScreenIncomingVm)
                .rejectSurprise(
                    surpriseId = requireNotNull(surprise.id),
                    onError = {
                        setStateResult(
                            state = currentState.copy(
                                screenState = ScreenState.SUCCESS,
                            ),
                            effect = Effect.BaseEffectWrapper(
                                data = BaseEffectsData.Alerter(
                                    alerterBuilder = BuilderAlerter.getRedBuilder("Упс, не удалось(")
                                )
                            )
                        )
                    },
                    onSuccess = {
                        reloadSurprises()
                        setStateResult(
                            state = currentState.copy(
                                screenState = ScreenState.SUCCESS,
                            ),
                            effect = Effect.BaseEffectWrapper(
                                data = BaseEffectsData.Alerter(
                                    alerterBuilder = BuilderAlerter.getGreenBuilder("Наверное ${surprise.sender?.name} расстроится(")
                                )
                            )
                        )
                    }
                )
        }
    }
}
