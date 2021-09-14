package ru.subbotind.android.rates.data.network.exception

import java.io.IOException

class UnexpectedServerErrorException(reason: String) : IOException(reason)