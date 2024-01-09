package nl.mranderson.rijks.ui.image

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MagnifierStyle
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.magnifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import nl.mranderson.rijks.ui.components.ArtImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageScreen(
    imageUrl: String,
    onBackClicked: () -> Unit
) {
    var magnifierCenter by remember {
        mutableStateOf(Offset.Unspecified)
    }

    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = onBackClicked,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 16.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null,
                )
            }
            ArtImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { magnifierCenter = it },
                            onDrag = { _, delta ->
                                magnifierCenter = magnifierCenter.plus(delta)
                            },
                            onDragEnd = { magnifierCenter = Offset.Unspecified },
                            onDragCancel = { magnifierCenter = Offset.Unspecified },
                        )
                    }
                    .magnifier(
                        sourceCenter = { magnifierCenter },
                        style = MagnifierStyle(
                            size = DpSize(100.dp, 100.dp),
                            cornerRadius = 100.dp
                        )
                    ),
                imageUrl = imageUrl,
                contentScale = ContentScale.Fit
            )
        }
    }
}