package com.payroll.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String mysqlUrl = System.getenv("MYSQL_URL");
            String user = System.getenv("MYSQL_USER");
            String password = System.getenv("MYSQL_PASSWORD");
            
            System.out.println("MYSQL_URL from env: " + mysqlUrl);
            System.out.println("MYSQL_USER from env: " + user);
            System.out.println("MYSQL_PASSWORD from env: " + (password != null ? "***" : "NULL"));
            
            String finalUrl = null;
            
            // Parse URL properly
            if (mysqlUrl != null && !mysqlUrl.isEmpty()) {
                // If URL contains credentials, extract host and database
                if (mysqlUrl.contains("@")) {
                    // Format: jdbc:mysql://user:pass@host:port/database
                    String[] parts = mysqlUrl.split("@");
                    if (parts.length > 1) {
                        finalUrl = "jdbc:mysql://" + parts[1];
                    }
                } else {
                    // Already clean
                    if (!mysqlUrl.startsWith("jdbc:")) {
                        finalUrl = "jdbc:mysql://" + mysqlUrl;
                    } else {
                        finalUrl = mysqlUrl;
                    }
                }
            }
            
            // Fallback to localhost
            if (finalUrl == null || finalUrl.isEmpty()) {
                finalUrl = "jdbc:mysql://localhost:3306/payroll_system";
                user = "root";
                password = "";
            }
            
            System.out.println("Connecting to: " + finalUrl);
            System.out.println("With user: " + user);
            
            con = DriverManager.getConnection(finalUrl, user, password);
            System.out.println("✅ Database Connected Successfully!");
            
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver Not Found: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ SQL Connection Error: " + e.getMessage());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("❌ Unexpected Error: " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }
}
