package com.xupt.socket.part03;

import java.sql.*;
import java.util.Random;

public class RobotDAO {
    public RobotDAO(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/how2java?characterEncoding=UTF-8","root","123456");
    }
    public int getResponseNumber(String receive){
        int sum=0;
        String sql="select count(*) from robot where receive='"+receive+"'";
        try(    Connection c=getConnection();
                PreparedStatement ps=c.prepareStatement(sql);
                ){
            ResultSet rs=ps.executeQuery(sql);
            if(rs.next()){
                sum=rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return sum;
    }
    public String getResponse(String receive){
        Random r=new Random();
        int sum=getResponseNumber(receive);
        String[] responses=new String[sum];
        String sql="select * from robot where receive='"+receive+"'";
        try(Connection c=getConnection();
            PreparedStatement ps=c.prepareStatement(sql);
                ){
            ResultSet rs=ps.executeQuery();
            int i=0;
            while(rs.next()){
                responses[i++]=rs.getString(3);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return responses[r.nextInt(sum)];
    }
}
