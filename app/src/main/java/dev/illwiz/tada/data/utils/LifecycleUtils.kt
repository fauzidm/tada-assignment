package dev.illwiz.tada.data.utils

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * https://gist.github.com/serhii-pokrovskyi/f9908ed6cb167a4572eff1c1f08e461b
 */
fun <T> LifecycleOwner.onDestroyNullable(initialise: () -> T): ReadWriteProperty<LifecycleOwner, T> =
    object : ReadWriteProperty<LifecycleOwner, T>, DefaultLifecycleObserver {
        private var value: T? = null

        init {
            this@onDestroyNullable
                .lifecycle
                .addObserver(this)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            value = null
            this@onDestroyNullable
                .lifecycle
                .removeObserver(this)
            super.onDestroy(owner)
        }

        override fun setValue(thisRef: LifecycleOwner, property: KProperty<*>, value: T) {
            this.value = value
        }

        override fun getValue(thisRef: LifecycleOwner, property: KProperty<*>): T {
            return value ?: initialise().also {
                value = it
            }
        }
    }