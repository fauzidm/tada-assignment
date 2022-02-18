package dev.illwiz.tada.data.pref

import android.content.Context
import android.content.SharedPreferences

abstract class BasePreference protected constructor(context: Context) {
    protected val pref: SharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
}