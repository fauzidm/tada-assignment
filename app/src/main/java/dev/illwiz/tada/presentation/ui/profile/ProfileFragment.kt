package dev.illwiz.tada.presentation.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.illwiz.tada.data.pref.CurrentUserPref
import dev.illwiz.tada.data.utils.TaskState
import dev.illwiz.tada.data.utils.onDestroyNullable
import dev.illwiz.tada.databinding.FragmentProfileBinding
import dev.illwiz.tada.presentation.ui.BaseFragment
import dev.illwiz.tada.presentation.ui.MainActivity
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    private val binding by onDestroyNullable {
        FragmentProfileBinding.inflate(LayoutInflater.from(requireContext()))
    }
    private var initialized = false

    @Inject
    lateinit var currentUserPref: CurrentUserPref

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(initialized) return
        initialized = true

        currentUserPref.getCurrentUser()?.let {
            Glide.with(this)
                .load(it.avatar)
                .into(binding.avatarImg)
            binding.usernameTxt.text = it.username
        }
        binding.logoutBtn.setOnClickListener {
            viewModel.logout()
        }

        // Listen to ProfileTask.Logout events
        viewModel.taskResult<ProfileTask.Logout, Unit>().observe(viewLifecycleOwner) {
            if(it.status == TaskState.Status.LOADING) {
                setLoading(true)
            } else if(it.status == TaskState.Status.SUCCESS) {
                setLoading(false)
                requireActivity().finish()
                startActivity(Intent(requireContext(),MainActivity::class.java))
            } else if(it.status == TaskState.Status.ERROR) {
                setLoading(false)
                Toast.makeText(requireContext(),it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setLoading(loading:Boolean) {
        binding.root.interceptTouch = loading
        binding.logoutBtn.isEnabled = !loading
    }
}