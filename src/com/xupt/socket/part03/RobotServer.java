package com.xupt.socket.part03;

import com.xupt.socket.part02.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RobotServer {
    public static void main(String[] args) {
        ServerSocket ss= null;
        try {
            ss = new ServerSocket(9123);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("等待客户端连接中");
        Socket s= null;
        try {
            s = ss.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("客户端连接成功");
        receiveThread(s);
    }
    public static void sendMsg(Socket s,String str){
        try{
            OutputStream os=s.getOutputStream();
            DataOutputStream dos=new DataOutputStream(os);
            dos.writeUTF(str);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public static void receiveThread(Socket s){
        new Thread(){
            @Override
            public void run(){
                while(true){
                    RobotDAO r=new RobotDAO();
                    String msg=null;
                    try {
                        InputStream is = s.getInputStream();
                        DataInputStream dis = new DataInputStream(is);
                        msg=dis.readUTF();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    if(r.getResponseNumber(msg)==0){
                        sendMsg(s,new String("人家听不懂你说的话!"));
                    }else{
                        sendMsg(s,r.getResponse(msg));
                    }
                }
            }
        }.start();
    }
}
