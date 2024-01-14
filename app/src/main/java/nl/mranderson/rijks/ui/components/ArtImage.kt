package nl.mranderson.rijks.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage

@Composable
fun ArtImage(modifier: Modifier, imageUrl: String, contentScale: ContentScale = ContentScale.Crop) {
    SubcomposeAsyncImage(
        modifier = modifier,
        loading = {
            LoadingView(modifier = Modifier.fillMaxSize())
        },
        error = {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null
            )
        },
        contentScale = contentScale,
        model = imageUrl,
        contentDescription = null,
    )
}