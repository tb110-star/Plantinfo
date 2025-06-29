package org.example.project.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.error
import kotlinproject.composeapp.generated.resources.placeholder
import org.jetbrains.compose.resources.painterResource

@Composable
fun <T> SimilarImagesRow(
    images: List<T>,
    modifier: Modifier = Modifier,
    getImageUrlSmall: (T) -> String,
    getImageUrlLarge: (T) -> String
) {
    val enlargedImage = remember { mutableStateOf<String?>(null) }
    val placeholderPainter = painterResource(Res.drawable.placeholder)
    val errorPainter = painterResource(Res.drawable.error)

    Column {
        LazyRow(
            modifier = modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(images) { image ->
                AsyncImage(
                    model =  getImageUrlSmall(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = placeholderPainter,
                    error = errorPainter,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .clickable {
                            enlargedImage.value = getImageUrlLarge(image)                        }
                )
            }
        }
        println("Similar Images URLs:")
        images.forEach {
            println(" - small: ${getImageUrlSmall(it)}")
            println(" - large: ${getImageUrlLarge(it)}")
        }

        enlargedImage.value?.let { imageUrl ->
            Dialog(onDismissRequest = { enlargedImage.value = null }) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    placeholder = placeholderPainter,
                    error = errorPainter,
                    modifier = Modifier

                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }
    }
}

