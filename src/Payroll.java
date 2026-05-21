public class Payroll {
    private int payrollId;
    private int empId;
    private int month;
    private int year;
    private double basicSalary;
    private double bonus;
    private double tax;
    private double deductions;
    private double netSalary;
    private String paymentDate;

    public Payroll(int payrollId, int empId, int month, int year,
                   double basicSalary, double bonus, double tax,
                   double deductions, String paymentDate) {
        this.payrollId = payrollId;
        this.empId = empId;
        this.month = month;
        this.year = year;
        this.basicSalary = basicSalary;
        this.bonus = bonus;
        this.tax = tax;
        this.deductions = deductions;
        // Net Salary calculation
        this.netSalary = (basicSalary + bonus) - (tax + deductions);
        this.paymentDate = paymentDate;
    }

    // Getters
    public int getPayrollId() { return payrollId; }
    public int getEmpId() { return empId; }
    public int getMonth() { return month; }
    public int getYear() { return year; }
    public double getBasicSalary() { return basicSalary; }
    public double getBonus() { return bonus; }
    public double getTax() { return tax; }
    public double getDeductions() { return deductions; }
    public double getNetSalary() { return netSalary; }
    public String getPaymentDate() { return paymentDate; }
}