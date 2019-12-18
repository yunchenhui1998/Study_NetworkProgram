package com.xupt.socket.part02;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket ss=new ServerSocket(8888);
            System.out.println("监听端口在8888");
            Socket s=ss.accept();
            InputStream in=s.getInputStream();
            DataInputStream dis=new DataInputStream(in);
            String msg=dis.readUTF();
            System.out.println(msg);
            dis.close();
            in.close();
            s.close();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
