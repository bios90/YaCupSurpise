package com.bios.yacupsurpise.base.view_models

import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.utils.BuilderAlerter
import java.io.Serializable

sealed class BaseEffectsData {
    data class NavigateTo(
        val clazz: Class<out BaseActivity>,
        val args: Serializable? = null,
        val finishCurrent: Boolean = false,
        val finishAllPrevious: Boolean = false,
    ) : BaseEffectsData()

    data class Toast(val text: String) : BaseEffectsData()
    data class Alerter(val alerterBuilder: BuilderAlerter) : BaseEffectsData()
    object Finish : BaseEffectsData()
}
