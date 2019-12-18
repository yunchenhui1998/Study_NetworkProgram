package com.xupt.socket.part01;

import java.net.InetAddress;
import java.net.UnknownHostException;
//获得本机IP地址
public class SocketTest01 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress host=InetAddress.getLocalHost();
        String ip=host.getHostAddress();
        System.out.println("本机IP地址是"+ip);
    }
}
