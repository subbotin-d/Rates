package ru.subbotind.android.rates.ui.error

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import ru.subbotind.android.rates.R

private const val ERROR_MESSAGE = "error_message"

class ErrorRetryDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(errorMessage: String) =
            ErrorRetryDialogFragment().apply {
                arguments = bundleOf(ERROR_MESSAGE to errorMessage)
            }
    }

    private var errorMessage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        errorMessage = arguments?.getString(ERROR_MESSAGE)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.error_dialog_title)
            .setMessage(errorMessage ?: getString(R.string.unexpected_error_text))
            .setPositiveButton(R.string.retry_button) { dialog, _ ->
                parentFragmentManager.setFragmentResult(
                    ErrorDialogResult.RESULT_KEY,
                    bundleOf(ErrorDialogResult.BUNDLE_KEY to ErrorDialogResult.Result.POSITIVE_BUTTON_CLICKED.name)
                )
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel_button) { dialog, _ ->
                parentFragmentManager.setFragmentResult(
                    ErrorDialogResult.RESULT_KEY,
                    bundleOf(ErrorDialogResult.BUNDLE_KEY to ErrorDialogResult.Result.NEGATIVE_BUTTON_CLICKED.name)
                )
                dialog.dismiss()
            }
            .create()
    }
}