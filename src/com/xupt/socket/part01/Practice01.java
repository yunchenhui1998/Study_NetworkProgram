package com.xupt.socket.part01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
首先获取 获取本机IP地址，比如说是192.168.2.100。 那么本网段的ip地址就是从 192.168.2.1 到 192.168.2.255
再通过使用java 执行ping命令 判断这些ip地址能否使用，把能够使用的ip打印出来
 */
public class Practice01 {
    static List<String> list= Collections.synchronizedList(new ArrayList<>());
    static ThreadPoolExecutor threadPool=new ThreadPoolExecutor(10,15,60, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
    public static void main(String[] args) {
        String localIP=getLocalIp().substring(0,10);
        System.out.println("起始地址:"+localIP);
        testIP(localIP);
        threadPoolIsShutDown();
    }
    public static String getLocalIp(){
        String ip=null;
        try {
            InetAddress host=InetAddress.getLocalHost();
            ip=host.getHostAddress();
            System.out.println("本地IP地址是："+ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }
    public static boolean isUsableIp(String ip){
        String ping="ping "+ip;
        String str=null;
        StringBuffer sb=new StringBuffer();
        try {
            Process p=Runtime.getRuntime().exec(ping);
            InputStreamReader isr=new InputStreamReader(p.getInputStream(),"GBK");
            BufferedReader br=new BufferedReader(isr);
            while((str=br.readLine())!=null){
                if(str.length()!=0){
                    sb.append(str);
                }
            }
            isr.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(sb.toString().contains("TTL")){
            return true;
        }else{
            return false;
        }
    }
    public static void testIP(String startIp){
        AtomicInteger atomicValue=new AtomicInteger();
        for(int i=1;i<=255;i++){

            String ip=startIp+i;
            threadPool.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            boolean usableIp=isUsableIp(ip);
                            if(usableIp){
                                list.add(ip);
                                System.out.println("找到可连接的IP"+ip);
                            }
                            synchronized (atomicValue){
                                System.out.println("已经完成:"+atomicValue.incrementAndGet()+"个IP测试");
                            }
                        }
                    }
            );
        }
    }
    private static void threadPoolIsShutDown(){
        threadPool.shutdown();
        while(true){
            if(threadPool.isTerminated()){
                System.out.println("如下IP地址可以连接");
                for(String ip:list){
                    System.out.println(ip);
                }
                System.out.println("总共有"+list.size()+"个可用IP地址");
                break;
            }
        }
    }
}
