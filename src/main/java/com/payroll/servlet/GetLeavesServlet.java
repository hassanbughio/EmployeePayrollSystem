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
        PrintWriter out = response.getWriter();

        StringBuilder json = new StringBuilder("[");
        boolean first = true;

        try (Connection con = com.payroll.util.DBConnection.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM leave_requests");
            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{")
                        .append("\"leaveId\":").append(rs.getInt("leave_id")).append(",")
                        .append("\"empId\":").append(rs.getInt("emp_id")).append(",")
                        .append("\"leaveType\":\"").append(rs.getString("leave_type")).append("\",")
                        .append("\"startDate\":\"").append(rs.getString("start_date")).append("\",")
                        .append("\"endDate\":\"").append(rs.getString("end_date")).append("\",")
                        .append("\"status\":\"").append(rs.getString("status")).append("\"")
                        .append("}");
                first = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        json.append("]");
        out.print(json);
    }
}
