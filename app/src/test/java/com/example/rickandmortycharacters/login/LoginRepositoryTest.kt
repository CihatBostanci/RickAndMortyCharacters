package com.example.rickandmortycharacters.login


import com.example.rickandmortycharacters.UnitTestCoroutineDispatcherProvider
import com.example.assessment_2.database.UserDAO
import com.example.rickandmortycharacters.invoke
import com.example.assessment_2.model.UserModel
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
class LoginRepositoryTest {
    //System Under Test
    private lateinit var sut: LoginRepository
    private lateinit var userDAO: UserDAO


    private val fixture = JFixture()

    private val dispatchers = UnitTestCoroutineDispatcherProvider()
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var userModelMock: UserModel


    @Before
    fun setUp() {

        userDAO = mockk(relaxed = true)
        sut = LoginRepository(userDAO)
        Dispatchers.setMain(dispatchers.main)

        userEmail = fixture()
        userPassword = fixture()
        userModelMock = fixture()
    }

    @Test
    fun `fetch all users service should call operation from the database service return success`() = runBlockingTest{
        sut.fetchAllUsers()
        coVerify {
            userDAO.findAllUsers()
        }
    }

    @Test
    fun `add  user service should register operation to the database service return success`() = runBlockingTest{

        coVerify {
            sut.addUser(userModelMock)
            userDAO.add(userModelMock)
        }
    }
    @Test
    fun `get  user by email and password service should call operation from the database service return success`() = runBlockingTest{
        sut.getUserByEmailAndPassword(userEmail, userPassword)
        coVerify {
            userDAO.getUserByEmailAndPassword(userEmail,userPassword)
        }
    }

}