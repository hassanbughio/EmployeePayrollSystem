package com.payroll.servlet;

import com.payroll.dao.EmployeeDAO;
import com.payroll.model.Employee;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
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
            
            EmployeeDAO employeeDAO = new EmployeeDAO();
            Employee employee = employeeDAO.validateLogin(username, password);
            
            if (employee != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", employee.getId());
                session.setAttribute("username", employee.getUsername());
                session.setAttribute("role", employee.getRole());
                
                result.put("success", true);
                result.put("message", "Login successful");
                result.put("userId", employee.getId());
                result.put("role", employee.getRole());
            } else {
                result.put("success", false);
                result.put("message", "Invalid username or password");
            }
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Server error: " + e.getMessage());
            e.printStackTrace();
        }
        
        out.print(gson.toJson(result));
        out.flush();
    }
}
