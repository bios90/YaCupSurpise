package com.bios.yacupsurpise.base

import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AlertDialogLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.bios.yacupsurpise.base.view_models.BaseEffectsData
import com.bios.yacupsurpise.base.view_models.BaseViewModel
import com.bios.yacupsurpise.ui.common.theme.getComposeRootView
import com.bios.yacupsurpise.ui.common.utils.WindowWrapper
import com.test.yacupsurprise.ui.common.theme.AppTheme

abstract class BaseActivity : AppCompatActivity(), WindowWrapper {

    protected val rootView: ComposeView by lazy { getComposeRootView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(rootView)
    }

    protected fun setContent(content: @Composable () -> Unit) {
        rootView.setContent {
            AppTheme {
                ApplyScreenWindowData()
                content.invoke()
            }
        }
    }

    fun handleBaseEffects(data: BaseEffectsData) {
        when (data) {
            is BaseEffectsData.Toast -> Toast(data.text)
            is BaseEffectsData.Alerter -> data.alerterBuilder.show(this)
            is BaseEffectsData.NavigateTo -> {
                Intent(this, data.clazz)
                    .apply {
                        data.args?.let(::putArgs)
                        if (data.finishAllPrevious) {
                            addClearAllPreviousFlags()
                        }
                    }
                    .apply(::startActivity)
                if (data.finishCurrent && data.finishAllPrevious.not()) {
                    finish()
                }
            }
            is BaseEffectsData.Finish -> {
                if (isFinishing.not()) {
                    finish()
                }
            }
        }
    }
}