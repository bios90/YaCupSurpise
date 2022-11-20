package com.bios.yacupsurpise.screens.act_main

import com.bios.yacupsurpise.base.view_models.BaseEffectsData
import com.bios.yacupsurpise.base.view_models.BaseViewModel
import com.bios.yacupsurpise.data.enums.TypeTab
import com.bios.yacupsurpise.screens.act_main.ActMainVm.*
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeVm

class ActMainVm : BaseViewModel<State, Effect>() {

    override val initialState = State(
        selectedTab = TypeTab.USERS
    )

    data class State(
        val selectedTab: TypeTab,
    )

    sealed class Effect {
        data class BaseEffectWrapper(val data: BaseEffectsData) : Effect()
    }

    private fun changeTabIfNeeded(tab: TypeTab) {
        if (tab != currentState.selectedTab) {
            setStateResult(
                state = currentState.copy(
                    selectedTab = tab
                )
            )
        }
    }

    inner class Listener {
        fun onTabClicked(tab: TypeTab) = changeTabIfNeeded(tab)

        fun onTabScrolled(tab: TypeTab) = changeTabIfNeeded(tab)
    }
}
