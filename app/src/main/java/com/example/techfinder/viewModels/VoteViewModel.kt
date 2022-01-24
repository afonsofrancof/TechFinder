package com.example.techfinder.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techfinder.objects.Categoria
import com.example.techfinder.objects.TIPOVOTO
import com.example.techfinder.utils.DbAPI
import com.example.techfinder.utils.Extensions.Companion.getUser
import java.util.stream.Collectors

class VoteViewModel : ViewModel() {

    var listaCategorias = MutableLiveData<MutableList<Categoria>>()

    fun alterVote(tipoVoto: TIPOVOTO, categoria: Categoria, idLoja: String) {
        getUser()?.username?.let { DbAPI.alterVote(tipoVoto, it, idLoja, categoria.nomeCategoria) }
    }

    fun getLoja(idLoja: String) {
        listaCategorias.value = getUser()?.username?.let { DbAPI.getLoja(it, idLoja).categorias }
    }

    fun getCategorias() {
        val listaGetCategoriasString = DbAPI.getCategorias()
        val listaGetCategorias = listaGetCategoriasString.stream()
            .map { cat -> Categoria(cat.nomeCategoria, 0, TIPOVOTO.NOVOTE) }.collect(
                Collectors.toList()
            )
        listaCategorias.value?.let {
            listaGetCategorias.removeAll { cat ->
                it.stream().anyMatch { cat.nomeCategoria == it.nomeCategoria }
            }
        }
        listaCategorias.value?.addAll(listaGetCategorias)
    }

    fun atualizaCategoria(categoria: Categoria, tipoVoto: TIPOVOTO) {
        categoria.tipoVoto = tipoVoto
        val newList = listaCategorias.value!!.toMutableList()
        newList.removeIf { it.nomeCategoria == categoria.nomeCategoria }
        if (tipoVoto == TIPOVOTO.NOVOTE) {
            newList.add(categoria)
        } else newList.add(0, categoria)
        listaCategorias.value = newList
    }


}