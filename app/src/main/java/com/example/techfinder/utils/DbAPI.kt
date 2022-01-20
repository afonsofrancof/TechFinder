package com.example.techfinder.utils

import User
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
        System.out.flush()
        val din = DataInputStream(client.getInputStream())
        val o = ObjectInputStream(din)
        var u = o.readObject() as User
        client.close()
        if(u!=null){return u}else{return null}


    }
}