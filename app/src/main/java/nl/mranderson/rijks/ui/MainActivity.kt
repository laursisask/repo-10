package nl.mranderson.rijks.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.mranderson.rijks.ui.navigation.NavGraph
import nl.mranderson.rijks.ui.theme.RijksTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This will lay out our app behind the system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            RijksTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}