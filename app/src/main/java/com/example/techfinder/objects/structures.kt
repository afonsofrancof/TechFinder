package com.example.techfinder.objects

import java.time.LocalDateTime

data class User(
    var username: String = "",
    var nome: String = "",
    var email: String = "",
    var password: String = "",
    var morada: String = ""
)

data class LojaPreview(
    var id: String = "",
    var nome: String = "",
    var localizacaoX: Float,
    var localizacaoY: Float,
    var horarioAbertura: LocalDateTime? = null,
    var horarioFecho: LocalDateTime? = null
)

data class Loja(
    var id: String = "",
    var nome: String = "",
    var horarios: MutableList<Horario> = ArrayList(),
    var website: String? = null,
    var morada: String? = null,
    var email: String? = null,
    var telefone: String = ""
)

data class Horario(
    var horarioAbertura: LocalDateTime? = null,
    var horarioFecho: LocalDateTime? = null,
    var dia: Int

)