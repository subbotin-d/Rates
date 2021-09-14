package ru.subbotind.android.rates.data.network.exception

import java.io.IOException

class ServerErrorException(val reason: String) : IOException(reason)