package dev.illwiz.tada.login

import com.kaspersky.kaspresso.screens.KScreen
import dev.illwiz.tada.R
import dev.illwiz.tada.presentation.ui.auth.login.LoginFragment
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.text.KButton

object LoginScreen : KScreen<LoginScreen>() {

	override val layoutId: Int = R.layout.fragment_login
	override val viewClass: Class<*> = LoginFragment::class.java

	val usernameForm = KEditText { withId(R.id.usernameForm) }
	val usernameTil = KTextInputLayout { withId(R.id.usernameTil) }
	val passwordForm = KEditText { withId(R.id.passwordForm) }
	val passwordTil = KTextInputLayout { withId(R.id.passwordTil) }
	val loginBtn = KButton { withId(R.id.loginBtn) }
}