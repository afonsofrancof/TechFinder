package com.example.techfinder.objects
import java.io.Serializable

data class User(
        var username: String ="",
        var nome: String ="",
        var email: String = "",
        var password: String = "",
        var morada: String = ""
):Serializable

data class LojaPreview(
        var id : String ="",
        var nome : String =""
):Serializable