package com.bios.yacupsurpise.ui.common.subviews

import android.graphics.Paint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bios.yacupsurpise.R
import com.bios.yacupsurpise.base.dp2pxInt
import com.bios.yacupsurpise.data.models.ObjectWithImageUrl
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.rememberDrawablePainter

@Composable
fun GlideImageApp(
    src: Any?,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    contentDescription: String = "my_glide_image",
    contentScale: ContentScale = ContentScale.Crop,
    isCircle: Boolean = false,
    @DrawableRes errorDrawableId: Int = R.drawable.img_placeholder_new,
    @DrawableRes placeHolderDrawableId: Int = R.drawable.img_placeholder_new,
) {
    ContentButton(
        modifier = modifier
            .run {
                if (isCircle) {
                    clip(CircleShape)
                } else {
                    this
                }
            },
        corners = RoundedCornerShape(0.dp),
        onClick = onClick ?: {},
        disabledAlpha = 1f,
        isEnabled = onClick != null
    ) {
        val img = if (src is ObjectWithImageUrl) {
            src.imageUrl
        } else {
            src
        }
        GlideImage(
            modifier = Modifier.fillMaxSize(),
            imageModel = img,
            placeHolder = rememberDrawablePainter(drawable = getProgressDrawableCompose()),
            contentDescription = contentDescription,
            contentScale = contentScale,
            error = painterResource(id = errorDrawableId),
            previewPlaceholder = placeHolderDrawableId
        )
    }
}

@Composable
private fun getProgressDrawableCompose(): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(LocalContext.current)
    circularProgressDrawable.strokeWidth = dp2pxInt(3f).toFloat()
    circularProgressDrawable.centerRadius = dp2pxInt(18f).toFloat()
    circularProgressDrawable.strokeCap = Paint.Cap.ROUND
    circularProgressDrawable.setColorSchemeColors(colorResource(R.color.orange).value.toInt())
    circularProgressDrawable.start()
    return circularProgressDrawable
}
