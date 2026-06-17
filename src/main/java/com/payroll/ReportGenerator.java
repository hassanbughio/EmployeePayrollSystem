package com.payroll;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.*;
import java.util.HashMap;
import java.util.Map;

public class ReportGenerator {

    private static final String PROJECT_PATH = "C:\\Users\\Muhammad Hassan\\Desktop\\EmployeePayrollSystem";

    public void generateSalarySlip(int empId, String empName, int month,
                                   int year, double basicSalary, double bonus,
                                   double tax, double deductions, double netSalary,
                                   String empPhoto) {
        try {
            String reportPath = PROJECT_PATH + "\\reports\\salary_slip.jrxml";
            String outputPath = PROJECT_PATH + "\\reports\\salary_slip_" + empId + "_" + month + "_" + year + ".pdf";

            Map<String, Object> params = new HashMap<>();
            params.put("emp_id", empId);
            params.put("emp_name", empName);
            params.put("month", month);
            params.put("year", year);
            params.put("basic_salary", basicSalary);
            params.put("bonus", bonus);
            params.put("tax", tax);
            params.put("deductions", deductions);
            params.put("net_salary", netSalary);
            params.put("emp_photo", empPhoto);  // ← photo add kiya

            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);

            System.out.println("✅ Salary Slip Generated: " + outputPath);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
