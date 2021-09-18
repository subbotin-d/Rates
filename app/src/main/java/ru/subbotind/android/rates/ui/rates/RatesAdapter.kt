package ru.subbotind.android.rates.ui.rates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.subbotind.android.rates.R
import ru.subbotind.android.rates.databinding.RateItemBinding
import ru.subbotind.android.rates.domain.entity.Rate

class RatesAdapter(
    private val rateClickListener: (String, Double, Int) -> Unit
) : ListAdapter<Rate, RecyclerView.ViewHolder>(RateDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        RateViewHolder.from(parent, rateClickListener)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as RateViewHolder).bind(item)
    }
}

class RateViewHolder private constructor(
    private val binding: RateItemBinding,
    private val rateClickListener: (String, Double, Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(
            parent: ViewGroup,
            rateClickListener: (String, Double, Int) -> Unit
        ): RateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RateItemBinding.inflate(inflater, parent, false)
            return RateViewHolder(binding, rateClickListener)
        }
    }

    private lateinit var rate: Rate

    init {
        binding.root.setOnClickListener {
            rateClickListener(rate.code, rate.value, rate.nominal)
        }
    }

    fun bind(rate: Rate) {
        this.rate = rate

        binding.apply {
            tvRate.text = itemView.resources.getString(
                R.string.current_rate_text,
                rate.nominal.toString(),
                rate.code,
                rate.value.toString()
            )

            tvRateDifference.text = rate.change.toString()

            if (rate.change >= 0) {
                ivRateChanges.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
                tvRateDifference.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.green
                    )
                )
            } else {
                ivRateChanges.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
                tvRateDifference.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.red
                    )
                )
            }
        }
    }
}

class RateDiffUtilCallback : DiffUtil.ItemCallback<Rate>() {

    override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean =
        oldItem.code == newItem.code

    override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean =
        oldItem == newItem
}