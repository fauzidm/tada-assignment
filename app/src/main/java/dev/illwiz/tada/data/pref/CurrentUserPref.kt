package dev.illwiz.tada.data.pref

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dev.illwiz.tada.domain.user.User

@SuppressLint("ApplySharedPref")
class CurrentUserPref(
    context: Context,
    private val gson: Gson
):BasePreference(context) {
    companion object {
        private const val CURRENT_USER_PREF_KEY = "CURRENT_USER_PREF_KEY"
    }

    // Get current authenticated User
    fun getCurrentUser():User? {
        return try {
            val json = pref.getString(CURRENT_USER_PREF_KEY,null)
            val userModel = gson.fromJson(json, User::class.java)
            userModel
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }

    // Set current authenticated User
    fun setCurrentUser(value: User?) {
        if(value==null) {
            pref.edit().remove(CURRENT_USER_PREF_KEY).commit()
        } else {
            val json = gson.toJson(value)
            pref.edit().putString(CURRENT_USER_PREF_KEY,json).commit()
        }
    }

    fun isAuthenticated():Boolean {
        return getCurrentUser()!=null
    }

    fun clear() {
        pref.edit().remove(CURRENT_USER_PREF_KEY).commit()
    }
}