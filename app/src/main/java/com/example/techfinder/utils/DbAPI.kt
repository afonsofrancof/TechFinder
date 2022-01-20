package com.example.techfinder.utils

import com.example.techfinder.objects.LojaPreview
import com.example.techfinder.objects.User
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class DbAPI {
    private var client: Socket

    init {
        client = Socket("192.168.0.107", 8888)
    }

    fun getUser(username: String): User? {

        val dos = DataOutputStream(client.getOutputStream())
        dos.writeUTF("getUser")
        dos.writeUTF(username)
        dos.flush();
        val din = DataInputStream(client.getInputStream())
        val username: String = din.readUTF();
        val nome: String = din.readUTF();
        val password: String = din.readUTF();
        val morada: String = din.readUTF();
        val email: String = din.readUTF();
        var user = User(username, nome, email, password, morada)
        return user
    }

    fun getLojasPreview() {
        val dos = DataOutputStream(client.getOutputStream())
        dos.writeUTF("getLojasPreview")
        dos.flush()
        val din = DataInputStream(client.getInputStream())
        val nlojas = din.readInt()
        var listLojas: MutableList<LojaPreview> = ArrayList()
        for (i in nlojas downTo 1) {
            var id = din.readUTF()
            var nome = din.readUTF()
            var localizacaoX = din.readFloat()
            var localizacaoY = din.readFloat()
            // horario abertura
            // horario fecho
            listLojas.add(LojaPreview())
        }
    }
}