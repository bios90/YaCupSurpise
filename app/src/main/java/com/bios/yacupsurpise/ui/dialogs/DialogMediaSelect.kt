package com.bios.yacupsurpise.ui.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.bios.yacupsurpise.R
import com.bios.yacupsurpise.base.getColorApp
import com.bios.yacupsurpise.base.setNavigationBarColor
import com.bios.yacupsurpise.ui.common.subviews.AppSpacer
import com.bios.yacupsurpise.ui.common.subviews.ButtonBlueSecondary
import com.bios.yacupsurpise.ui.common.subviews.ButtonOrange
import com.bios.yacupsurpise.ui.common.theme.getComposeRootView
import com.bios.yacupsurpise.ui.common.theme.top
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.yacupsurprise.ui.common.theme.AppTheme

class DialogMediaSelect(
    private val onImageClicked: () -> Unit,
    private val onVideoClicked: () -> Unit,
) : BottomSheetDialogFragment() {
    override fun getTheme(): Int = R.style.Widget_AppTheme_BottomSheet_WhiteNavBar
    private val rootView by lazy {
        ComposeView(requireContext())
            .apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        dialog?.setNavigationBarColor(getColorApp(R.color.white))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            (view?.parent as ViewGroup).background =
                ColorDrawable(Color.TRANSPARENT)
        }
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = rootView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView.setContent {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(AppTheme.dimens.x6).top)
                    .background(AppTheme.color.white)
                    .padding(horizontal = AppTheme.dimens.x6)
            ) {
                AppSpacer(height = AppTheme.dimens.x8)
                ButtonOrange(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Изображение",
                    onClick = {
                        onImageClicked.invoke()
                        dismiss()
                    }
                )
                AppSpacer(height = AppTheme.dimens.x4)
                ButtonBlueSecondary(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Видео",
                    onClick = {
                        onVideoClicked.invoke()
                        dismiss()
                    }
                )
                AppSpacer(height = AppTheme.dimens.x8)
            }
        }
    }
}

