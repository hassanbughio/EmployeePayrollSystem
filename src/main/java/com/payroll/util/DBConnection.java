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
            
            // Remove jdbc: prefix if exists
            if (url != null && url.startsWith("jdbc:")) {
                url = url.substring(5); // Remove "jdbc:"
            }
            
            // Add jdbc: prefix if not exists
            if (url != null && !url.startsWith("jdbc:")) {
                url = "jdbc:" + url;
            }
            
            // Remove credentials from URL if present
            if (url != null && url.contains("@")) {
                // Format: jdbc:mysql://user:pass@host/db
                String[] parts = url.split("@");
                url = "jdbc:mysql://" + parts[1]; // Keep only host/db part
            }
            
            if (url == null || url.isEmpty()) {
                url = "jdbc:mysql://localhost:3306/payroll_system";
                user = "root";
                password = "";
            }
            
            System.out.println("Connecting to: " + url);
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
