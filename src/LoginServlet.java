package com.payroll.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import com.payroll.dao.UserDAO;
import com.payroll.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        System.out.println("\n===== LOGIN ATTEMPT =====");
        System.out.println("Username: " + username);
        System.out.println("========================\n");
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.login(username, password);
            
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());
                
                System.out.println("✅ LOGIN SUCCESS!");
                out.println("{\"success\": true, \"message\": \"Login successful\", \"role\": \"" + user.getRole() + "\"}");
            } else {
                System.out.println("❌ LOGIN FAILED!");
                out.println("{\"success\": false, \"message\": \"Invalid username or password\"}");
            }
        } catch (Exception e) {
            System.out.println("❌ EXCEPTION: " + e.getMessage());
            e.printStackTrace();
            out.println("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}");
        }
        
        out.flush();
    }
}
