package com.payroll.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if ("admin".equals(username) && "admin123".equals(password)) {
            out.print("{\"success\":true,\"message\":\"Login successful\",\"role\":\"ADMIN\"}");
        } else if ("hassan".equals(username) && "user123".equals(password)) {
            out.print("{\"success\":true,\"message\":\"Login successful\",\"role\":\"USER\"}");
        } else {
            out.print("{\"success\":false,\"message\":\"Invalid credentials\"}");
        }
    }
}
