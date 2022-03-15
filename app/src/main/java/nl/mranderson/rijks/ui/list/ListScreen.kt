package nl.mranderson.rijks.ui.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.flow.Flow
import nl.mranderson.rijks.ui.list.ListViewModel.ArtUIModel.ArtData
import nl.mranderson.rijks.ui.list.ListViewModel.ArtUIModel.AuthorSeparator


@ExperimentalFoundationApi
@Composable
fun ListScreen(viewModel: ListViewModel) {

    val interaction = object : ListInteraction {
        override fun onCollectionClicked(id: String) {
            //TODO navigation
        }
    }

    CollectionList(
        artCollection = viewModel.artCollectionFlow,
        interaction = interaction
    )
}

@ExperimentalFoundationApi
@Composable
fun CollectionList(
    artCollection: Flow<PagingData<ListViewModel.ArtUIModel>>,
    interaction: ListInteraction
) {
    val lazyArtCollection = artCollection.collectAsLazyPagingItems()

    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        items(lazyArtCollection.itemCount) { index ->
            lazyArtCollection[index]?.let { art ->
                when (art) {
                    is AuthorSeparator -> Separator(
                        author = art.author
                    )
                    is ArtData -> ArtListItem(
                        art = art,
                        onArtClicked = { id -> interaction.onCollectionClicked(id) })
                }
            }
        }
        renderLoading(lazyArtCollection)
        renderError(lazyArtCollection)
    }
}

@ExperimentalFoundationApi
private fun LazyListScope.renderLoading(lazyArtCollection: LazyPagingItems<ListViewModel.ArtUIModel>) {
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

@ExperimentalFoundationApi
private fun LazyListScope.renderError(lazyArtCollection: LazyPagingItems<ListViewModel.ArtUIModel>) {
    lazyArtCollection.apply {
        when {
            loadState.refresh is LoadState.Error -> {
                val e = lazyArtCollection.loadState.refresh as LoadState.Error
                item {
                    ErrorItem(
                        message = e.error.localizedMessage ?: "",
                        modifier = Modifier.fillParentMaxSize()
                    ) { retry() }
                }
            }
            loadState.append is LoadState.Error -> {
                item {
                    Button(
                        modifier = Modifier.padding(all = 8.dp),
                        onClick = { retry() }
                    ) {
                        Text(text = "Probeer opnieuw", textAlign = TextAlign.Center)
                    }
                }
            }
            else -> return
        }
    }
}

@Composable
fun Separator(modifier: Modifier = Modifier, author: String) {
    Text(
        text = author, textAlign = TextAlign.Center, modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    )
}

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
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

@Composable
fun ErrorItem(
    modifier: Modifier = Modifier,
    message: String,
    onClickRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onClickRetry) {
            Text(text = "Probeer opnieuw", textAlign = TextAlign.Center)
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
                    .align(Alignment.CenterVertically),
                art = art
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = art.title, style = typography.h6)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "VIEW DETAIL", style = typography.caption)
            }
        }
    }
}

@Composable
fun ArtImage(modifier: Modifier, art: ArtData) {
    SubcomposeAsyncImage(
        modifier = modifier
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp))),
        loading = {
            LoadingView(modifier = Modifier.fillMaxSize())
        },
        error = {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null
            )
        },
        contentScale = ContentScale.Crop,
        model = art.imageUrl,
        contentDescription = null,
    )
}