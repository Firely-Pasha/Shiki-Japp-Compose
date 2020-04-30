package com.pagalaco.shikijapp.ui.compose

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.VectorPainter
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp
import com.pagalaco.shikijapp.R
import java.util.*
import kotlin.collections.HashMap

enum class ShikiDrawerState {
    Anime,
    Manga,
    Calendar
}

class ShikiDrawerModel(
    val title: String
)


@Composable
fun ShikiDrawer(children: @Composable() (ShikiDrawerState) -> Unit = {}) {

    val (shikiDrawer, _) = state {
        Hashtable<ShikiDrawerState, ShikiDrawerModel>().apply {
            put(ShikiDrawerState.Anime, ShikiDrawerModel("Anime"))
            put(ShikiDrawerState.Manga, ShikiDrawerModel("Manga"))
            put(ShikiDrawerState.Calendar, ShikiDrawerModel("Calendar"))
        }
    }

    val (drawerState, onDrawerStateChange) = state { DrawerState.Closed }
    val (drawerPage, onDrawerPageChange) = state { ShikiDrawerState.Anime }
    val (title, onTitleChange) = state { shikiDrawer.getValue(ShikiDrawerState.Anime).title }

    ModalDrawerLayout(
        drawerState = drawerState,
        onStateChange = onDrawerStateChange,
        drawerContent = {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                shikiDrawer.forEach { (t, u) ->
                    ShikiDrawerItem(
                        onClick = {
                            onDrawerPageChange(t)
                            onTitleChange(u.title)
                            onDrawerStateChange(DrawerState.Closed)
                        },
                        title = u.title,
                        isActive = t == drawerPage
                    )
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                }
            }
        },
        bodyContent = {
            Column {
                TopAppBar(
                    title = { Text(text = title) },
                    navigationIcon = {
                        IconButton({
                            onDrawerStateChange.invoke(DrawerState.Opened)
                        },
                            icon = {
                                Icon(
                                    VectorPainter(
                                        asset = vectorResource(
                                            id = R.drawable.ic_launcher_foreground
                                        )
                                    )
                                )
                            })
                    }
                )
                Box(
                    backgroundColor = Color.Black,
                    modifier = Modifier.fillMaxSize()
                ) {
                    children(drawerPage)
                }
            }
        }
    )
}

@Composable
fun ShikiDrawerItem(onClick: () -> Unit, title: String, isActive: Boolean) {

    TextButton(
        onClick = onClick,
        backgroundColor = if (isActive) Color.LightGray else Color.White
    ) {
        Row(
            verticalGravity = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                asset = vectorResource(id = R.drawable.ic_baseline_local_movies_24),
                modifier = Modifier.height(30.dp)
            )
            Text(
                text = title
            )
        }
    }

}