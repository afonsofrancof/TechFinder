package com.example.techfinder.viewModels

import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.techfinder.activities.LoginActivity
import com.example.techfinder.activities.MainActivity
import com.example.techfinder.objects.User
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {

    fun autenticaUser(username: String, password: String, activity: LoginActivity) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = com.example.techfinder.utils.DbAPI.autenticaUser(
                username,
                password
            )
            if (user != null) {
                val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

                val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
                    "trabalholi",
                    masterKeyAlias,
                    activity,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )

                val editor = sharedPreferences.edit()
                val gson = Gson()
                val json = gson.toJson(user)
                editor.putString("user", json)
                editor.apply()

                activity.startActivity(Intent(activity, MainActivity::class.java))
                activity.finish()
            }
        }

    }
}