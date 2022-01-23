package com.example.techfinder.viewModels

import androidx.lifecycle.ViewModel
import com.example.techfinder.utils.DbAPI
import com.example.techfinder.utils.Extensions.Companion.getUser
import kotlinx.coroutines.runBlocking

class PerfilViewModel : ViewModel() {

    fun alterarPfp(path: String): String? =
        runBlocking {
            getUser()?.username?.let {
                return@runBlocking DbAPI.alterarPfp(
                    it,
                    path
                )
            }
        }
}
