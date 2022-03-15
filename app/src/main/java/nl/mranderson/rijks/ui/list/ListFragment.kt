package nl.mranderson.rijks.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import nl.mranderson.rijks.ui.theme.RijksTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalFoundationApi
class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val viewModel by viewModel<ListViewModel>()
                RijksTheme {
                    Surface {
                        ListScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}