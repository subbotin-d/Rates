package ru.subbotind.android.rates.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.subbotind.android.rates.R
import ru.subbotind.android.rates.databinding.FragmentCalculationBinding
import ru.subbotind.android.rates.presentation.CalculationViewModel

private const val FROM_CUR_PARAM = "fromCur"
const val RATE_PARAM = "rate"
const val NOMINAL_PARAM = "nominal"

class CalculationFragment : Fragment() {

    companion object {
        fun newInstance(fromCur: String, rate: Double, nominal: Int) =
            CalculationFragment().apply {
                arguments = Bundle().apply {
                    putString(FROM_CUR_PARAM, fromCur)
                    putDouble(RATE_PARAM, rate)
                    putInt(NOMINAL_PARAM, nominal)
                }
            }

        private const val ZERO_AMOUNT = 0
    }

    private var _binding: FragmentCalculationBinding? = null
    private val binding
        get() = _binding!!

    private val calculationViewModel: CalculationViewModel by viewModels()

    private var fromCur: String? = null
    private var rate: Double? = null
    private var nominal: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fromCur = it.getString(FROM_CUR_PARAM)
            rate = it.getDouble(RATE_PARAM)
            nominal = it.getInt(NOMINAL_PARAM)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculationBinding.inflate(inflater, container, false)

        initView()
        initListener()

        calculationViewModel.total.observe(viewLifecycleOwner, ::showTotalAmount)

        return binding.root
    }

    private fun initListener() {
        binding.btnCalculate.setOnClickListener {
            val amount: Int? = binding.tiedAmount.text.toString().toIntOrNull()

            amount?.let {
                calculationViewModel.calculate(amount)
            }
                ?: calculationViewModel.calculate(ZERO_AMOUNT)
        }
    }

    private fun initView() {
        binding.tvCurrentRate.text =
            getString(R.string.current_rate_text, nominal.toString(), fromCur, rate.toString())
    }

    private fun showTotalAmount(amount: String) {
        binding.tvTotalAmount.text = getString(R.string.total_amount_text, amount, fromCur)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}