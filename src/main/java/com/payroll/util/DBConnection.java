package com.payroll.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Get from environment variables
            String host = "mySQL1.railway.internal";
            String port = "3306";
            String database = "railway";
            String user = "root";
            String password = "pUCFWmjhtZuJpWghiGGPqGCMvLNReTeY";
            
            // Build URL
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC";
            
            System.out.println("Connecting to: " + url);
            System.out.println("User: " + user);
            
            con = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Database Connected!");
            
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver Error: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Connection Error: " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }
}
