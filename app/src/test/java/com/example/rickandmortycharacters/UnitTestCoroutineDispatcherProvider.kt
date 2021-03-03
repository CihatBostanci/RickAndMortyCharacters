package com.example.rickandmortycharacters

import com.example.assessment_2.core.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class UnitTestCoroutineDispatcherProvider : CoroutineDispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Unconfined
    override val io: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}