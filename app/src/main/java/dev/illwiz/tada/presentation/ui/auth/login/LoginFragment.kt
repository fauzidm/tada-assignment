package dev.illwiz.tada.presentation.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.illwiz.tada.R
import dev.illwiz.tada.data.utils.LayoutUtils
import dev.illwiz.tada.data.utils.TaskState
import dev.illwiz.tada.data.utils.onDestroyNullable
import dev.illwiz.tada.databinding.FragmentLoginBinding
import dev.illwiz.tada.presentation.ui.BaseFragment

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private val binding by onDestroyNullable {
        FragmentLoginBinding.inflate(LayoutInflater.from(requireContext()))
    }
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.usernameForm.doAfterTextChanged {
            binding.usernameTil.error = null
        }
        binding.passwordForm.doAfterTextChanged {
            binding.passwordTil.error = null
        }
        binding.passwordForm.setOnEditorActionListener { v, actionId, event ->
            if(actionId== EditorInfo.IME_ACTION_DONE) {
                removeFormFocus()
            }
            return@setOnEditorActionListener false
        }

        binding.registerTxt.setOnClickListener {
            removeFormFocus()
            findNavController().navigate(R.id.registerFragment)
        }

        binding.loginBtn.setOnClickListener {
            removeFormFocus()
            val username = binding.usernameForm.text.toString()
            val password = binding.passwordForm.text.toString()
            var valid = true
            if(username.isBlank()) {
                binding.usernameTil.error = "Username should not be empty"
                valid = false
            }
            if(password.isBlank()) {
                binding.passwordTil.error = "Password should not be empty"
                valid = false
            }
            if(!valid) {
                return@setOnClickListener
            }
            viewModel.login(username, password)
        }

        // Listen to LoginTask.Login events
        viewModel.taskResult<LoginTask.Login, Unit>().observe(viewLifecycleOwner) {
            if(it.status == TaskState.Status.LOADING) {
                setLoading(true)
            } else if(it.status == TaskState.Status.SUCCESS) {
                setLoading(false)
                findNavController().popBackStack()
                findNavController().navigate(R.id.homeFragment)
            } else if(it.status == TaskState.Status.ERROR) {
                setLoading(false)
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setLoading(loading:Boolean) {
        binding.root.interceptTouch = loading
        binding.loginBtn.isEnabled = !loading
    }

    private fun removeFormFocus() {
        LayoutUtils.hideKeyboard(binding.root)
    }
}