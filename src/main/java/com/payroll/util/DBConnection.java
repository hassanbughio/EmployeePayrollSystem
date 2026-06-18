package com.payroll.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Get from environment
            String host = System.getenv("MYSQLHOST");
            String port = System.getenv("MYSQLPORT");
            String database = System.getenv("MYSQL_DATABASE");
            String user = System.getenv("MYSQLUSER");
            String password = System.getenv("MYSQLPASSWORD");
            
            // Defaults if not set
            if (host == null) host = "mysql.railway.internal";
            if (port == null) port = "3306";
            if (database == null) database = "railway";
            if (user == null) user = "root";
            if (password == null) password = "pUCFWmjhtZuJpWghiGGPqGCMvLNReTeY";
            
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            
            System.out.println("Connecting to: " + url);
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
