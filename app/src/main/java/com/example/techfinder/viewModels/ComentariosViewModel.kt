package com.example.techfinder.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techfinder.objects.Comentario
import com.example.techfinder.objects.LojaPreview
import com.example.techfinder.utils.DbAPI
import com.example.techfinder.utils.Extensions.Companion.getUser

class ComentariosViewModel : ViewModel() {
    var comentsLista = MutableLiveData<List<Comentario>>()

    fun getComentarios() {
        comentsLista.value = getUser()?.username?.let { DbAPI.getComentarios(it) } //TODO GET USER
    }

}