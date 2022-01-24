package com.example.techfinder.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techfinder.objects.Categoria
import com.example.techfinder.objects.Comentario
import com.example.techfinder.objects.Loja
import com.example.techfinder.objects.LojaPreview
import com.example.techfinder.utils.DbAPI
import com.example.techfinder.utils.Extensions.Companion.getUser

class LojaInfoViewModel : ViewModel() {

    var listaCategoria = MutableLiveData<List<Categoria>>()

    var listaComentarios = MutableLiveData<MutableList<Comentario>>()

    fun getLoja(idLoja:String): Loja? {
        return getUser()?.let { DbAPI.getLoja(it.username,idLoja) }
    }
    fun fav(idLoja:String,futuroEstado :Boolean){
        getUser()?.let {DbAPI.toggleFavorito(futuroEstado, it.username,idLoja)}
    }

    fun criarComentario(comentario: Comentario){
        getUser()?.let { DbAPI.comentar(it.username,comentario.idLoja!!,comentario.texto) }
        val list = listaComentarios.value
        list?.let {
            it.add(comentario)
            listaComentarios.value = it
        }
    }
}