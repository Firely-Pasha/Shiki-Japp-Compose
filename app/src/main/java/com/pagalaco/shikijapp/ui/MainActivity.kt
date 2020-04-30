package com.pagalaco.shikijapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import com.chuckerteam.chucker.api.Chucker
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.pagalaco.shikijapp.data.ShikimoriApi
import com.pagalaco.shikijapp.ui.compose.ComposeApp
import net.pagala.kshikisdk.ShikimoriSdk

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ShikimoriApi.init(ShikimoriSdk("ShikiJapp", "Firely-Pasha", clientBuilderMod = {
            it.addInterceptor(ChuckerInterceptor(this))
        }))

        setContent {
            MaterialTheme {
                ComposeApp()
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        ComposeApp()
    }
}