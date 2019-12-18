package com.xupt.socket.part02;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerPractice01 {
    ServerSocket ss=null;
    Socket socket=null;
    InputStream is=null;
    OutputStream os=null;
    DataInputStream dis=null;
    DataOutputStream dos=null;
    public ServerPractice01(){
        init();
    }
    private void init(){
        System.out.println("服务器运行中");
        try {
            ServerSocket ss=new ServerSocket(9612);
            System.out.println("服务器等待连接");
            socket =ss.accept();
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
                    System.out.println("收到客户端信息"+msg);
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
        ServerPractice01 s=new ServerPractice01();
        System.out.println("连接成功");
        s.sendThread();
        s.listenThread();
    }
}
