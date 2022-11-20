package com.test.yacupsurprise.ui.common.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bios.yacupsurpise.R
import com.bios.yacupsurpise.ui.common.theme.dpTextUnit

class Typography {
    val LightS = getFontWithSize(size = TextSize.S, fontWeight = FontWeight.Light)
    val LightM = getFontWithSize(size = TextSize.M, fontWeight = FontWeight.Light)
    val LightL = getFontWithSize(size = TextSize.L, fontWeight = FontWeight.Light)
    val LightXl = getFontWithSize(size = TextSize.Xl, fontWeight = FontWeight.Light)
    val LightXxl = getFontWithSize(size = TextSize.Xxl, fontWeight = FontWeight.Light)
    val LightExtraLarge = getFontWithSize(size = TextSize.ExtraLarge, fontWeight = FontWeight.Light)

    val RegS = getFontWithSize(size = TextSize.S)
    val RegM = getFontWithSize(size = TextSize.M)
    val RegL = getFontWithSize(size = TextSize.L)
    val RegXl = getFontWithSize(size = TextSize.Xl)
    val RegXxl = getFontWithSize(size = TextSize.Xxl)
    val RegExtraLarge = getFontWithSize(size = TextSize.ExtraLarge)

    val BoldS = getFontWithSize(size = TextSize.S, fontWeight = FontWeight.Bold)
    val BoldM = getFontWithSize(size = TextSize.M, fontWeight = FontWeight.Bold)
    val BoldL = getFontWithSize(size = TextSize.L, fontWeight = FontWeight.Bold)
    val BoldXl = getFontWithSize(size = TextSize.Xl, fontWeight = FontWeight.Bold)
    val BoldXxl = getFontWithSize(size = TextSize.Xxl, fontWeight = FontWeight.Bold)
    val BoldExtraLarge = getFontWithSize(size = TextSize.ExtraLarge, fontWeight = FontWeight.Bold)

    val FawLightS = getFontFawWithSize(size = TextSize.S, fontWeight = FontWeight.Light)
    val FawLightM = getFontFawWithSize(size = TextSize.M, fontWeight = FontWeight.Light)
    val FawLightL = getFontFawWithSize(size = TextSize.L, fontWeight = FontWeight.Light)
    val FawLightXl = getFontFawWithSize(size = TextSize.Xl, fontWeight = FontWeight.Light)
    val FawLightXxl = getFontFawWithSize(size = TextSize.Xxl, fontWeight = FontWeight.Light)
    val FawLightExtraLarge = getFontFawWithSize(size = TextSize.ExtraLarge, fontWeight = FontWeight.Light)

    val FawRegS = getFontFawWithSize(size = TextSize.S)
    val FawRegM = getFontFawWithSize(size = TextSize.M)
    val FawRegL = getFontFawWithSize(size = TextSize.L)
    val FawRegXl = getFontFawWithSize(size = TextSize.Xl)
    val FawRegXxl = getFontFawWithSize(size = TextSize.Xxl)
    val FawRegExtraLarge = getFontFawWithSize(size = TextSize.ExtraLarge)

    val FawBoldS = getFontFawWithSize(size = TextSize.S, fontWeight = FontWeight.Bold)
    val FawBoldM = getFontFawWithSize(size = TextSize.M, fontWeight = FontWeight.Bold)
    val FawBoldL = getFontFawWithSize(size = TextSize.L, fontWeight = FontWeight.Bold)
    val FawBoldXl = getFontFawWithSize(size = TextSize.Xl, fontWeight = FontWeight.Bold)
    val FawBoldXxl = getFontFawWithSize(size = TextSize.Xxl, fontWeight = FontWeight.Bold)
    val FawBoldExtraLarge = getFontFawWithSize(size = TextSize.ExtraLarge, fontWeight = FontWeight.Bold)


    @OptIn(ExperimentalUnitApi::class)
    private fun getFontWithSize(size: Float, fontWeight: FontWeight = FontWeight.Normal) =
        TextStyle(
            fontFamily = Fonts.Montserrat,
            fontSize = size.sp,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center,
            color = AppTheme.color.black
        )

    @OptIn(ExperimentalUnitApi::class)
    private fun getFontFawWithSize(size: Float, fontWeight: FontWeight = FontWeight.Normal) =
        TextStyle(
            fontFamily = Fonts.Faw,
            fontSize = size.dpTextUnit,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center,
            color = AppTheme.color.black
        )
}

object Fonts {
    val Montserrat = FontFamily(
        Font(R.font.mono_light, FontWeight.Light),
        Font(R.font.mono_reg, FontWeight.Normal),
        Font(R.font.mono_bold, FontWeight.Bold),
    )

    val Faw = FontFamily(
        Font(R.font.fa_light, FontWeight.Light),
        Font(R.font.fa_regular, FontWeight.Normal),
        Font(R.font.fa_solid, FontWeight.Medium),
    )
}


object TextSize {
    val S = 12f
    val M = 14f
    val L = 16f
    val Xl = 19f
    val Xxl = 24f
    val ExtraLarge = 36f
}
