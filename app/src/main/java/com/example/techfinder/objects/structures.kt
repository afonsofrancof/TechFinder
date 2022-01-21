package com.example.techfinder.objects

import java.sql.Time
import java.sql.Timestamp

data class User(
    var username: String = "",
    var nome: String = "",
    var email: String = "",
    var password: String = "",
    var morada: String = "",
    var pfpUrl: String? = ""
)


data class LojaPreview(
    var id: String = "",
    var nome: String = "",
    var coordenadas: Coordenadas,
    var horario: Horario
)

data class Loja(
    var id: String = "",
    var nome: String = "",
    var coordenadas: Coordenadas,
    var horarios: MutableList<Horario> = ArrayList(),
    var email: String? = null,
    var website: String? = null,
    var telefone: String = ""
)

data class Horario(
    var dia: Int,
    var horarioAbertura: Time? = null,
    var horarioFecho: Time? = null
)

data class Coordenadas(
    var x: Float,
    var y: Float
)

data class Comentario(
    var idLoja: String?,
    var username: String?,
    var texto: String,
    var data: Timestamp
)

data class Categoria(
    var nomeCategoria: String = ""
)