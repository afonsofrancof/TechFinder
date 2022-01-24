package com.example.techfinder.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techfinder.objects.LojaPreview
import com.example.techfinder.utils.DbAPI
import com.example.techfinder.utils.Extensions.Companion.getUser
import com.example.techfinder.utils.Extensions.Companion.isLojaOpen
import java.util.*
import kotlin.Comparator


class ShopsFeedViewModel : ViewModel() {
    var lojaLista = MutableLiveData<List<LojaPreview>>()

    fun getLojasPreview() {
        lojaLista.value = getUser()?.username?.let { DbAPI.getLojasPreview(it) }
    }

    fun sortBoolean(){
        var loja1Open: Boolean = false
        var loja2Open: Boolean = false
        Collections.sort(
            lojaLista.value,
            Comparator { loja1, loja2 ->
                loja1.horario?.horarioAbertura?.let { horarioAberturaLoja1 ->
                    loja1.horario!!.horarioFecho?.let { horarioFechoLoja1 ->
                        loja1Open = isLojaOpen(
                            horarioAberturaLoja1,
                            horarioFechoLoja1
                        )

                        loja2.horario?.horarioAbertura?.let { horarioAberturaLoja2 ->
                            loja2.horario!!.horarioFecho?.let { horarioFechoLoja2 ->
                                loja2Open = isLojaOpen(
                                    horarioAberturaLoja2,
                                    horarioFechoLoja2
                                )
                                loja2Open.compareTo(loja1Open)
                            }

                        }
                    }
                }!!

            })
    }

}