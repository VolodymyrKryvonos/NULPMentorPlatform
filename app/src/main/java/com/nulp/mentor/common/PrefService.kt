package com.nulp.mentor.common

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.nulp.mentor.common.PrefsString.LOGGED
import com.nulp.mentor.common.PrefsString.USER
import com.nulp.mentor.domain.model.User

object PrefsString{
    const val USER = "user"
    const val LOGGED = "logged"
}

class PrefService(context: Context) {

    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    fun storeUser(user: User){
        Gson().toJson(user)
        with(pref.edit()){
            putString(USER, Gson().toJson(user))
            putBoolean(PrefsString.LOGGED, true)
            apply()
        }
    }

    fun logout(){
        with(pref.edit()){
            remove(USER)
            remove(LOGGED)
            apply()
        }
    }

    fun getUser():User?{
        return Gson().fromJson(pref.getString(USER,""),User::class.java)
    }

    fun isLoggedIn() = pref.getBoolean(LOGGED,false)
}