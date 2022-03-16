package nl.mranderson.rijks.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import nl.mranderson.rijks.ui.theme.Purple40

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
    backgroundColor: Color = Purple40
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .height(32.dp),
        shape = MaterialTheme.shapes.medium,
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name.capitalize())
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Chips(titles = listOf("Schilderij", "Model", "Object"))
}