package com.payroll.servlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/getPayroll")
public class GetPayrollServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        StringBuilder json = new StringBuilder("[");
        boolean first = true;

        try (Connection con = com.payroll.util.DBConnection.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM payroll");
            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{")
                        .append("\"payrollId\":").append(rs.getInt("payroll_id")).append(",")
                        .append("\"empId\":").append(rs.getInt("emp_id")).append(",")
                        .append("\"month\":").append(rs.getInt("month")).append(",")
                        .append("\"year\":").append(rs.getInt("year")).append(",")
                        .append("\"basicSalary\":").append(rs.getDouble("basic_salary")).append(",")
                        .append("\"bonus\":").append(rs.getDouble("bonus")).append(",")
                        .append("\"tax\":").append(rs.getDouble("tax")).append(",")
                        .append("\"netSalary\":").append(rs.getDouble("net_salary"))
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
