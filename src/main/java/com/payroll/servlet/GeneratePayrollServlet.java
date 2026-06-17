import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/generatePayroll")
public class GeneratePayrollServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int empId = Integer.parseInt(request.getParameter("empId"));
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));
        double basicSalary = Double.parseDouble(request.getParameter("basicSalary"));
        double bonus = Double.parseDouble(request.getParameter("bonus"));
        double tax = Double.parseDouble(request.getParameter("tax"));
        double deductions = Double.parseDouble(request.getParameter("deductions"));

        Payroll payroll = new Payroll(0, empId, month, year, basicSalary, bonus, tax, deductions, "2025-06-30");
        PayrollDAO dao = new PayrollDAO();
        dao.generatePayroll(payroll);

        response.setContentType("text/plain");
        response.getWriter().print("success");
    }
}