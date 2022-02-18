package dev.illwiz.tada.presentation.ui

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseFragment:Fragment() {

    protected fun <T> observeFlow(flow: Flow<T>, observeDispatcher: CoroutineDispatcher? = null, observer: suspend (T) -> Unit) {
        lifecycleScope.launch(observeDispatcher ?: EmptyCoroutineContext) {
            flow
                .onEach {
                    /**
                     * Catch error on here not in the Flow using 'catch' operator it's too late if we catch it on there
                     * the stream would be terminated
                     */
                    try {
                        observer.invoke(it)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(requireContext(),"Observe Stream -> ${e.message ?: "Error"}",Toast.LENGTH_LONG).show()
                    }
                }
                .collect()
        }
    }

    open fun backAction():Boolean = false
}