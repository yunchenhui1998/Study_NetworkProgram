package com.xupt.socket.part02;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientPractice01 {
    Socket socket=null;
    InputStream is=null;
    OutputStream os=null;
    DataInputStream dis=null;
    DataOutputStream dos=null;
    public ClientPractice01(){
        init();
    }
    private void init(){
        try {
            socket=new Socket("127.0.0.1",9612);
            is=socket.getInputStream();
            dis=new DataInputStream(is);
            os=socket.getOutputStream();
            dos=new DataOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void listenThread(){
        Thread t=new Thread(){
            @Override
            public void run(){
                while(true){
                    String msg= null;
                    try {
                        msg = dis.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("收到服务端信息"+msg);
                }
            }
        };
        t.start();
    }
    public void sendThread(){
        Thread t=new Thread(){
            @Override
            public void run(){
                while(true){
                    Scanner in=new Scanner(System.in);
                    String msg=in.next();
                    try {
                        dos.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    public static void main(String[] args) {
        ClientPractice01 c=new ClientPractice01();
        c.sendThread();
        c.listenThread();
    }
}
