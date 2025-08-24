package mz.co.macave.mundodoscarros.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtendedColors(
    val customBackgroundColor: Color,
    val customBackgroundContainer: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        customBackgroundColor = ScreenBackground,
        customBackgroundContainer = OnScreenBackGroundContainer
    )
}