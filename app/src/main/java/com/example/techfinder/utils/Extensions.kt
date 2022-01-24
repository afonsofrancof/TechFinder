package com.example.techfinder.utils

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.techfinder.R
import com.example.techfinder.activities.LoginActivity
import com.example.techfinder.objects.User
import com.google.gson.Gson
import java.sql.Time
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import android.view.View

import android.app.Activity

import android.view.inputmethod.InputMethodManager




class Extensions {
    companion object{
        fun Any.getUser(): User?{
            val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

            val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
                "trabalholi",
                masterKeyAlias,
                MyApplication.appContext,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            if(!sharedPreferences.contains("user")) return null;
            val gson = Gson()
            val userJson = sharedPreferences.getString("user","")
            return gson.fromJson(userJson, User::class.java)
        }
        fun Any.setUser(user: User){
            val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

            val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
                "trabalholi",
                masterKeyAlias,
                MyApplication.appContext,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            val prefsEditor: SharedPreferences.Editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(user)
            prefsEditor.putString("user", json)
            prefsEditor.apply()
        }

        fun Any.isLojaOpen(abertura:Time ,fecho:Time):Boolean{
            val atual = LocalTime.parse(Time(System.currentTimeMillis()).toString())
            Log.i("DEGUBMANOS",fecho.toString())
            return !(atual.isBefore(LocalTime.parse(abertura.toString())) ||
                    atual.isAfter(LocalTime.parse(fecho.toString())))
        }

        fun Any.dayOfWeek():Int = DayOfWeek.from(LocalDate.now()).value

        fun Any.hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }


    }
}