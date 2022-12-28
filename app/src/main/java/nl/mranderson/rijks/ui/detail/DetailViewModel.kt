package nl.mranderson.rijks.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.mranderson.rijks.domain.model.ArtDetails
import nl.mranderson.rijks.domain.usecase.GetArtDetails
import nl.mranderson.rijks.ui.detail.DetailViewModel.ScreenState.Data
import nl.mranderson.rijks.ui.detail.DetailViewModel.ScreenState.Error
import nl.mranderson.rijks.ui.detail.DetailViewModel.ScreenState.Loading

class DetailViewModel(
    private val artId: String,
    val getArtDetails: GetArtDetails
) : ViewModel() {

    private val _state = MutableLiveData<ScreenState>()
    val state: LiveData<ScreenState> = _state

    init {
        fetchArtDetails()
    }

    fun onRetryClicked() {
        fetchArtDetails()
    }

    private fun fetchArtDetails() {
        viewModelScope.launch {
            _state.value = Loading
            getArtDetails(artId = artId).onSuccess {
                _state.value = Data(artDetail = it)
            }.onFailure {
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