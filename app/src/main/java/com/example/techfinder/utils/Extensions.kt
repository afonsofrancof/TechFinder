package com.example.techfinder.utils

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.techfinder.activities.LoginActivity
import com.example.techfinder.objects.User
import com.google.gson.Gson

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

    }
}