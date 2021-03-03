package com.example.rickandmortycharacters.characterdetail

import com.example.rickandmortycharacters.UnitTestCoroutineDispatcherProvider
import com.example.assessment_2.api.EpisodeService
import com.example.assessment_2.database.UserDAO
import com.example.rickandmortycharacters.invoke
import com.flextrade.jfixture.JFixture
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class CharacterDetailRepositoryTest {

    //System Under Test
    private lateinit var sut: CharacterDetailRepository
    private lateinit var userDAO: UserDAO
    private lateinit var episodeService: EpisodeService


    private val fixture = JFixture()

    private val dispatchers = UnitTestCoroutineDispatcherProvider()

    private var userId: Int = 0
    private var favoriteCharacterId: Int = 0
    private var episodeId: Int = 0


    @Before
    fun setUp() {
        userDAO = mockk(relaxed = true)
        episodeService = mockk(relaxed = true)
        sut = CharacterDetailRepository(userDAO, episodeService)

        Dispatchers.setMain(dispatchers.main)

        userId = fixture()
        favoriteCharacterId = fixture()
        episodeId = fixture()
    }

    @Test
    fun `update favorite character should to change favorite character to the database service return success`() =
        runBlockingTest {
            coVerify {
                sut.updateFavoriteCharacter(userId, favoriteCharacterId)
                userDAO.updateFavoriteCharacter(userId, favoriteCharacterId)
            }
        }

    @Test
    fun `fetch user by id should get user from the database service return success`() =
        runBlockingTest {
            sut.getUserByUserId(userId)
            coVerify {
               userDAO.getUserById(userId)
            }
        }

    @Test
    fun `fetch episode by id should get episode from the network service return success`() =
        runBlockingTest {
            sut.getSingleEpisode(episodeId)
            coVerify {
                episodeService.getSingleEpisode(episodeId)
            }
        }

}