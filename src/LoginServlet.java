package com.payroll.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            System.out.println("\n===== LOGIN REQUEST =====");
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            
            // Direct database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver loaded");
            
            String url = System.getenv("MYSQL_URL");
            String user = System.getenv("MYSQL_USER");
            String pass = System.getenv("MYSQL_PASSWORD");
            
            System.out.println("URL: " + url);
            System.out.println("User: " + user);
            
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Database connected");
            
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            
            System.out.println("Executing query...");
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String role = rs.getString("role");
                
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                session.setAttribute("username", username);
                session.setAttribute("role", role);
                
                System.out.println("✅ Login successful!");
                out.println("{\"success\": true, \"message\": \"Login successful\", \"role\": \"" + role + "\"}");
            } else {
                System.out.println("❌ Invalid credentials");
                out.println("{\"success\": false, \"message\": \"Invalid username or password\"}");
            }
            
            con.close();
            
        } catch (ClassNotFoundException e) {
            System.out.println("❌ ClassNotFound: " + e.getMessage());
            out.println("{\"success\": false, \"message\": \"Driver error: " + e.getMessage() + "\"}");
        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
            e.printStackTrace();
            out.println("{\"success\": false, \"message\": \"Database error: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            System.out.println("❌ General Error: " + e.getMessage());
            e.printStackTrace();
            out.println("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}");
        }
        
        out.flush();
    }
}
