package com.pagalaco.shikijapp.ui.compose

import androidx.compose.Composable
import androidx.compose.ambientOf
import androidx.compose.onCommit
import androidx.compose.state
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import com.koduok.compose.glideimage.GlideImage
import com.pagalaco.shikijapp.data.ShikimoriApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import net.pagala.kshikisdk.filters.search.AnimeSearchFilter
import net.pagala.kshikisdk.filters.search.MangaSearchFilter
import net.pagala.kshikisdk.interfaces.Anime
import net.pagala.kshikisdk.interfaces.AnimeInfo
import net.pagala.kshikisdk.interfaces.Title

enum class TitleType {
    Anime,
    Manga
}

@Composable
fun TitleCatalog(type: TitleType) {

    val (animes, setAnimes) = state<List<Title>> { arrayListOf() }

    Box(
        backgroundColor = when(type) {
            TitleType.Anime -> Color(0.9f, 1f, 1f)
            TitleType.Manga -> Color(1f, 0.9f, 1f)
        },
        modifier = Modifier.fillMaxSize()
    ) {

        AdapterList(data = animes) {
            TitleCatalogItem(title = it)
        }

        onCommit(type) {
            GlobalScope.launch(Dispatchers.IO) {
                val titles = when(type) {
                    TitleType.Anime -> {
                        ShikimoriApi.animeService.getList(AnimeSearchFilter().apply {
                            limit = 10
                            page = 1
                        })
                    }
                    TitleType.Manga -> {
                        ShikimoriApi.mangaService.getList(MangaSearchFilter().apply {
                            limit = 10
                            page = 1
                        })
                    }
                }
                launch(Dispatchers.Main) {
                    setAnimes(titles)
                }
            }
        }
    }
}

@Composable
fun TitleCatalogItem(title: Title) {
    Column {
        ShikiImage("https://shikimori.one" + title.image.original)
        Text(text = title.name)
    }
}