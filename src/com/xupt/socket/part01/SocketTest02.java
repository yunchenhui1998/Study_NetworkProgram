package com.xupt.socket.part01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SocketTest02 {
    public static void main(String[] args) throws IOException {
        Process p=Runtime.getRuntime().exec("ping "+"192.163.1.106");
        BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
        String str=null;
        StringBuilder sb=new StringBuilder();
        while((str=br.readLine())!=null){
            if(str.length()!=0){
                sb.append(str+"\r\n");
            }
        }
        System.out.println("本次指令返回的消息是:");
        System.out.println(sb.toString());
    }
}
