package com.bios.yacupsurpise.screens.act_main.subscreens.users

import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.view_models.BaseEffectsData
import com.bios.yacupsurpise.base.view_models.BaseEffectsData.*
import com.bios.yacupsurpise.base.view_models.BaseViewModel
import com.bios.yacupsurpise.data.models.ModelUser
import com.bios.yacupsurpise.di.SubScreenUsersInjector
import com.bios.yacupsurpise.screens.act_main.subscreens.users.SubScreenUsersVm.*
import com.bios.yacupsurpise.screens.act_main.subscreens.users.SubScreenUsersVm.Effect.*
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeVm
import com.bios.yacupsurpise.ui.common.utils.ScreenState
import com.bios.yacupsurpise.utils.BuilderAlerter

class SubScreenUsersVm : BaseViewModel<State, Effect>() {

    private val injector = SubScreenUsersInjector()
    private val networker by lazy { injector.provideBaseNetworker(this) }
    override val initialState = State(
        screenState = ScreenState.SUCCESS,
        users = emptyList()
    )

    data class State(
        val screenState: ScreenState,
        val users: List<ModelUser>,
    )

    sealed class Effect {
        data class BaseEffectWrapper(val data: BaseEffectsData) : Effect()
    }

    override fun onCreate(act: BaseActivity) {
        super.onCreate(act)
        reloadUsers()
    }

    private fun reloadUsers() {
        setStateResult(
            state = currentState.copy(
                screenState = ScreenState.LOADING
            )
        )
        networker.getUsers(
            actionError = {
                setStateResult(
                    state = currentState.copy(
                        screenState = ScreenState.SUCCESS
                    )
                )
            },
            actionSuccess = {
                setStateResult(
                    state = currentState.copy(
                        screenState = ScreenState.SUCCESS
                    )
                )
                setStateResult(
                    state = currentState.copy(
                        users = it
                    ),
                    effect = BaseEffectWrapper(
                        Alerter(
                            BuilderAlerter.getGreenBuilder("Got ${it.size} Users")
                        )
                    )
                )
            }
        )
    }
}