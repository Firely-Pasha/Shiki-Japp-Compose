package com.pagalaco.shikijapp.ui.compose

import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.graphics.Color
import androidx.ui.layout.fillMaxSize

@Composable
fun ComposeApp() {
    ShikiDrawer { drawerState ->
        Crossfade(current = drawerState) {
            when(it) {
                ShikiDrawerState.Anime -> {
                    TitleCatalog(type = TitleType.Anime)
                }
                ShikiDrawerState.Manga -> {
                    TitleCatalog(type = TitleType.Manga)
                }
                ShikiDrawerState.Calendar -> {
                    ShikiCalendar()
                }
            }
        }
    }
}