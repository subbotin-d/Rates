package ru.subbotind.android.rates.ui.error

object ErrorDialogResult {

    const val RESULT_KEY = "ERROR_DIALOG_RESULT_KEY"
    const val BUNDLE_KEY = "EVENT"

    enum class Result {
        POSITIVE_BUTTON_CLICKED,
        NEGATIVE_BUTTON_CLICKED
    }
}