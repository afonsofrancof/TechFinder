package com.example.techfinder.utils

import com.example.techfinder.objects.LojaPreview
import com.example.techfinder.objects.User
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.time.LocalDateTime

class DbAPI {
    companion object {
        fun getUser(username: String): User? {
            val client = Socket("192.168.0.107", 8888)
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

        fun getLojasPreview(): MutableList<LojaPreview> {
            val client = Socket("192.168.0.107", 8888)
            val dos = DataOutputStream(client.getOutputStream())
            dos.writeUTF("getLojasPreview")
            dos.flush()
            val din = DataInputStream(client.getInputStream())
            val nlojas = din.readInt()
            val listLojas: MutableList<LojaPreview> = ArrayList()
            for (i in nlojas downTo 1) {
                val id = din.readUTF()
                val nome = din.readUTF()
                val localizacaoX = din.readFloat()
                val localizacaoY = din.readFloat()
                var horarioString = din.readUTF()
                val horarioAbertura = LocalDateTime.parse(horarioString)
                horarioString = din.readUTF()
                val horarioFecho = LocalDateTime.parse(horarioString)
                listLojas.add(
                    LojaPreview(
                        id,
                        nome,
                        localizacaoX,
                        localizacaoY,
                        horarioAbertura,
                        horarioFecho
                    )
                )
            }
            return listLojas
        }
    }
}