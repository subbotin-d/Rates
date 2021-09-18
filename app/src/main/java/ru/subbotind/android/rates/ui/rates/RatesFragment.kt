package ru.subbotind.android.rates.ui.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.subbotind.android.rates.R
import ru.subbotind.android.rates.databinding.FragmentRatesBinding
import ru.subbotind.android.rates.domain.entity.Rate
import ru.subbotind.android.rates.presentation.ErrorState
import ru.subbotind.android.rates.presentation.RatesViewModel
import ru.subbotind.android.rates.ui.calculation.CalculationFragment
import ru.subbotind.android.rates.ui.error.ErrorDialogResult
import ru.subbotind.android.rates.ui.extensions.showRetryErrorDialog

@AndroidEntryPoint
class RatesFragment : Fragment() {

    companion object {
        fun newInstance() = RatesFragment()

        const val TAG = "RATES_FRAGMENT"
    }

    private val ratesViewModel: RatesViewModel by viewModels()
    private var ratesAdapter: RatesAdapter? = null

    private var _binding: FragmentRatesBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(ErrorDialogResult.RESULT_KEY) { _, bundle ->
            val result = bundle.getString(ErrorDialogResult.BUNDLE_KEY)

            when (result?.let { ErrorDialogResult.Result.valueOf(it) }) {
                ErrorDialogResult.Result.POSITIVE_BUTTON_CLICKED -> onPositiveButtonClick()
                ErrorDialogResult.Result.NEGATIVE_BUTTON_CLICKED -> onNegativeButtonClick()
            }
        }
    }

    private fun onPositiveButtonClick() {
        ratesViewModel.fetchRates()
    }

    private fun onNegativeButtonClick() {
        activity?.finish()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatesBinding.inflate(inflater, container, false)

        initRecycler()
        initSwipeToRefresh()

        ratesViewModel.errorState.observe(viewLifecycleOwner, ::handleError)
        ratesViewModel.rates.observe(viewLifecycleOwner, ::updateRates)

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

    private fun initSwipeToRefresh() {
        binding.swipeToRefreshRates.setOnRefreshListener {
            ratesViewModel.fetchRates()
        }
    }

    private fun handleError(errorState: ErrorState) {
        when (errorState) {
            is ErrorState.ServerError -> showRetryErrorDialog(errorState.cause)
            is ErrorState.UnexpectedError -> showRetryErrorDialog(errorState.cause)
            ErrorState.NoInternetError -> showRetryErrorDialog(getString(R.string.no_internet_message))
        }
    }

    private fun updateRates(rates: List<Rate>) {
        ratesAdapter?.submitList(rates)
        binding.swipeToRefreshRates.isRefreshing = false
    }

    private fun onRateClickListener(): (String, Double, Int) -> Unit = { fromCur, rate, nominal ->
        openRateDetails(fromCur, rate, nominal)
    }

    private fun openRateDetails(fromCur: String, rate: Double, nominal: Int) {
        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(
                R.id.fragmentContainer,
                CalculationFragment.newInstance(fromCur, rate, nominal)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        ratesAdapter = null
    }
}