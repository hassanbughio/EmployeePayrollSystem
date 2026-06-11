package com.payroll.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String url = System.getenv("MYSQL_URL");
            String user = System.getenv("MYSQL_USER");
            String password = System.getenv("MYSQL_PASSWORD");
            
            if (url == null || url.isEmpty()) {
                url = "jdbc:mysql://localhost:3306/payroll_system";
                user = "root";
                password = "";
            }
            
            con = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Database Connected Successfully!");
            
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver Not Found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Connection Failed: " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }
}
