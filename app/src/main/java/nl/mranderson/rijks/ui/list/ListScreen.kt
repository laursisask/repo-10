package nl.mranderson.rijks.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import nl.mranderson.rijks.R
import nl.mranderson.rijks.ui.components.ArtImage
import nl.mranderson.rijks.ui.components.ErrorButton
import nl.mranderson.rijks.ui.components.ErrorView
import nl.mranderson.rijks.ui.components.LoadingView
import nl.mranderson.rijks.ui.list.ListViewModel.ArtUIModel
import nl.mranderson.rijks.ui.list.ListViewModel.ArtUIModel.ArtData
import nl.mranderson.rijks.ui.list.ListViewModel.ArtUIModel.AuthorSeparator

@Composable
fun ListScreen(
    artCollection: LazyPagingItems<ArtUIModel>,
    onArtClicked: (String) -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        items(artCollection.itemCount) { index ->
            artCollection[index]?.let { art ->
                when (art) {
                    is AuthorSeparator -> Separator(
                        author = art.author
                    )
                    is ArtData -> ArtListItem(
                        art = art,
                        onArtClicked = { id ->
                            onArtClicked(id)
                        })
                }
            }
        }
        renderLoading(artCollection)
        renderError(artCollection)
    }
}

private fun LazyListScope.renderLoading(lazyArtCollection: LazyPagingItems<ArtUIModel>) {
    lazyArtCollection.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
            }
            loadState.append is LoadState.Loading -> {
                item { LoadingItem() }
            }
            else -> return
        }
    }
}

private fun LazyListScope.renderError(lazyArtCollection: LazyPagingItems<ArtUIModel>) {
    lazyArtCollection.apply {
        when {
            loadState.refresh is LoadState.Error -> {
                val e = lazyArtCollection.loadState.refresh as LoadState.Error
                item {
                    ErrorView(
                        message = e.error.localizedMessage ?: "",
                        modifier = Modifier.fillParentMaxSize()
                    ) { retry() }
                }
            }
            loadState.append is LoadState.Error -> {
                item {
                    ErrorButton { retry() }
                }
            }
            else -> return
        }
    }
}

@Composable
fun ArtListItem(
    modifier: Modifier = Modifier,
    art: ArtData,
    onArtClicked: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onArtClicked(art.id) }
            .then(modifier),
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Row {
            ArtImage(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
                    .size(84.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(16.dp))),
                imageUrl = art.imageUrl
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = art.title, style = typography.h6)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = stringResource(id = R.string.view_details), style = typography.caption)
            }
        }
    }
}

@Composable
fun Separator(modifier: Modifier = Modifier, author: String) {
    Text(
        text = author,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    )
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}