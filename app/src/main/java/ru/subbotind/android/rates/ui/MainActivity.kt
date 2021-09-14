package ru.subbotind.android.rates.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.subbotind.android.rates.R
import ru.subbotind.android.rates.ui.rates.RatesFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, RatesFragment.newInstance(), RatesFragment.TAG)
                .commit()
        }
    }
}