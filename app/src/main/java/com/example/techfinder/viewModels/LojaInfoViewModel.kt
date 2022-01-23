package com.example.techfinder.viewModels

import androidx.lifecycle.ViewModel
import com.example.techfinder.objects.Loja
import com.example.techfinder.utils.DbAPI
import com.example.techfinder.utils.Extensions.Companion.getUser

class LojaInfoViewModel : ViewModel() {

    fun getLoja(idLoja:String): Loja? {
        return getUser()?.let { DbAPI.getLoja(it.username,idLoja) }
    }
}