package dev.illwiz.tada

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import dev.illwiz.tada.domain.usecase.LoginUseCase
import dev.illwiz.tada.presentation.ui.auth.login.LoginTask
import dev.illwiz.tada.presentation.ui.auth.login.LoginViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@SmallTest
@RunWith(JUnit4::class)
class LoginViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = Dispatchers.Unconfined

    @RelaxedMockK
    lateinit var loginUseCase: LoginUseCase
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        loginViewModel = LoginViewModel(dispatcher,loginUseCase)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun loginViewModelGetTasksShouldContainRequiredTask() {
        // Arrange
        val requiredTasks = listOf(LoginTask.Login)
        // Act
        val result = loginViewModel.getTasks()
        // Assert
        assertTrue(result.containsAll(requiredTasks))
    }

    @Test
    fun loginTaskShouldCallItsUseCase() {
        // Arrange
        val username = "java"
        val password = "java"
        coEvery { loginUseCase.execute(username,password) } returns Unit
        // Act
        loginViewModel.login(username,password)
        // Assert
        coVerify(exactly = 1) { loginUseCase.execute(username,password) }
    }
}