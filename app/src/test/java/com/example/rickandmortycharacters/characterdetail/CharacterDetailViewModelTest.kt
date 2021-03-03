package com.example.rickandmortycharacters.characterdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rickandmortycharacters.UnitTestCoroutineDispatcherProvider
import com.example.assessment_2.home.HomeRepository
import com.example.assessment_2.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import com.example.rickandmortycharacters.invoke
import com.flextrade.jfixture.JFixture
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain


import org.junit.Rule

@ExperimentalCoroutinesApi
class CharacterDetailViewModelTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var sut: CharacterDetailViewModel
    private lateinit var characterDetailRepository: CharacterDetailRepository

    private val dispatchers = UnitTestCoroutineDispatcherProvider()
    private val fixture = JFixture()

    private var userId: Int = 0
    private var favoriteId:Int = 0
    private var episodeId: Int = 0



    @Before
    fun setUp() {

        characterDetailRepository = mockk(relaxed = true)

        sut = CharacterDetailViewModel(characterDetailRepository)
        Dispatchers.setMain(dispatchers.main)

        userId = fixture()
        favoriteId = fixture()
        episodeId = fixture()

    }
    @Test
    fun `update favorite characters should call update from repository`() = runBlockingTest {

        sut.updateFavoriteCharacter( userId,favoriteId)

        coVerify {
            characterDetailRepository.updateFavoriteCharacter(userId, favoriteId)
        }
    }
    @Test
    fun `fetch user by id should call get from repository`() = runBlockingTest {

        sut.getUserById( userId)

        coVerify {
            characterDetailRepository.getUserByUserId(userId)
        }
    }

    @Test
    fun `fetch episode by id should call get from repository`() = runBlockingTest {

        sut.getSingleEpisode( episodeId )

        coVerify {
            characterDetailRepository.getSingleEpisode(episodeId)
        }
    }
}