package ru.subbotind.android.rates.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.WorkRequest
import dagger.hilt.android.AndroidEntryPoint
import ru.subbotind.android.rates.R
import ru.subbotind.android.rates.background.WorkController
import ru.subbotind.android.rates.ui.rates.RatesFragment
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var rateSynchronizationRequest: WorkRequest

    @Inject
    lateinit var workController: WorkController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, RatesFragment.newInstance(), RatesFragment.TAG)
                .commit()
        }

        workController.startWork(rateSynchronizationRequest)
    }
}