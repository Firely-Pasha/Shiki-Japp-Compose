package com.pagalaco.shikijapp.ui.compose

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.Composable
import androidx.compose.onCommit
import androidx.compose.state
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.WithConstraints
import androidx.ui.foundation.Image
import androidx.ui.graphics.ImageAsset
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.height
import androidx.ui.layout.width
import androidx.ui.unit.Dp
import androidx.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

@Composable
fun ShikiImage(imageUrl: String, width: Dp, height: Dp) {
    WithConstraints(
        modifier = Modifier.width(width) + Modifier.height(height)
    ) { constraints, layoutDirection ->
        val (image, setImage) = state<ImageAsset?> { null }
        val context = ContextAmbient.current

        onCommit(image) {
            val target = object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                    setImage(null)
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    setImage(resource.asImageAsset())
                }
            }

            Glide
                .with(context)
                .apply {
                    setImage(null)
                    clear(target)
                }
                .asBitmap()
                .load(imageUrl)
                .into(target)
        }

        image?.let {
            Image(it)
        }
    }
}