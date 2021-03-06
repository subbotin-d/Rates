package ru.subbotind.android.rates.ui.extensions

import androidx.fragment.app.Fragment
import ru.subbotind.android.rates.ui.error.ErrorRetryDialogFragment

fun Fragment.showRetryErrorDialog(message: String) {
    val errorDialogFragment = ErrorRetryDialogFragment.newInstance(message)

    parentFragmentManager.let {
        errorDialogFragment.showNow(
            it,
            ErrorRetryDialogFragment::class.java.simpleName
        )
    }
}