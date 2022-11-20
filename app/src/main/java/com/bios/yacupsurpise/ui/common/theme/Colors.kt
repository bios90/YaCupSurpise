package com.test.yacupsurprise.ui.common.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.bios.yacupsurpise.ui.common.theme.color

class Colors {
    val transparent by mutableStateOf("#00000000".color)
    val white by mutableStateOf("#ffffff".color)
    val whiteSnow by mutableStateOf("#E8E9ED".color)
    val black by mutableStateOf("#0C0C0C".color)
    val gray1 by mutableStateOf("#E8E8E8".color)
    val gray2 by mutableStateOf("#8A8C90".color)
    val green by mutableStateOf("#31B768".color)
    val red by mutableStateOf("#F83138".color)
    val blueLight by mutableStateOf("#669BBC".color)
    val blueDark by mutableStateOf("#29335C".color)
    val orange by mutableStateOf("#E4572E".color)
    val yellow by mutableStateOf("#F3A712".color)
    val pink by mutableStateOf("#FF7B9C".color)
    val blueSecondary by mutableStateOf("#607196".color)
    val blueGray by mutableStateOf("#BABFD1".color)
}
