package com.example.techfinder.utils


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.techfinder.objects.*
import java.io.*
import java.net.Socket
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalDateTime
import com.example.techfinder.activities.MainActivity
import java.io.File





class DbAPI {
    companion object {
        private const val host: String = "193.200.241.76"
        private const val port: Int = 8888

        /*
            Queries:
                -> autenticaUser 👌
                -> criarConta 👌
                -> checkUserName 👌
                -> alterarPassword 👌
                -> getComentarios 👌
                -> getFavoritos 👌
                -> alterVote 👌
                -> comentar 👌
                -> toggleFavorito 👌
                -> getLoja (tudo) - apenas uma loja 👌
                -> getLojaPreview (id, nome, local x y , horario a f) - todas as lojas 👌
                -> getCategorias 👌
        */

        fun autenticaUser(username: String, password: String): User? {
            val client = Socket(host, port)
            val dos = DataOutputStream(client.getOutputStream())
            val din = DataInputStream(client.getInputStream())

            dos.writeUTF("autenticaUser")
            dos.writeUTF(username)
            dos.writeUTF(password)
            dos.flush()

            val autenticado = din.readBoolean()
            var user: User? = null
            if (autenticado) {
                val nome = din.readUTF()
                val morada = din.readUTF()
                val email = din.readUTF()
                val pfpUrl: String?
                if(din.readBoolean()) {
                     pfpUrl = din.readUTF()
                }else{
                     pfpUrl = null
                }
                user = User(username, nome, email, password, morada, pfpUrl)
            }
            din.close()
            dos.close()
            client.close()
            return user
        }

        fun criarConta(
            username: String,
            password: String,
            nome: String,
            morada: String,
            email: String
        ): Boolean {
            val client = Socket(host, port)
            val dos = DataOutputStream(client.getOutputStream())
            val din = DataInputStream(client.getInputStream())

            dos.writeUTF("criarConta")
            dos.writeUTF(username)
            dos.writeUTF(password)
            dos.writeUTF(nome)
            dos.writeUTF(morada)
            dos.writeUTF(email)
            dos.flush()


            val usernameDisponivel = din.readBoolean()
            val emailDisponivel = din.readBoolean()

            dos.close()
            din.close()
            client.close()
            return usernameDisponivel && emailDisponivel
        }

        fun checkUserName(username: String): Boolean {
            val client = Socket(host, port)
            val dos = DataOutputStream(client.getOutputStream())
            val din = DataInputStream(client.getInputStream())

            dos.writeUTF("checkUserName")
            dos.writeUTF(username)
            dos.flush()


            val check = din.readBoolean()
            dos.close()
            din.close()
            client.close()
            return check
        }

        fun alterarPassword(username: String, oldPassword: String, newPassword: String): Boolean {
            val client = Socket(host, port)
            val dos = DataOutputStream(client.getOutputStream())
            val din = DataInputStream(client.getInputStream())

            dos.writeUTF("alterarPassword")
            dos.writeUTF(username)
            dos.writeUTF(oldPassword)
            dos.writeUTF(newPassword)
            dos.flush()


            val passIgual = din.readBoolean()
            val passCerta = din.readBoolean()
            dos.close()
            din.close()
            client.close()
            if (passIgual)
                throw PassIgualException("")
            if (!passCerta)
                throw PassErradaException("")
            return true
        }

        fun alterarPfp(username:String,path:String): String{
            val client = Socket(host, port)
            val dos = DataOutputStream(BufferedOutputStream(client.getOutputStream()))
            val din = DataInputStream(BufferedInputStream(client.getInputStream()))
            dos.writeUTF("alterarPfp")
            dos.writeUTF(username)


            val imagefile = File(path)
            val byteArray = imagefile.readBytes()
            dos.writeInt(byteArray.size)
            dos.writeUTF(imagefile.extension)
            dos.write(byteArray)
            dos.flush()

            val link = din.readUTF();
            din.close()
            dos.close()
            client.close()
            return link
        }

        fun getComentarios(username: String): MutableList<Comentario> {
            val client = Socket(host, port)
            val dos = DataOutputStream(BufferedOutputStream(client.getOutputStream()))
            val din = DataInputStream(BufferedInputStream(client.getInputStream()))

            dos.writeUTF("getComentarios")
            dos.writeUTF(username)
            dos.flush()


            val comentarios: MutableList<Comentario> = ArrayList()
            while (din.readBoolean()) {
                val idLoja = din.readUTF()
                val nomeLoja = din.readUTF()
                val texto = din.readUTF()
                val time = din.readLong()
                val data = Timestamp(time)
                val comentario = Comentario(idLoja,nomeLoja, username, texto, data)
                comentarios.add(comentario)
            }
            dos.close()
            din.close()
            client.close()
            return comentarios
        }

        /*
            'alter' pode ser:
                upvote (1)
                downvote (-1)
                remove (0)
        */
        fun alterVote(voto: TIPOVOTO, username: String, idLoja: String, categoria: String) {
            val client = Socket(host, port)
            val dos = DataOutputStream(BufferedOutputStream(client.getOutputStream()))

            dos.writeUTF("alterVote")
            dos.writeInt(voto.value)
            dos.writeUTF(username)
            dos.writeUTF(idLoja)
            dos.writeUTF(categoria)
            dos.flush()
            dos.close()
            client.close()
        }

        fun comentar(username: String, idLoja: String, texto: String) {
            val client = Socket(host, port)
            val dos = DataOutputStream(BufferedOutputStream(client.getOutputStream()))

            dos.writeUTF("comentar")
            dos.writeUTF(username)
            dos.writeUTF(idLoja)
            dos.writeUTF(texto)
            dos.writeLong(System.currentTimeMillis())
            dos.flush()
            dos.close()
            client.close()
        }

        fun getFavorito() {

        }

        // Action: true -> add / false -> remove
        fun toggleFavorito(action: Boolean, username: String, idLoja: String) {
            val client = Socket(host, port)
            val dos = DataOutputStream(BufferedOutputStream(client.getOutputStream()))

            dos.writeUTF("toggleFavorito")
            dos.writeBoolean(action)
            dos.writeUTF(username)
            dos.writeUTF(idLoja)
            dos.flush()
            dos.close()
            client.close()
        }

        fun getLojasPreview(username: String): MutableList<LojaPreview> {
            val client = Socket(host, port)
            val dos = DataOutputStream(BufferedOutputStream(client.getOutputStream()))
            val din = DataInputStream(BufferedInputStream(client.getInputStream()))
            val dia = LocalDateTime.now().dayOfWeek.value

            dos.writeUTF("getLojasPreview")
            dos.writeUTF(username)
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
                val fav = din.readBoolean()
                val horario = Horario(dia, horarioAbertura, horarioFecho)
                val listCategorias: MutableList<Categoria> = ArrayList()
                while (din.readBoolean()) {
                    val nomeCategoria = din.readUTF()
                    listCategorias.add(Categoria(nomeCategoria,0,TIPOVOTO.NOVOTE))
                }
                listLojas.add(
                    LojaPreview(
                        id,
                        nome,
                        coordenadas,
                        horario,
                        listCategorias,
                        fav
                    )
                )
            }
            din.close()
            client.close()
            return listLojas
        }

        fun getLoja(username: String, idLoja: String): Loja {
            val client = Socket(host, port)
            val dos = DataOutputStream(BufferedOutputStream(client.getOutputStream()))
            val din = DataInputStream(BufferedInputStream(client.getInputStream()))

            dos.writeUTF("getLoja")
            dos.writeUTF(username)
            dos.writeUTF(idLoja)
            dos.flush()


            val nome = din.readUTF()
            val website = din.readUTF()
            val email = din.readUTF()
            val telefone = din.readUTF()
            val coordX = din.readFloat()
            val coordY = din.readFloat()
            val fav = din.readBoolean()


            val coordenadas = Coordenadas(coordX, coordY)

            // lista de horarios
            val horarios: MutableList<Horario> = ArrayList()
            while (din.readBoolean()) {
                val diaSemana = din.readInt()
                var horarioString = din.readUTF()
                val horarioAbertura = Time.valueOf(horarioString)
                horarioString = din.readUTF()
                val horarioFecho = Time.valueOf(horarioString)
                val horario = Horario(diaSemana, horarioAbertura, horarioFecho)
                horarios.add(horario)
            }

            // Categorias e repetivos votos
            val categorias: MutableList<Categoria> = ArrayList()
            while (din.readBoolean()) {
                val nomeCategoria = din.readUTF()
                val votos = din.readInt()
                val tipoVotoInt =
                    din.readInt() // Fazer com que servidor envie o tipo de voto que o user fez nesta loja
                val tipoVoto = TIPOVOTO.fromInt(tipoVotoInt)
                val categoria = Categoria(nomeCategoria, votos, tipoVoto)
                categorias.add(categoria)
            }
            categorias.sortBy { it.voto }

            // Comentarios
            val comentarios: MutableList<Comentario> = ArrayList()
            while (din.readBoolean()) {
                val usernameC = din.readUTF()
                val texto = din.readUTF()
                val time = din.readLong()
                val data = Timestamp(time)
                val comentario = Comentario(idLoja,nome, usernameC, texto, data)
                comentarios.add(comentario)
            }
            dos.close()
            din.close()
            client.close()
            return Loja(
                idLoja,
                nome,
                coordenadas,
                horarios,
                comentarios,
                categorias,
                email,
                website,
                telefone,
                fav
            )
        }

        // provavelmente tem de ser mudado
        fun getCategorias(): MutableList<Categoria> {
            val client = Socket(host, port)
            val dos = DataOutputStream(BufferedOutputStream(client.getOutputStream()))
            val din = DataInputStream(BufferedInputStream(client.getInputStream()))

            dos.writeUTF("getCategorias")
            dos.flush()
            val listCategorias: MutableList<Categoria> = ArrayList()

            while (din.readBoolean()) {
                val nomeCategoria = din.readUTF()
                listCategorias.add(Categoria(nomeCategoria, 0, TIPOVOTO.NOVOTE))
            }
            din.close()
            dos.close()
            client.close()
            return listCategorias
        }

        fun getPfp(username:String):String?{
            val client = Socket(host, port)
            val dos = DataOutputStream(BufferedOutputStream(client.getOutputStream()))
            val din = DataInputStream(BufferedInputStream(client.getInputStream()))

            dos.writeUTF("getPfp")
            dos.writeUTF(username)
            dos.flush()

            var url : String? = null
            if(din.readBoolean()) url = din.readUTF()
            dos.close()
            din.close()
            client.close()
            return url
        }
    }
}