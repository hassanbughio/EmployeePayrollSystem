package com.payroll.servlet;

import com.payroll.util.DBConnection;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> result = new HashMap<>();
        
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                result.put("success", false);
                result.put("message", "Username and password required");
                out.print(gson.toJson(result));
                return;
            }
            
            // Database se check kar
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // Login successful
                HttpSession session = request.getSession();
                int userId = rs.getInt("user_id");
                String role = rs.getString("role");
                
                session.setAttribute("userId", userId);
                session.setAttribute("username", username);
                session.setAttribute("role", role);
                
                result.put("success", true);
                result.put("message", "Login successful");
                result.put("userId", userId);
                result.put("username", username);
                result.put("role", role);
            } else {
                // Login failed
                result.put("success", false);
                result.put("message", "Invalid username or password");
            }
            
            conn.close();
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Server error: " + e.getMessage());
            e.printStackTrace();
        }
        
        out.print(gson.toJson(result));
        out.flush();
    }
}
