package com.example.tinkoffmovies.data.interceptor

import android.content.Context
import com.example.tinkoffmovies.R
import com.example.tinkoffmovies.common.exceptions.TinkoffException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

// Прям так инжектить контекст в интерсептор такая себе идея, но...
class HttpErrorInterceptor @Inject constructor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (!response.isSuccessful)
            // Можно было бы сделать разные модели, но особого смысла нет.
            // Вот если бы было различное поведение на ui слое - тогда да.
            TinkoffException(
                when (response.code) {
                    401 -> context.getString(R.string.token_exception_text)
                    402 -> context.getString(R.string.too_manu_daily_requests)
                    404 -> context.getString(R.string.film_not_found)
                    429 -> context.getString(R.string.ddos_exception)
                    else -> context.getString(R.string.something_went_wrong)
                }
            )
        return response
    }
}