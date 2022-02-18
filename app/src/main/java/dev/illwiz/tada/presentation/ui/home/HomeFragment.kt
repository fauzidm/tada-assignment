package dev.illwiz.tada.presentation.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.GravityCompat
import androidx.core.view.doOnLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dagger.hilt.android.AndroidEntryPoint
import dev.illwiz.tada.R
import dev.illwiz.tada.data.pref.CurrentUserPref
import dev.illwiz.tada.data.utils.LayoutUtils
import dev.illwiz.tada.data.utils.onDestroyNullable
import dev.illwiz.tada.databinding.FragmentHomeBinding
import dev.illwiz.tada.presentation.ui.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment(),NavController.OnDestinationChangedListener {
    private val binding by onDestroyNullable {
        FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()))
    }
    private var initialized = false

    @Inject
    lateinit var currentUserPref: CurrentUserPref

    private lateinit var actionBarDrawerToggle:ActionBarDrawerToggle

    private val navController by lazy {
        (requireActivity().supportFragmentManager.findFragmentById(R.id.homeNavHostFragment) as NavHostFragment).navController
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(initialized) return
        initialized = true

        actionBarDrawerToggle = ActionBarDrawerToggle(requireActivity(), binding.root, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        actionBarDrawerToggle.drawerArrowDrawable.color = Color.WHITE
        binding.root.removeDrawerListener(actionBarDrawerToggle)
        binding.root.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        actionBarDrawerToggle.syncState()
        binding.root.doOnLayout {
            binding.drawerScrollView.layoutParams.width = (it.width * 0.75).toInt()
        }
        Glide.with(this)
            .load(currentUserPref.getCurrentUser()?.avatar)
            .placeholder(R.drawable.placeholder)
            .transform(CenterCrop(),RoundedCorners(LayoutUtils.dpToPx(8f,requireContext()).toInt()))
            .into(binding.avatarImg)
        binding.usernameTxt.text = "Welcome, ${currentUserPref.getCurrentUser()?.username ?: "-"}"
        binding.homeBtn.setCompoundDrawablesWithIntrinsicBounds(
            AppCompatResources.getDrawable(requireContext(),R.drawable.ic_round_home_24)?.apply {
                DrawableCompat.setTint(this,Color.BLACK) }
            , null,null,null)
        binding.profileBtn.setCompoundDrawablesWithIntrinsicBounds(
            AppCompatResources.getDrawable(requireContext(),R.drawable.ic_round_person_24)?.apply {
                DrawableCompat.setTint(this,Color.BLACK) }
            , null,null,null)
        binding.homeBtn.setOnClickListener {
            binding.root.closeDrawer(GravityCompat.START)
            if(navController.currentDestination?.id == R.id.homeContentFragment) {
                return@setOnClickListener
            }
            navController.popBackStack(R.id.homeContentFragment,false)
        }
        binding.profileBtn.setOnClickListener {
            binding.root.closeDrawer(GravityCompat.START)
            if(navController.currentDestination?.id == R.id.profileFragment) {
                return@setOnClickListener
            }
            if(navController.currentDestination?.id != R.id.homeContentFragment) {
                navController.popBackStack(R.id.homeContentFragment,false)
            }
            navController.navigate(R.id.profileFragment)
        }
    }

    override fun backAction(): Boolean {
        if(binding.root.isDrawerVisible(GravityCompat.START)) {
            binding.root.closeDrawer(GravityCompat.START)
            return true
        }
        if(navController.currentDestination?.id != R.id.homeContentFragment) {
            navController.popBackStack()
            return true
        }
        return false
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        if(destination.id == R.id.homeContentFragment || destination.id == R.id.profileFragment) {
            binding.toolbar.title = getString(R.string.app_name)
            setToolbarNavigationMode(0)
        } else if(destination.id == R.id.detailFragment) {
            setToolbarNavigationMode(1)
            binding.toolbar.title = "Detail"
        }
    }

    // 0 = Show Hamburger Icon
    // 1 = Show Back Arrow Icon
    private fun setToolbarNavigationMode(mode:Int) {
        if(mode==0) {
            if(actionBarDrawerToggle.isDrawerIndicatorEnabled) return
            actionBarDrawerToggle.toolbarNavigationClickListener = null
            actionBarDrawerToggle.isDrawerIndicatorEnabled = true
            actionBarDrawerToggle.syncState()
        } else if(mode==1) {
            if(!actionBarDrawerToggle.isDrawerIndicatorEnabled) return
            actionBarDrawerToggle.isDrawerIndicatorEnabled = false
            actionBarDrawerToggle.setToolbarNavigationClickListener { navController.popBackStack() }
        }
    }
}