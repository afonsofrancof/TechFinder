package com.example.techfinder.objects
import java.io.Serializable
import java.time.LocalDateTime

data class User(
        var username: String ="",
        var nome: String ="",
        var email: String = "",
        var password: String = "",
        var morada: String = ""
):Serializable

data class LojaPreview(
        var id : String ="",
        var nome : String ="",
        var localizacaoX : Float,
        var localizacaoY : Float,
        var horarioAbertura : LocalDateTime? = null,
        var horarioFecho : LocalDateTime?  = null
):Serializable

data class Loja (
        var id : String ="",
        var nome : String ="",
        var horarioAbertura : LocalDateTime? = null,
        var horarioFecho : LocalDateTime?  = null,
        var website : String? = null,
        var morada : String? = null,
        var email : String? = null,
        var telefone : String = ""
):Serializable