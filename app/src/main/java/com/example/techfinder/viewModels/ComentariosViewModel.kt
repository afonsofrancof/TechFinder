package com.example.techfinder.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techfinder.objects.Comentario
import com.example.techfinder.objects.LojaPreview
import com.example.techfinder.utils.DbAPI

class ComentariosViewModel : ViewModel() {
    var comentsLista = MutableLiveData<List<Comentario>>()

    fun getComentarios() {
        comentsLista.value = DbAPI.getComentarios("username1") //TODO GET USER
    }

}