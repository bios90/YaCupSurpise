package com.bios.yacupsurpise.screens.act_welcome

import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.toSet
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeVm.*
import com.bios.yacupsurpise.base.view_models.BaseEffectsData
import com.bios.yacupsurpise.base.view_models.BaseEffectsData.*
import com.bios.yacupsurpise.base.view_models.BaseViewModel
import com.bios.yacupsurpise.di.ActWelcomeInjector
import com.bios.yacupsurpise.local_data.LocalData
import com.bios.yacupsurpise.local_data.SharedPrefsManager
import com.bios.yacupsurpise.screens.act_main.ActMain
import com.bios.yacupsurpise.screens.act_welcome.ActWelcomeVm.Effect.*
import com.bios.yacupsurpise.ui.common.utils.ScreenState
import com.photogramma.logic.utils.getErrorAlerterOrMake
import com.photogramma.logic.utils.getErrorMessageOrMake
import com.photogramma.logic.utils.validate
import com.photogramma.logic.utils.validateEmail

class ActWelcomeVm : BaseViewModel<State, Effect>() {

    override val initialState = State(
        screenState = ScreenState.LOADING,
        showLoginDialog = false,
        showRegisterDialog = false
    )

    data class State(
        val screenState: ScreenState,
        val showLoginDialog: Boolean,
        val showRegisterDialog: Boolean,
    )

    sealed class Effect {
        data class BaseEffectWrapper(val data: BaseEffectsData) : Effect()
    }

    private val injector = ActWelcomeInjector()
    private val networker by lazy { injector.provideBaseNetworker(this) }

    override fun onCreate(act: BaseActivity) {
        super.onCreate(act)
        checkLogin()
    }

    private fun checkLogin() {
        val user = SharedPrefsManager.getLoggedUser()
        if (user != null) {
            setStateResult(
                state = currentState,
                effect = BaseEffectWrapper(
                    NavigateTo(
                        clazz = ActMain::class.java,
                        finishAllPrevious = true
                    )
                )
            )
        } else {
            setStateResult(
                state = currentState.copy(
                    screenState = ScreenState.SUCCESS
                ),
            )
        }
    }

    private fun makeLogin(email: String, password: String) {
        setStateResult(
            state = currentState.copy(
                screenState = ScreenState.LOADING
            )
        )
        networker.makeLogin(
            email = email,
            password = password,
            actionError = { errorMessage ->
                setStateResult(
                    state = currentState.copy(
                        screenState = ScreenState.SUCCESS
                    ),
                    effect = BaseEffectWrapper(Toast(errorMessage))
                )
            },
            actionSuccess = {
                setStateResult(
                    state = currentState.copy(
                        screenState = ScreenState.SUCCESS
                    )
                )
                it?.let { user ->
                    val ldUser = LocalData.LdLoggedUser(user)
                    SharedPrefsManager.saveLocalData(ldUser)
                    checkLogin()
                }
            }
        )
    }

    private fun makeRegister(name: String, email: String, password: String) {
        setStateResult(
            state = currentState.copy(
                screenState = ScreenState.LOADING
            )
        )
        networker.makeRegister(
            name = name,
            email = email,
            password = password,
            actionError = { errorMessage ->
                setStateResult(
                    state = currentState.copy(
                        screenState = ScreenState.SUCCESS
                    ),
                    effect = BaseEffectWrapper(Toast(errorMessage))
                )
            },
            actionSuccess = {
                setStateResult(
                    state = currentState.copy(
                        screenState = ScreenState.SUCCESS
                    )
                )
                it?.let { user ->
                    val ldUser = LocalData.LdLoggedUser(user)
                    SharedPrefsManager.saveLocalData(ldUser)
                    checkLogin()
                }
            }
        )
    }

    inner class Listener {
        fun onLoginClicked() {
            setStateResult(
                state = currentState.copy(
                    showLoginDialog = true,
                    showRegisterDialog = false
                )
            )
        }

        fun onRegisterClicked() {
            setStateResult(
                state = currentState.copy(
                    showLoginDialog = false,
                    showRegisterDialog = true
                )
            )
        }

        fun onDialogsDismissRequest() {
            setStateResult(
                state = currentState.copy(
                    showLoginDialog = false,
                    showRegisterDialog = false
                )
            )
        }

        fun onLoginConfirmed(email: String, password: String) {
            validate {
                validateEmail(email)
                validatePasswords(password)
                getErrorMessageOrMake(
                    onError = { errors ->
                        setStateResult(
                            state = currentState,
                            effects = BaseEffectWrapper(Toast(errors)).toSet()
                        )
                    },
                    onSuccess = {
                        makeLogin(
                            email = email,
                            password = password
                        )
                    }
                )
            }
        }

        fun onRegisterConfirmed(name: String, email: String, password: String) {
            validate {
                validateNickname(name)
                validateEmail(email)
                validatePasswords(password)
                getErrorMessageOrMake(
                    onError = { errors ->
                        setStateResult(
                            state = currentState.copy(
                                screenState = ScreenState.SUCCESS
                            ),
                            effects = BaseEffectWrapper(Toast(errors)).toSet()
                        )
                    },
                    onSuccess = {
                        makeRegister(
                            name = name,
                            email = email,
                            password = password
                        )
                    }
                )
            }
        }
    }
}