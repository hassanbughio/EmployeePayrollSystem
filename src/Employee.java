public class Employee {

    private int empId;
    private String empName;
    private String email;
    private String phone;
    private double salary;
    private int deptId;

    // Constructor
    public Employee(int empId, String empName, String email,
                    String phone, double salary, int deptId) {
        this.empId = empId;
        this.empName = empName;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.deptId = deptId;
    }

    // Getters
    public int getEmpId() { return empId; }
    public String getEmpName() { return empName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public double getSalary() { return salary; }
    public int getDeptId() { return deptId; }

    // Setters
    public void setEmpId(int empId) { this.empId = empId; }
    public void setEmpName(String empName) { this.empName = empName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setDeptId(int deptId) { this.deptId = deptId; }
}
