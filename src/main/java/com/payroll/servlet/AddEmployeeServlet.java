package com.payroll.servlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/addEmployee")
public class AddEmployeeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String empName = request.getParameter("empName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        double salary = Double.parseDouble(request.getParameter("salary"));
        int deptId = Integer.parseInt(request.getParameter("deptId"));

        Employee emp = new Employee(0, empName, email, phone, salary, deptId);
        EmployeeDAO dao = new EmployeeDAO();
        dao.addEmployee(emp);

        response.setContentType("text/plain");
        response.getWriter().print("success");
    }
}
