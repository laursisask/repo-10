package nl.mranderson.rijks.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map
import nl.mranderson.rijks.domain.usecase.GetCollection
import nl.mranderson.rijks.ui.list.ListViewModel.ArtUIModel.AuthorSeparator

@HiltViewModel
class ListViewModel @Inject constructor(
    getCollection: GetCollection
) : ViewModel() {

    val artCollectionFlow = getCollection().map { pagingData ->
        pagingData.map { art ->
            ArtUIModel.ArtData(
                id = art.objectNumber,
                title = art.title,
                author = art.author,
                imageUrl = art.imageUrl
            )
        }.insertSeparators { before, after ->
            when {
                before == null && after != null -> AuthorSeparator(after.author)
                before != null && after != null && before.author != after.author -> AuthorSeparator(
                    after.author
                )
                else -> null
            }
        }
    }.cachedIn(viewModelScope)

    sealed class ArtUIModel {
        class ArtData(
            val id: String,
            val title: String,
            val author: String,
            val imageUrl: String
        ) : ArtUIModel()
        class AuthorSeparator(val author: String) : ArtUIModel()
    }
}