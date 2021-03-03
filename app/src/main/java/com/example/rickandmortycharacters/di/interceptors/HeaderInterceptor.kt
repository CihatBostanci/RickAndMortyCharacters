package com.example.rickandmortycharacters.di.interceptors

import com.example.rickandmortycharacters.MainApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val baseRequest = chain.request()
        val request =baseRequest.newBuilder()
        request.method(baseRequest.method(), baseRequest.body())
            /*.addHeader(
                "Authorization",
                "Bearer " + MainApplication.sharedPreferencesManager[TOKEN, ""]
            )*/
        return chain.proceed(request.build())
    }
}