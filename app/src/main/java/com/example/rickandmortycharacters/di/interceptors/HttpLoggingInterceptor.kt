package com.example.rickandmortycharacters.di.interceptors

import com.example.rickandmortycharacters.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

fun createHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.BASIC
    }