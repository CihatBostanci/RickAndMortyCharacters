package com.example.rickandmortycharacters.di

import com.example.rickandmortycharacters.api.CharacterService
import com.example.rickandmortycharacters.api.EpisodeService
import com.example.rickandmortycharacters.di.interceptors.HeaderInterceptor
import com.example.rickandmortycharacters.di.interceptors.NetworkConnectionInterceptor
import com.example.rickandmortycharacters.di.interceptors.createHttpLoggingInterceptor
import com.example.rickandmortycharacters.di.provider.createOkHttpClient
import com.example.rickandmortycharacters.di.provider.createWebService
import com.example.rickandmortycharacters.utils.baseUrl
import org.koin.core.qualifier.named
import org.koin.dsl.module


val networkModule = module {
    single { createHttpLoggingInterceptor() }
    single { HeaderInterceptor() }
    single { NetworkConnectionInterceptor(context = get()) }
    single(named("safe")) {
        createOkHttpClient(
            isSafe = false,
            headerInterceptor = get(),
            networkConnectionInterceptor = get(),
            httpLoggingInterceptor = get(),
        )
    }
    single(named("unsafe")){
        createOkHttpClient(
            isSafe = false,
            headerInterceptor = get(),
            networkConnectionInterceptor = get(),
            httpLoggingInterceptor = get(),
        )
    }

    single { createWebService<CharacterService>(okHttpClient = get(qualifier = named("safe")), url = baseUrl) }
    single { createWebService<EpisodeService>(okHttpClient = get(qualifier = named("safe")), url = baseUrl) }


}


