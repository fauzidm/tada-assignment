package dev.illwiz.tada

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.annotation.StyleRes
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity
import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.rules.ExternalResource

/**
 * Android Navigation Hilt Testing: https://github.com/google/dagger/issues/1953#issuecomment-722895455
 * https://github.com/android/architecture-samples/blob/dev-hilt/app/src/androidTest/java/com/example/android/architecture/blueprints/todoapp/HiltExt.kt
 * launchFragmentInContainer from the androidx.fragment:fragment-testing library
 * is NOT possible to use right now as it uses a hardcoded Activity under the hood
 * (i.e. [EmptyFragmentActivity]) which is not annotated with @AndroidEntryPoint.
 *
 * As a workaround, use this function that is equivalent. It requires you to add
 * [HiltTestActivity] in the debug folder and include it in the debug AndroidManifest.xml file
 * as can be found in this project.
 */
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    @StyleRes themeResId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
    navHostController: NavHostController? = null,
    fragmentFactory: FragmentFactory? = null,
    fragmentArg: Fragment? = null,
    crossinline action: T.() -> Unit = {}
): ActivityScenario<HiltTestActivity> {
    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    ).putExtra(EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY, themeResId)
    return ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
        fragmentFactory?.let {
            activity.supportFragmentManager.fragmentFactory = it
        }
        val fragment: Fragment = fragmentArg
            ?: activity.supportFragmentManager.fragmentFactory.instantiate(
                Preconditions.checkNotNull(T::class.java.classLoader),
                T::class.java.name
            )
        fragment.arguments = fragmentArgs
        navHostController?.let {
            fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    Navigation.setViewNavController(fragment.requireView(), it)
                }
            }
        }
        activity.supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()
        (fragment as T).action()
    }
}

class TestNavHostControllerRule(
    @NavigationRes private val navigationGraph: Int,
    @IdRes private val currentDestination: Int,
    private val viewModelStore: ViewModelStore = ViewModelStore()
) : ExternalResource() {
    lateinit var testNavHostController: TestNavHostController

    override fun before() = InstrumentationRegistry.getInstrumentation().runOnMainSync {
        super.before()
        // Before test
        testNavHostController = TestNavHostController(ApplicationProvider.getApplicationContext())
        testNavHostController.setViewModelStore(viewModelStore)
        val graph = testNavHostController.navInflater.inflate(navigationGraph)
        graph.startDestination = currentDestination
        testNavHostController.graph = graph
        testNavHostController.setCurrentDestination(currentDestination)
    }
}