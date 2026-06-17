package com.payroll.servlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/applyLeave")
public class ApplyLeaveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int empId = Integer.parseInt(request.getParameter("empId"));
        String leaveType = request.getParameter("leaveType");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        LeaveRequest leave = new LeaveRequest(0, empId, leaveType, startDate, endDate, "PENDING");
        LeaveDAO dao = new LeaveDAO();
        dao.applyLeave(leave);

        response.setContentType("text/plain");
        response.getWriter().print("success");
    }
}
