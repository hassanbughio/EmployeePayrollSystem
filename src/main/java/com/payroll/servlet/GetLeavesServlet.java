package com.payroll.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/getLeaves")
public class GetLeavesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            Connection con = com.payroll.util.DBConnection.getConnection();
            
            if (con == null) {
                System.out.println("❌ Connection NULL!");
                out.print("[]");
                return;
            }
            
            String sql = "SELECT l.leave_id, l.emp_id, e.emp_name, l.leave_type, l.start_date, l.end_date, l.status " +
                        "FROM leaves l " +
                        "JOIN employees e ON l.emp_id = e.emp_id";
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            out.print("[");
            boolean first = true;
            
            while (rs.next()) {
                if (!first) out.print(",");
                
                out.print("{");
                out.print("\"leaveId\":" + rs.getInt("leave_id") + ",");
                out.print("\"empId\":" + rs.getInt("emp_id") + ",");
                out.print("\"empName\":\"" + rs.getString("emp_name") + "\",");
                out.print("\"leaveType\":\"" + rs.getString("leave_type") + "\",");
                out.print("\"startDate\":\"" + rs.getString("start_date") + "\",");
                out.print("\"endDate\":\"" + rs.getString("end_date") + "\",");
                out.print("\"status\":\"" + rs.getString("status") + "\"");
                out.print("}");
                
                first = false;
            }
            
            out.print("]");
            
            System.out.println("✅ Leaves fetched!");
            
            con.close();
            
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            out.print("[]");
        }
        
        out.flush();
    }
}
