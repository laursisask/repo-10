package nl.mranderson.rijks.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import nl.mranderson.rijks.R
import nl.mranderson.rijks.ui.detail.DetailFragment.Companion.ARGUMENTS_ART_ID
import nl.mranderson.rijks.ui.theme.RijksTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val viewModel by viewModel<ListViewModel>()

                val interaction = object : ListInteraction {
                    override fun onCollectionClicked(id: String) {
                        findNavController().navigate(
                            R.id.action_listFragment_to_detailFragment,
                            bundleOf(ARGUMENTS_ART_ID to id)
                        )
                    }
                }

                RijksTheme {
                    Surface {
                        ListScreen(viewModel.artCollectionFlow, interaction)
                    }
                }
            }
        }
    }
}