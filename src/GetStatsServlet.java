package com.payroll.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/getStats")
public class GetStatsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        
        int employees = 0, pendingLeaves = 0, payroll = 0, departments = 0;
        
        try (Connection con = com.payroll.util.DBConnection.getConnection()) {
            ResultSet rs;
            
            // Total Employees
            rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM employees");
            if (rs.next()) employees = rs.getInt(1);
            
            // Pending Leaves (FIXED TABLE NAME: leaves NOT leave_requests)
            rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM leaves WHERE status='PENDING'");
            if (rs.next()) pendingLeaves = rs.getInt(1);
            
            // Total Payroll
            rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM payroll");
            if (rs.next()) payroll = rs.getInt(1);
            
            // Total Departments
            rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM departments");
            if (rs.next()) departments = rs.getInt(1);
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        out.print("{\"employees\":" + employees +
                ",\"pendingLeaves\":" + pendingLeaves +
                ",\"payroll\":" + payroll +
                ",\"departments\":" + departments + "}");
        out.flush();
    }
}
