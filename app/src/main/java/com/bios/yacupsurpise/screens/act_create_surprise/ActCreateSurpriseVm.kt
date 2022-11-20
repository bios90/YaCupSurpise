package com.bios.yacupsurpise.screens.act_create_surprise

import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.safe
import com.bios.yacupsurpise.base.view_models.BaseEffectsData
import com.bios.yacupsurpise.base.view_models.BaseEffectsData.*
import com.bios.yacupsurpise.base.view_models.BaseViewModel
import com.bios.yacupsurpise.data.MainBus
import com.bios.yacupsurpise.data.models.ModelUser
import com.bios.yacupsurpise.di.ActCreateSurpriseInjector
import com.bios.yacupsurpise.local_data.SharedPrefsManager
import com.bios.yacupsurpise.screens.act_create_surprise.ActCreateSurpriseVm.*
import com.bios.yacupsurpise.screens.act_create_surprise.ActCreateSurpriseVm.Effect.*
import com.bios.yacupsurpise.ui.common.utils.ScreenState
import com.bios.yacupsurpise.utils.BuilderAlerter
import com.bios.yacupsurpise.utils.MediaPickerManager
import com.bios.yacupsurpise.utils.PermissionsManager
import com.bios.yacupsurpise.utils.files.FileItem
import com.photogramma.logic.utils.getErrorAlerterOrMake
import com.photogramma.logic.utils.validate

class ActCreateSurpriseVm(private val args: ActCreateSurprise.Args) : BaseViewModel<State, Effect>() {

    override val initialState: State = State(
        screenState = ScreenState.SUCCESS,
        receiver = args.receiver,
        inputText = null,
        selectedImage = null,
        selectedVideo = null
    )

    data class State(
        val screenState: ScreenState,
        val receiver: ModelUser,
        val inputText: String?,
        val selectedImage: FileItem?,
        val selectedVideo: FileItem?,
    )

    sealed class Effect {
        data class BaseEffectWrapper(val data: BaseEffectsData) : Effect()
    }

    private val injector = ActCreateSurpriseInjector()
    private var mediaPickerManager: MediaPickerManager? = null
    private var permissionsManager: PermissionsManager? = null

    override fun onCreate(act: BaseActivity) {
        super.onCreate(act)
        mediaPickerManager = injector.provideMediaPickerManager(act as ActCreateSurprise)
        permissionsManager = injector.providePermissionsManager(act)
    }

    private fun pickMedia() {
        mediaPickerManager?.pickMedia(
            onImagePicked = {
                setStateResult(
                    state = currentState.copy(
                        selectedImage = it,
                        selectedVideo = null
                    )
                )
            },
            onVideoPicked = {
                setStateResult(
                    state = currentState.copy(
                        selectedImage = null,
                        selectedVideo = it
                    )
                )
            },
            onError = {
                setStateResult(
                    state = currentState,
                    effect = BaseEffectWrapper(
                        data = Alerter(
                            alerterBuilder = BuilderAlerter.getRedBuilder("Ощибк апри загрузке файлов")
                        )
                    )
                )
            }
        )
    }

    private fun createSurprise() {
        val currentUserId = SharedPrefsManager.getLoggedUser()?.id ?: return
        val receiverId = args.receiver.id ?: return
        setStateResult(
            state = currentState.copy(screenState = ScreenState.LOADING),
        )
        injector.provideBaseNetworker(this)
            .createSurprise(
                senderId = currentUserId,
                receiverId = receiverId,
                text = currentState.inputText,
                image = currentState.selectedImage,
                video = currentState.selectedVideo,
                onError = { errorText ->
                    setStateResult(
                        state = currentState.copy(
                            screenState = ScreenState.SUCCESS
                        ),
                        effect = BaseEffectWrapper(
                            Alerter(
                                alerterBuilder = BuilderAlerter.getRedBuilder(errorText)
                            )
                        )
                    )
                },
                onSuccess = {
                    MainBus.flowSurpriseCreated.tryEmit(it)
                    setStateResult(
                        state = currentState.copy(
                            screenState = ScreenState.SUCCESS
                        ),
                        effect = BaseEffectWrapper(Finish)
                    )
                }
            )

    }

    inner class Listener {

        fun onDeleteFileClicked() {
            setStateResult(
                state = currentState.copy(
                    selectedImage = null,
                    selectedVideo = null,
                )
            )
        }

        fun onAddFileClicked() {
            permissionsManager?.checkPermissions {
                if (permissionsManager?.areAllPermissionsGranted().safe()) {
                    pickMedia()
                }
            }
        }

        fun onInputTextChanged(text: String) {
            setStateResult(
                state = currentState.copy(
                    inputText = text
                )
            )
        }

        fun onCreateClicked() {
            validate {
                val hasData = currentState.inputText != null
                        || currentState.selectedImage != null
                        || currentState.selectedVideo != null
                validateAnyBoolean(
                    condition = hasData,
                    fieldName = null,
                    errorMessage = "Пожалуйста напишите текст или добавьте изображение/видео"
                )
                getErrorAlerterOrMake(
                    onError = {
                        setStateResult(
                            state = currentState,
                            effect = BaseEffectWrapper(it)
                        )
                    },
                    onSuccess = ::createSurprise
                )
            }
        }
    }

}
