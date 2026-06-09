package com.payroll.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        System.out.println("Login attempt: " + username);
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        // HARDCODED TEST
        if ("admin".equals(username) && "admin123".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", 1);
            session.setAttribute("username", username);
            session.setAttribute("role", "ADMIN");
            
            System.out.println("✅ Login success!");
            out.println("{\"success\": true, \"message\": \"Login successful\", \"role\": \"ADMIN\"}");
        } else if ("hassan".equals(username) && "user123".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", 2);
            session.setAttribute("username", username);
            session.setAttribute("role", "USER");
            
            System.out.println("✅ Login success!");
            out.println("{\"success\": true, \"message\": \"Login successful\", \"role\": \"USER\"}");
        } else {
            System.out.println("❌ Login failed!");
            out.println("{\"success\": false, \"message\": \"Invalid username or password\"}");
        }
        
        out.flush();
    }
}
