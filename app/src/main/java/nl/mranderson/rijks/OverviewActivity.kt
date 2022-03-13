package nl.mranderson.rijks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.androidx.viewmodel.ext.android.viewModel

class OverviewActivity : AppCompatActivity() {

    val overviewViewModel: OverviewViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}