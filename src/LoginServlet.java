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
        
        System.out.println("Login attempt: " + username);
        
        UserDAO userDAO = new UserDAO();
        User user = userDAO.login(username, password);
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            
            System.out.println("Login successful: " + username);
            
            out.println("{");
            out.println("  \"success\": true,");
            out.println("  \"message\": \"Login successful\",");
            out.println("  \"userId\": " + user.getId() + ",");
            out.println("  \"username\": \"" + username + "\",");
            out.println("  \"role\": \"" + user.getRole() + "\"");
            out.println("}");
        } else {
            System.out.println("Login failed: " + username);
            
            out.println("{");
            out.println("  \"success\": false,");
            out.println("  \"message\": \"Invalid username or password\"");
            out.println("}");
        }
        
        out.flush();
    }
}
