package com.payroll.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        if ("admin".equals(username) && "admin123".equals(password)) {
            out.println("{\"success\": true, \"message\": \"Login successful\", \"role\": \"ADMIN\"}");
        } else {
            out.println("{\"success\": false, \"message\": \"Invalid credentials\"}");
        }
        
        out.flush();
        out.close();
    }
}
