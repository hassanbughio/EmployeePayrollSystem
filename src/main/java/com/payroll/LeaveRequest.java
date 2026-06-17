package com.payroll;
public class LeaveRequest {
    private int leaveId;
    private int empId;
    private String leaveType;
    private String startDate;
    private String endDate;
    private String status;

    public LeaveRequest(int leaveId, int empId, String leaveType,
                        String startDate, String endDate, String status) {
        this.leaveId = leaveId;
        this.empId = empId;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getters
    public int getLeaveId() { return leaveId; }
    public int getEmpId() { return empId; }
    public String getLeaveType() { return leaveType; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getStatus() { return status; }

    // Setters
    public void setStatus(String status) { this.status = status; }
}
