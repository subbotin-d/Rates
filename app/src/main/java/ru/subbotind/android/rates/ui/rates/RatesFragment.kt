package ru.subbotind.android.rates.ui.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.subbotind.android.rates.R
import ru.subbotind.android.rates.databinding.FragmentRatesBinding
import ru.subbotind.android.rates.presentation.ErrorState
import ru.subbotind.android.rates.presentation.RatesViewModel
import ru.subbotind.android.rates.ui.CalculationFragment
import ru.subbotind.android.rates.ui.error.OnCancelButtonClickListener
import ru.subbotind.android.rates.ui.error.OnRetryButtonClickListener
import ru.subbotind.android.rates.ui.extensions.showRetryErrorDialog

@AndroidEntryPoint
class RatesFragment : Fragment(), OnRetryButtonClickListener, OnCancelButtonClickListener {

    companion object {
        fun newInstance() = RatesFragment()

        const val TAG = "RATES_FRAGMENT"
    }

    private val ratesViewModel: RatesViewModel by viewModels()
    private var ratesAdapter: RatesAdapter? = null

    private var _binding: FragmentRatesBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatesBinding.inflate(inflater, container, false)

        initRecycler()

        ratesViewModel.rates.observe(viewLifecycleOwner) { rates ->
            ratesAdapter?.submitList(rates)
        }

        ratesViewModel.errorState.observe(viewLifecycleOwner, ::handleError)

        return binding.root
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(context)
        ratesAdapter = RatesAdapter(onRateClickListener())

        binding.movieListRecyclerView.apply {
            setLayoutManager(layoutManager)
            adapter = ratesAdapter
        }
    }

    private fun onRateClickListener(): (String, Double, Int) -> Unit = { fromCur, rate, nominal ->
        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(
                R.id.fragmentContainer,
                CalculationFragment.newInstance(fromCur, rate, nominal)
            )
            .addToBackStack(null)
            .commit()
    }

    private fun handleError(errorState: ErrorState) {
        when (errorState) {
            is ErrorState.ServerError -> showRetryErrorDialog(errorState.cause)
            is ErrorState.UnexpectedError -> showRetryErrorDialog(errorState.cause)
            ErrorState.NoInternetError -> showRetryErrorDialog("Интернет включи падла!")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        ratesAdapter = null
    }

    override fun onRetryButtonClick() {
        //TODO("Not yet implemented")
    }

    override fun onCancelButtonClick() {
        //TODO("Not yet implemented")
    }
}