package org.example.kobweblocalisation

import Res
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.components.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import io.github.skeptick.libres.LibresSettings
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.jetbrains.compose.web.css.vh

private const val COLOR_MODE_KEY = "kobweblocalisation:colorMode"
const val LOCALE_KEY = "kobweblocalisation:locale"

@InitSilk
fun initColorMode(ctx: InitSilkContext) {
    ctx.config.initialColorMode = localStorage.getItem(COLOR_MODE_KEY)?.let { ColorMode.valueOf(it) } ?: ColorMode.DARK
}

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {

    LibresSettings.languageCode =
        (localStorage.getItem(LOCALE_KEY)
            ?: Res.locales.find { it == window.navigator.language }
            ?: Res.locales.first())
            .also { localStorage.setItem(LOCALE_KEY, it) }

    SilkApp {
        val colorMode = ColorMode.current
        LaunchedEffect(colorMode) {
            localStorage.setItem(COLOR_MODE_KEY, colorMode.name)
        }

        Surface(
            SmoothColorStyle.toModifier()
                .minHeight(100.vh)
                .scrollBehavior(ScrollBehavior.Smooth)
        ) {
            content()
        }
    }
}