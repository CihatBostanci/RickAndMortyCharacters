package com.example.rickandmortycharacters.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rickandmortycharacters.UnitTestCoroutineDispatcherProvider
import com.flextrade.jfixture.JFixture
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import com.example.rickandmortycharacters.invoke
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get: Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var sut: HomeViewModel
    private lateinit var homeRepository: HomeRepository

    private val dispatchers = UnitTestCoroutineDispatcherProvider()
    private val fixture = JFixture()

    private  var pageNumberMock :Int? = null
    private  var nameMock :String? = null
    private  var statusMock :String? = null


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatchers.main)

        homeRepository = mockk(relaxed = true)

        sut = HomeViewModel(homeRepository)
        pageNumberMock = fixture()
        nameMock = fixture()
        statusMock = fixture()

    }
    @Test
    fun `fetch all characters should call get from repository`() = runBlockingTest {

        sut.getAllCharacters()

        coVerify {
            homeRepository.getAllCharacters()
        }
    }
    @Test
    fun `fetch with by pageNumber characters should call get from repository`() = runBlockingTest {

        pageNumberMock?.let {
            sut.getAllCharactersWithPage( it )
        }
        coVerify {
            pageNumberMock?.let {
                homeRepository.getAllCharacters( it )
            }
        }
    }

    @Test
    fun `filter with by name and status characters should call get from repository`() = runBlockingTest {

        sut.filterByNameAndStatus( nameMock,statusMock )

        coVerify {
            homeRepository.filterAllCharacters( nameMock, statusMock )
        }
    }


}