package com.example.techfinder.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.techfinder.activities.LoginActivity
import com.example.techfinder.objects.User
import com.google.gson.Gson

class LoginViewModel : ViewModel() {

    fun saveUser(user:User,activity: LoginActivity){
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
    }

}