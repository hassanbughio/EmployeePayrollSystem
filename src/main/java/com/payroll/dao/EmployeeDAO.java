
package com.payroll.dao;

import com.payroll.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EmployeeDAO {
    // 1. ADD EMPLOYEE
    public void addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (emp_name, email, phone, salary, dept_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = com.payroll.util.DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getEmpName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getPhone());
            ps.setDouble(4, emp.getSalary());
            ps.setInt(5, emp.getDeptId());
            ps.executeUpdate();
            System.out.println("✅ Employee Added Successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. UPDATE EMPLOYEE
    public void updateEmployee(Employee emp) {
        String sql = "UPDATE employees SET emp_name=?, email=?, phone=?, salary=?, dept_id=? WHERE emp_id=?";
        try (Connection con = com.payroll.util.DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getEmpName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getPhone());
            ps.setDouble(4, emp.getSalary());
            ps.setInt(5, emp.getDeptId());
            ps.setInt(6, emp.getEmpId());
            ps.executeUpdate();
            System.out.println("✅ Employee Updated Successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. DELETE EMPLOYEE
    public void deleteEmployee(int empId) {
        String sql = "DELETE FROM employees WHERE emp_id=?";
        try (Connection con = com.payroll.util.DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.executeUpdate();
            System.out.println("✅ Employee Deleted Successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. SEARCH EMPLOYEE
    public Employee searchEmployee(int empId) {
        String sql = "SELECT * FROM employees WHERE emp_id=?";
        Employee emp = null;
        try (Connection con = com.payroll.util.DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                emp = new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("emp_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDouble("salary"),
                        rs.getInt("dept_id")
                );
                System.out.println("✅ Employee Found: " + emp.getEmpName());
            } else {
                System.out.println("❌ Employee Not Found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emp;
    }
}
