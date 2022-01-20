package com.example.techfinder.utils

import User
import com.example.techfinder.objects.LojaPreview
import com.example.techfinder.objects.User
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.ObjectInputStream
import java.net.Socket

class DbAPI {
    private var client : Socket
    init {
        client = Socket("192.168.0.107", 8888)
    }

    fun getUser(username: String) : User? {

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
        var user : User = User(username,nome,email,password,morada)
        client.close()
        if(user!=null){return user}else{return null}

    }

    fun getLojasPreview(){
        val dos = DataOutputStream(client.getOutputStream())
        dos.writeUTF("getLojasPreview")
        dos.flush()
        val din = DataInputStream(client.getInputStream())
        val nlojas = din.readInt()
        var listLojas : List<LojaPreview> = ArrayList()
        for(i in nlojas downTo 1){

        }
    }
}