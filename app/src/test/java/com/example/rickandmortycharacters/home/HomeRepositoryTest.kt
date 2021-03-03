package com.example.rickandmortycharacters.home


import com.example.rickandmortycharacters.UnitTestCoroutineDispatcherProvider
import com.example.assessment_2.api.CharacterService
import com.example.assessment_2.database.UserDAO
import com.example.rickandmortycharacters.invoke
import com.flextrade.jfixture.JFixture
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeRepositoryTest {

    //System Under Test
    private lateinit var sut: HomeRepository
    private lateinit var userDAO: UserDAO
    private lateinit var characterService: CharacterService

    private val fixture = JFixture()

    private val dispatchers = UnitTestCoroutineDispatcherProvider()

    private var pageNumber = 0
    private var name = ""
    private var status = ""
    private var userId = 0
    private var favoriteCharacterId = 0

    @Before
    fun setUp() {

        characterService = mockk(relaxed = true)
        userDAO = mockk(relaxed = true)
        sut = HomeRepository(characterService, userDAO)
        Dispatchers.setMain(dispatchers.main)

        //Fixture
        pageNumber = fixture()
        name = fixture()
        status = fixture()
        userId = fixture()
        favoriteCharacterId = fixture()

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `get all characters service should call operation from the network service return success`() = runBlockingTest{
        sut.getAllCharacters()
        coVerify {
            characterService.getAllCharacters()
        }
    }

    @Test
    fun `get all characters with page number service should call operation from the network service return success`() = runBlockingTest{
        sut.getAllCharacters(pageNumber)
        coVerify {
            characterService.getAllCharacters(pageNumber)
        }
    }

    @Test
    fun `filter all characters according to name and status service should call operation from the network service return success`() = runBlockingTest{
        sut.filterAllCharacters( name,status)
        coVerify {
            characterService.filterByNameAndStatusAllCharacters(name, status)
        }
    }


    @Test
    fun `update favorite character  according to select character id should refresh operation to the room database return success`() = runBlockingTest{
        coVerify {
            sut.updateFavoriteCharacter( userId,favoriteCharacterId )
            userDAO.updateFavoriteCharacter(userId,favoriteCharacterId )
        }
    }

    @Test
    fun `get user according to user id should call operation to the room database return success`() = runBlockingTest{
        userDAO.getUserById(userId )
        coVerify {
            sut.getUserByUserId( userId )

        }
    }

}