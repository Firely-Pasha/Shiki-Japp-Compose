package com.pagalaco.shikijapp.data

import net.pagala.kshikisdk.ShikimoriSdk

object ShikimoriApi {
    lateinit var shikimoriSdk: ShikimoriSdk

    fun init(shikimoriSdk: ShikimoriSdk) {
        this.shikimoriSdk = shikimoriSdk
    }

    val animeService by lazy { shikimoriSdk.createAnimeService() }
    val mangaService by lazy { shikimoriSdk.createMangaService() }
}