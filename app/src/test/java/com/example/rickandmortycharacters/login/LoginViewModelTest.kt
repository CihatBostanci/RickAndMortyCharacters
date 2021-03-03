package com.example.rickandmortycharacters.login

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rickandmortycharacters.UnitTestCoroutineDispatcherProvider
import com.example.assessment_2.home.HomeRepository
import com.example.assessment_2.home.HomeViewModel
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

import org.junit.Assert.*
import org.junit.Rule
import kotlin.math.log

@ExperimentalCoroutinesApi
class LoginViewModelTest {
    @get: Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var sut: LoginViewModel
    private lateinit var loginRepository: LoginRepository

    private val dispatchers = UnitTestCoroutineDispatcherProvider()
    private val fixture = JFixture()

    private lateinit var userModel :UserModel

    private lateinit var email:String
    private lateinit var password:String

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatchers.main)

        loginRepository = mockk(relaxed = true)

        sut = LoginViewModel(loginRepository)

        userModel = fixture()
        email = fixture()
        password = fixture()


    }

    @Test
    fun `fetch all users should call get from repository`() = runBlockingTest {

        sut.fetchDataAllUsers()

        coVerify {
            loginRepository.fetchAllUsers()
        }
    }
    @Test
    fun `add  user should register to repository`() = runBlockingTest {

        sut.addUser( userModel )

        coVerify {
            loginRepository.addUser(userModel)
        }
    }

    @Test
    fun `get user should call from repository`() = runBlockingTest {

        sut.getUserByEmailAndPassword( email,password )

        coVerify {
            loginRepository.getUserByEmailAndPassword(email,password )
        }
    }

    @Test
    fun getUserByEmailAndPassword() {
    }
}