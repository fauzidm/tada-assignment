package dev.illwiz.tada.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.illwiz.tada.R
import dev.illwiz.tada.data.pref.CurrentUserPref
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var currentUserPref: CurrentUserPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph_main)
        if(currentUserPref.isAuthenticated()) {
            graph.startDestination = R.id.homeFragment
        } else {
            graph.startDestination = R.id.loginFragment
        }
        navHostFragment.navController.graph = graph
    }

    override fun onBackPressed() {
        val fragmentInContainer = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if(fragmentInContainer is NavHostFragment) {
            val fragment = fragmentInContainer.childFragmentManager.fragments[0]
            if(fragment is BaseFragment && fragment.backAction()) {
                return
            }
        }
        super.onBackPressed()
    }
}