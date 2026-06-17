package com.payroll.servlet;
import com.payroll.ReportGenerator;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/generateReport")
public class GenerateReportServlet extends HttpServlet {

    private static final String PROJECT_PATH = "C:\\Users\\Muhammad Hassan\\Desktop\\EmployeePayrollSystem";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int empId = Integer.parseInt(request.getParameter("empId"));
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));

        String empName = "";
        String photoFileName = "";
        double basicSalary = 0, bonus = 0, tax = 0, deductions = 0, netSalary = 0;

        try (Connection con = com.payroll.util.DBConnection.getConnection()) {

            // Employee name aur photo lo
            PreparedStatement ps = con.prepareStatement(
                    "SELECT emp_name, photo FROM employees WHERE emp_id=?");
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                empName = rs.getString("emp_name");
                String p = rs.getString("photo");
                if (p != null && !p.isEmpty()) {
                    photoFileName = p;
                }
            }

            // Payroll data lo
            ps = con.prepareStatement(
                    "SELECT * FROM payroll WHERE emp_id=? AND month=? AND year=?");
            ps.setInt(1, empId);
            ps.setInt(2, month);
            ps.setInt(3, year);
            rs = ps.executeQuery();
            if (rs.next()) {
                basicSalary = rs.getDouble("basic_salary");
                bonus = rs.getDouble("bonus");
                tax = rs.getDouble("tax");
                deductions = rs.getDouble("deductions");
                netSalary = rs.getDouble("net_salary");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Photo ka full path banao
        String photoPath = "";
        if (!photoFileName.isEmpty()) {
            photoPath = PROJECT_PATH + "\\web\\photos\\" + photoFileName;
        }

        // PDF generate karo
        ReportGenerator report = new ReportGenerator();
        report.generateSalarySlip(empId, empName, month, year,
                basicSalary, bonus, tax, deductions, netSalary, photoPath);

        // PDF file browser mein bhejo
        File pdfFile = new File(PROJECT_PATH + "\\reports\\salary_slip_"
                + empId + "_" + month + "_" + year + ".pdf");

        if (!pdfFile.exists()) {
            response.setContentType("text/plain");
            response.getWriter().print("❌ PDF not found!");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=salary_slip.pdf");

        FileInputStream fis = new FileInputStream(pdfFile);
        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        fis.close();
    }
}
