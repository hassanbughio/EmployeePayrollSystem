package com.payroll.dao;
import com.payroll.Payroll;

import java.sql.*;

public class PayrollDAO {

    // 1. GENERATE PAYROLL
    public void generatePayroll(Payroll payroll) {
        String sql = "INSERT INTO payroll (emp_id, month, year, basic_salary, bonus, tax, deductions, net_salary, payment_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = com.payroll.util.DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, payroll.getEmpId());
            ps.setInt(2, payroll.getMonth());
            ps.setInt(3, payroll.getYear());
            ps.setDouble(4, payroll.getBasicSalary());
            ps.setDouble(5, payroll.getBonus());
            ps.setDouble(6, payroll.getTax());
            ps.setDouble(7, payroll.getDeductions());
            ps.setDouble(8, payroll.getNetSalary());
            ps.setString(9, payroll.getPaymentDate());
            ps.executeUpdate();
            System.out.println("✅ Payroll Generated Successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. VIEW PAYROLL BY EMPLOYEE
    public void viewPayroll(int empId) {
        String sql = "SELECT * FROM payroll WHERE emp_id=?";
        try (Connection con = com.payroll.util.DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Payroll Details ---");
            while (rs.next()) {
                System.out.println(
                        "Month: " + rs.getInt("month") + "/" + rs.getInt("year") +
                                " | Basic: " + rs.getDouble("basic_salary") +
                                " | Bonus: " + rs.getDouble("bonus") +
                                " | Tax: " + rs.getDouble("tax") +
                                " | Deductions: " + rs.getDouble("deductions") +
                                " | Net Salary: " + rs.getDouble("net_salary")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. CALCULATE NET SALARY
    public double calculateNetSalary(double basicSalary, double bonus,
                                     double tax, double deductions) {
        double netSalary = (basicSalary + bonus) - (tax + deductions);
        System.out.println("\n--- Salary Calculation ---");
        System.out.println("Basic Salary : " + basicSalary);
        System.out.println("Bonus        : " + bonus);
        System.out.println("Tax          : " + tax);
        System.out.println("Deductions   : " + deductions);
        System.out.println("Net Salary   : " + netSalary);
        return netSalary;
    }
}
