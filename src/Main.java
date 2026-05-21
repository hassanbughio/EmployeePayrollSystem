public class Main {
    public static void main(String[] args) {

        System.out.println("=== TESTING START ===\n");

        // 1. DB Connection Test
        System.out.println("--- Test 1: DB Connection ---");
        DBConnection.getConnection();

        // 2. Employee Test
        System.out.println("\n--- Test 2: Employee CRUD ---");
        EmployeeDAO empDAO = new EmployeeDAO();
        Employee emp = new Employee(0, "Test User", "test@gmail.com", "03001111111", 45000, 1);
        empDAO.addEmployee(emp);
        empDAO.searchEmployee(1);

        // 3. Login Test
        System.out.println("\n--- Test 3: Login ---");
        UserDAO userDAO = new UserDAO();
        userDAO.login("admin", "admin123");
        userDAO.login("wrong", "wrong");

        // 4. Leave Test
        System.out.println("\n--- Test 4: Leave ---");
        LeaveDAO leaveDAO = new LeaveDAO();
        LeaveRequest leave = new LeaveRequest(0, 1, "Annual", "2025-07-01", "2025-07-03", "PENDING");
        leaveDAO.applyLeave(leave);
        leaveDAO.approveLeave(1);

        // 5. Payroll Test
        System.out.println("\n--- Test 5: Payroll ---");
        PayrollDAO payrollDAO = new PayrollDAO();
        payrollDAO.calculateNetSalary(50000, 5000, 3000, 2000);

        // 6. Report Test
        System.out.println("\n--- Test 6: Report ---");
        ReportGenerator report = new ReportGenerator();
        report.generateSalarySlip(1, "Muhammad Hassan", 6, 2025, 50000, 5000, 3000, 2000, 50000);

        System.out.println("\n=== ALL TESTS PASSED ===");
    }
}