package com.payroll.servlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/deleteEmployee")
public class DeleteEmployeeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int empId = Integer.parseInt(request.getParameter("empId"));

        EmployeeDAO dao = new EmployeeDAO();
        dao.deleteEmployee(empId);

        response.setContentType("text/plain");
        response.getWriter().print("success");
    }
}
