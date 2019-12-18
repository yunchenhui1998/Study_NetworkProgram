package com.xupt.socket.part02;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket s=new Socket("127.0.0.1",8888);
            OutputStream os=s.getOutputStream();
            DataOutputStream dos=new DataOutputStream(os);
            Scanner in=new Scanner(System.in);
            String msg=in.next();
            dos.writeUTF(msg);
            in.close();
            dos.close();
            os.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
