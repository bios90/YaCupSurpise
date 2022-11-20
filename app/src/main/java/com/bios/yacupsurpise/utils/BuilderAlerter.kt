package com.bios.yacupsurpise.utils

import android.app.Dialog
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.bios.yacupsurpise.R
import com.bios.yacupsurpise.base.getColorApp
import com.tapadoo.alerter.Alerter

class BuilderAlerter {
    companion object {

        fun getRedBuilder(text: String, title: String = ""): BuilderAlerter {
            return BuilderAlerter()
                .setColor(getColorApp(R.color.red))
                .setText(text)
                .setTitle(title)
        }

        fun getGreenBuilder(text: String, title: String = ""): BuilderAlerter {
            return BuilderAlerter()
                .setColor(getColorApp(R.color.green))
                .setText(text)
                .setTitle(title)
        }
    }

    private var title: String = ""
    private var text: String = ""
    private var color: Int = getColorApp(R.color.red)
    private var duration: Int = 4000

    fun setTitle(title: String) = apply({
        this.title = title
    })

    fun setText(text: String) = apply({
        this.text = text
    })

    fun setColor(color: Int) = apply({
        this.color = color
    })

    fun setDuration(duration: Int) = apply({
        this.duration = duration
    })

    fun show(act: AppCompatActivity) {
        val alerter = Alerter.Companion.create(act)
        show(alerter, act.window.decorView)
    }

    fun show(dialog: Dialog) {
        val alerter = Alerter.Companion.create(dialog)
        val decorView = dialog.window?.decorView ?: return
        show(alerter, decorView)
    }

    fun show(alerter: Alerter, decorView: View) {
        if (Alerter.isShowing) {
            return
        }

        val defaultVisibility = decorView.getSystemUiVisibility()
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv());

        alerter.setTitle(title)
            .setText(text)
            .setDuration(duration.toLong())
            .setBackgroundColorInt(color)
            .setOnClickListener({
                Alerter.hide()
                decorView.setSystemUiVisibility(defaultVisibility)
            })
            .setOnHideListener({
                decorView.setSystemUiVisibility(defaultVisibility)
            })
            .show()
    }
}