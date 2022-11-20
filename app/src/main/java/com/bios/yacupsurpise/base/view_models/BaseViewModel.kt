package com.bios.yacupsurpise.base.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.toSet
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeVm

typealias StateResult <State, Effect> = Pair<State, Set<Effect>>

abstract class BaseViewModel<State, Effect> : ViewModel() {
    abstract val initialState: State
    protected val stateResultInner: StateResultLiveData<State, Effect> = StateResultLiveData()
    val stateResult: LiveData<StateResultEvent<State, Effect>> = stateResultInner
    val currentState: State
        get() = requireNotNull(stateResult.value?.peekData()?.first)


    open fun onCreate(act: BaseActivity) {
        if (stateResultInner.value == null) {
            stateResultInner.value = StateResultEvent(initialState to emptySet())
        }
    }

    fun setStateResult(state: State, effects: Set<Effect> = emptySet()) {
        stateResultInner.value = StateResultEvent(state to effects)
    }

    fun setStateResult(state: State, effect: Effect) {
        setStateResult(state, effect.toSet())
    }

    open fun onStart(act: BaseActivity) = Unit
    open fun onResume(act: BaseActivity) = Unit
    open fun onPause(act: BaseActivity) = Unit
    open fun onStop(act: BaseActivity) = Unit
    open fun onDestroy(act: BaseActivity) = Unit
}
