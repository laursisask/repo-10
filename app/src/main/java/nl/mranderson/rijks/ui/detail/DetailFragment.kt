package nl.mranderson.rijks.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import nl.mranderson.rijks.ui.detail.DetailViewModel.ScreenState.Loading
import nl.mranderson.rijks.ui.theme.RijksTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment() {

    companion object {
        const val ARGUMENTS_ART_ID = "id"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val viewModel by viewModel<DetailViewModel>(parameters = {
                    parametersOf(
                        requireArguments().get(ARGUMENTS_ART_ID)
                    )
                })

                val state by viewModel.state.observeAsState(Loading)

                val interaction = object : DetailInteraction {
                    override fun onBackClicked() {
                        findNavController().popBackStack()
                    }

                    override fun onRetryClicked() {
                        viewModel.onRetryClicked()
                    }
                }

                RijksTheme {
                    Surface {
                        DetailScreen(state = state, interaction = interaction)
                    }
                }
            }
        }
    }
}