package nl.mranderson.rijks.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.mranderson.rijks.domain.model.ArtDetails
import nl.mranderson.rijks.domain.usecase.GetArtDetails
import nl.mranderson.rijks.ui.detail.DetailViewModel.ScreenState.Data
import nl.mranderson.rijks.ui.detail.DetailViewModel.ScreenState.Error
import nl.mranderson.rijks.ui.detail.DetailViewModel.ScreenState.Loading
import nl.mranderson.rijks.ui.navigation.Screens

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    val getArtDetails: GetArtDetails
) : ViewModel() {

    private val _state: MutableStateFlow<ScreenState> = MutableStateFlow(Loading)
    val state: StateFlow<ScreenState> get() = _state.asStateFlow()

    init {
        fetchArtDetails()
    }

    fun onRetryClicked() {
        fetchArtDetails()
    }

    private fun fetchArtDetails() {
        viewModelScope.launch {
            _state.value = Loading
            val artId : String? = savedState[Screens.Detail.argArtId]
            if (artId != null) {
                getArtDetails(artId = artId).onSuccess {
                    _state.value = Data(artDetail = it)
                }.onFailure {
                    _state.value = Error
                }
            } else {
                _state.value = Error
            }
        }
    }

    sealed class ScreenState {
        object Loading : ScreenState()
        data class Data(val artDetail: ArtDetails) : ScreenState()
        object Error : ScreenState()
    }

}