package com.example.techfinder.utils

import com.example.techfinder.objects.Categoria
import com.example.techfinder.objects.LojaPreview
import com.example.techfinder.objects.User
import java.io.*
import java.net.Socket
import java.sql.Time
import java.time.LocalDateTime

class DbAPI {
    companion object {

        /*
            Queries:

                -> getUser (tudo)
                -> autenticaUser
                -> criarConta
                -> checkUserName
                -> alterarPassword
                -> getLoja (tudo) - apenas uma loja
                -> getLojaPreview (id, nome, local x y , horario a f) - todas as lojas
                -> getCategorias

             */

        fun getUser(username: String): User? {
            val client = Socket("10.0.2.2", 8888)
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
            val client = Socket("10.0.2.2", 8888)
            val dos = DataOutputStream(BufferedOutputStream(client.getOutputStream()))
            val din = DataInputStream(BufferedInputStream(client.getInputStream()))
            dos.writeUTF("getLojasPreview")
            dos.writeInt(LocalDateTime.now().dayOfWeek.value)
            dos.flush()
            val listLojas: MutableList<LojaPreview> = ArrayList()
            while(true) {
                if(!din.readBoolean()) break
                val id = din.readUTF()
                val nome = din.readUTF()
                val localizacaoX = din.readFloat()
                val localizacaoY = din.readFloat()
                var horarioString = din.readUTF()
                val horarioAbertura = Time.valueOf(horarioString)
                horarioString = din.readUTF()
                val horarioFecho = Time.valueOf(horarioString)
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

        fun getCategorias(): MutableList<Categoria>{

            val client = Socket("10.0.2.2", 8888)
            val dos = DataOutputStream(BufferedOutputStream(client.getOutputStream()))
            val din = DataInputStream(BufferedInputStream(client.getInputStream()))
            dos.writeUTF("getLojasPreview")
            dos.flush()
            val listCategorias: MutableList<Categoria> = ArrayList()

            while(true){
                if(!din.readBoolean()) break
                val nomeCategoria = din.readUTF();
                listCategorias.add(Categoria(nomeCategoria))
            }

            return listCategorias
        }

    }
}