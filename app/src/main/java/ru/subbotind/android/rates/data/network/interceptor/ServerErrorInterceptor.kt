package ru.subbotind.android.rates.data.network.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import ru.subbotind.android.rates.R
import ru.subbotind.android.rates.data.network.exception.ServerErrorException
import ru.subbotind.android.rates.data.network.exception.UnexpectedServerErrorException
import java.net.HttpURLConnection

class ServerErrorInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        when (response.code) {
            HttpURLConnection.HTTP_OK -> return response
            HttpURLConnection.HTTP_INTERNAL_ERROR -> throw createInternalError()
            HttpURLConnection.HTTP_UNAVAILABLE -> throw createUnavailableError()
            HttpURLConnection.HTTP_BAD_REQUEST -> throw createBadRequestError()
            HttpURLConnection.HTTP_NOT_FOUND -> throw createNotFoundError()
            else -> throw createUnexpectedError()
        }
    }

    private fun createUnexpectedError(message: String? = null): UnexpectedServerErrorException =
        UnexpectedServerErrorException(message ?: context.getString(R.string.unexpected_error_text))

    private fun createNotFoundError(): ServerErrorException =
        ServerErrorException(context.getString(R.string.not_found_error_text))

    private fun createBadRequestError(): ServerErrorException =
        ServerErrorException(context.getString(R.string.bad_request_error_text))

    private fun createUnavailableError(): ServerErrorException =
        ServerErrorException(context.getString(R.string.server_unavailable_error_text))

    private fun createInternalError(): ServerErrorException =
        ServerErrorException(context.getString(R.string.server_internal_error_text))
}