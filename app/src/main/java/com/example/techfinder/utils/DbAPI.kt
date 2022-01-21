package com.example.techfinder.utils

import com.example.techfinder.objects.*
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
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
        fun autenticaUser(username: String, password: String): User? {
            val client = Socket("192.168.0.107", 8888)
            val dos = DataOutputStream(client.getOutputStream())
            val din = DataInputStream(client.getInputStream())
            dos.writeUTF("autenticaUser")
            dos.writeUTF(username)
            dos.writeUTF(password)
            dos.flush();
            dos.close()
            val autenticado = din.readBoolean();
            var user: User? = null
            if (autenticado) {
                val username: String = din.readUTF()
                val nome: String = din.readUTF()
                val password: String = din.readUTF()
                val morada: String = din.readUTF()
                val email: String = din.readUTF()
                user = User(username, nome, email, password, morada)
            }
            din.close()
            return user
        }

        fun criarConta(
            username: String,
            password: String,
            nome: String,
            morada: String,
            email: String
        ): Boolean {
            val client = Socket("192.168.0.107", 8888)
            val dos = DataOutputStream(client.getOutputStream())
            val din = DataInputStream(client.getInputStream())
            dos.writeUTF("criarConta")
            dos.writeUTF(username)
            dos.writeUTF(password)
            dos.writeUTF(nome)
            dos.writeUTF(morada)
            dos.writeUTF(email)
            dos.flush()
            dos.close()

            val usernameDisponivel = din.readBoolean()
            val emailDisponivel = din.readBoolean()
            din.close()
            client.close()
            return usernameDisponivel && emailDisponivel
        }

        fun getLojasPreview(): MutableList<LojaPreview> {
            val client = Socket("10.0.2.2", 8888)
            val dos = DataOutputStream(BufferedOutputStream(client.getOutputStream()))
            val din = DataInputStream(BufferedInputStream(client.getInputStream()))
            val dia = LocalDateTime.now().dayOfWeek.value;

            dos.writeUTF("getLojasPreview")
            dos.writeInt(dia)
            dos.flush()

            val listLojas: MutableList<LojaPreview> = ArrayList()
            while (din.readBoolean()) {
                val id = din.readUTF()
                val nome = din.readUTF()
                val coordX = din.readFloat()
                val coordY = din.readFloat()
                val coordenadas = Coordenadas(coordX, coordY)
                var horarioString = din.readUTF()
                val horarioAbertura = Time.valueOf(horarioString)
                horarioString = din.readUTF()
                val horarioFecho = Time.valueOf(horarioString)
                val horario = Horario(dia, horarioAbertura, horarioFecho)
                listLojas.add(
                    LojaPreview(
                        id,
                        nome,
                        coordenadas,
                        horario
                    )
                )
            }
            din.close()
            client.close()
            return listLojas
        }

        // Nao acabado🤏
        fun getLoja(idLoja: String): Loja {
            val client = Socket("10.0.2.2", 8888)
            val dos = DataOutputStream(BufferedOutputStream(client.getOutputStream()))
            val din = DataInputStream(BufferedInputStream(client.getInputStream()))

            dos.writeUTF("getLoja")
            dos.writeUTF(idLoja)
            dos.flush()
            dos.close()

            val id = din.readUTF();
            val nome = din.readUTF()
            val website = din.readUTF()
            val email = din.readUTF()
            val telefone = din.readUTF()
            val coordX = din.readFloat()
            val coordY = din.readFloat()

            val coordenadas = Coordenadas(coordX, coordY)

            val horarios: MutableList<Horario> = ArrayList()
            while (din.readBoolean()) {
                val diaSemana = din.readInt();
                var horarioString = din.readUTF()
                val horarioAbertura = Time.valueOf(horarioString)
                horarioString = din.readUTF()
                val horarioFecho = Time.valueOf(horarioString)
                val horario = Horario(diaSemana, horarioAbertura, horarioFecho)
                horarios.add(horario)
            }
            din.close()
            client.close()

            return Loja(
                id, nome, coordenadas, horarios, email, website, telefone
            )
        }


        fun getCategorias(): MutableList<Categoria> {
            val client = Socket("10.0.2.2", 8888)
            val dos = DataOutputStream(BufferedOutputStream(client.getOutputStream()))
            val din = DataInputStream(BufferedInputStream(client.getInputStream()))

            dos.writeUTF("getCategorias")
            dos.flush()
            val listCategorias: MutableList<Categoria> = ArrayList()

            while (true) {
                if (!din.readBoolean()) break
                val nomeCategoria = din.readUTF();
                listCategorias.add(Categoria(nomeCategoria))
            }

            return listCategorias
        }
    }
}