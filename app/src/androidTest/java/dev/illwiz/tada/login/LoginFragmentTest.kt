package dev.illwiz.tada.login

import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.filters.LargeTest
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dev.illwiz.tada.R
import dev.illwiz.tada.TestNavHostControllerRule
import dev.illwiz.tada.domain.usecase.LoginUseCase
import dev.illwiz.tada.domain.usecase.LogoutUseCase
import dev.illwiz.tada.domain.usecase.RegisterUseCase
import dev.illwiz.tada.launchFragmentInHiltContainer
import dev.illwiz.tada.presentation.di.UseCaseModule
import dev.illwiz.tada.presentation.ui.auth.login.LoginFragment
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.delay
import org.junit.*

@LargeTest
@HiltAndroidTest
@UninstallModules(UseCaseModule::class)
class LoginFragmentTest:TestCase() {
	@get:Rule
	val hiltRule = HiltAndroidRule(this)
	@get:Rule
	val testNavHostControllerRule = TestNavHostControllerRule(
		R.navigation.nav_graph_main, R.id.loginFragment
	)

	@BindValue
	@JvmField
	val logoutUseCase = mockk<LogoutUseCase>()
	@BindValue
	@JvmField
	val registerUseCase = mockk<RegisterUseCase>()
	@BindValue
	@JvmField
	val loginUseCase = mockk<LoginUseCase>()

	@Before
	fun setUp() {
		hiltRule.inject()
	}

	@After
	fun tearDown() {
		unmockkAll()
	}

	@Test
	fun testLoginSuccess() {
		before {
			coEvery { loginUseCase.execute(any(),any()) } coAnswers {
				delay(1000)
			}
			launchFragmentInHiltContainer<LoginFragment>(navHostController = testNavHostControllerRule.testNavHostController)
		}.after {
			println("After test")
		}.run {
			step("Type Username & Password") {
				LoginScreen {
					usernameForm {
						typeText("java")
					}
					passwordForm {
						typeText("java-password")
					}
				}
			}
			step("Tap Login Button") {
				LoginScreen {
					loginBtn {
						click()
					}
				}
			}
			step("Verify loading (login button disabled)") {
				LoginScreen {
					flakySafely {
						loginBtn {
							isDisabled()
						}
					}
				}
			}
			step("Verify done loading (login button enabled)") {
				LoginScreen {
					flakySafely {
						loginBtn {
							isEnabled()
						}
					}
				}
			}
			step("Assert login success and redirected to Home") {
				flakySafely {
					val currentDestination = testNavHostControllerRule.testNavHostController.currentDestination
					Assert.assertEquals(R.id.homeFragment,currentDestination?.id)
				}
			}
		}
	}

	@Test
	fun testTapLoginButtonWithEmptyUsername() {
		before {
			launchFragmentInHiltContainer<LoginFragment>(navHostController = testNavHostControllerRule.testNavHostController)
		}.after {
			println("After test")
		}.run {
			step("Provide empty username") {
				LoginScreen {
					passwordForm {
						typeText("java-password")
					}
				}
			}
			step("Tap Login Button") {
				LoginScreen {
					loginBtn {
						click()
					}
				}
			}
			step("Verify display username not valid message") {
				LoginScreen {
					flakySafely {
						usernameTil {
							hasErrorText("Username should not be empty")
						}
					}
				}
			}
		}
	}

	@Test
	fun testTapLoginButtonWithEmptyPassword() {
		before {
			launchFragmentInHiltContainer<LoginFragment>(navHostController = testNavHostControllerRule.testNavHostController)
		}.after {
			println("After test")
		}.run {
			step("Provide empty password") {
				LoginScreen {
					usernameForm {
						typeText("java")
					}
				}
			}
			step("Tap Login Button") {
				LoginScreen {
					loginBtn {
						click()
					}
				}
			}
			step("Verify display password not valid message") {
				LoginScreen {
					flakySafely {
						passwordTil {
							hasErrorText("Password should not be empty")
						}
					}
				}
			}
		}
	}
}