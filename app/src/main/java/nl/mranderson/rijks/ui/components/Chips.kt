package nl.mranderson.rijks.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.mranderson.rijks.ui.theme.Purple40
import nl.mranderson.rijks.ui.theme.White

@Composable
fun Chips(
    modifier: Modifier = Modifier,
    titles: List<String>
) {
    LazyRow(modifier = modifier) {
        items(titles) {
            Chip(
                name = it,
            )
        }
    }
}

@Composable
fun Chip(
    name: String,
    backgroundColor: Color = Purple40,
    textColor: Color = White,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .height(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
    ) {
        Text(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 8.dp)
                .wrapContentHeight(CenterVertically),
            text = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}

@Preview
@Composable
private fun Preview() {
    Chips(titles = listOf("Schilderij", "Model", "Object"))
}