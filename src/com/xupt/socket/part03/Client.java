package com.xupt.socket.part03;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket s= null;
        try {
            s = new Socket("127.0.0.1",9123);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendThread(s);
        receiveThread(s);
    }
    public static void sendThread(Socket s){
        new Thread(){
          @Override
          public void run(){
              while(true) {
                  try {
                      OutputStream os = s.getOutputStream();
                      DataOutputStream dos = new DataOutputStream(os);
                      Scanner sc = new Scanner(System.in);
                      String msg = sc.next();
                      dos.writeUTF(msg);
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
          }
        }.start();
    }
    public static void receiveThread(Socket s){
        new Thread(){
            @Override
            public void run(){
                while (true){
                    try {
                        InputStream is = s.getInputStream();
                        DataInputStream dis = new DataInputStream(is);
                        String msg=dis.readUTF();
                        System.out.println("收到机器人发送的信息"+msg);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
