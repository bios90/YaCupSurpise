package com.bios.yacupsurpise.di

import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.view_models.BaseViewModel
import com.bios.yacupsurpise.networking.BaseNetworker
import com.bios.yacupsurpise.screens.act_create_surprise.ActCreateSurprise
import com.bios.yacupsurpise.screens.act_create_surprise.ActCreateSurpriseVm
import com.bios.yacupsurpise.utils.MediaPickerManager
import com.bios.yacupsurpise.utils.PermissionsManager

class SubScreensInjector {
    private var mediaPickerManager: MediaPickerManager? = null
    private var baseNetworker: BaseNetworker? = null
    private var permissionsManager: PermissionsManager? = null

    fun provideMediaPickerManager(act: ActCreateSurprise) =
        mediaPickerManager ?: MediaPickerManager(act)
            .also { this.mediaPickerManager = it }

    fun provideBaseNetworker(vm: BaseViewModel<*,*>) =
        baseNetworker ?: BaseInjector.provideBaseNetworker(vm)
            .also { this.baseNetworker = it }

    fun providePermissionsManager(act: BaseActivity) = permissionsManager
        ?: PermissionsManager(act)
            .also { this.permissionsManager = it }
}
