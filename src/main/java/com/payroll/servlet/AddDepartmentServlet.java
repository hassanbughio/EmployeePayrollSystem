package com.payroll.servlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/addDepartment")
public class AddDepartmentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String deptName = request.getParameter("deptName");
        String location = request.getParameter("location");

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try (Connection con = com.payroll.util.DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO departments (dept_name, location) VALUES (?, ?)");
            ps.setString(1, deptName);
            ps.setString(2, location);
            ps.executeUpdate();
            out.print("success");
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("error");
        }
    }
}
