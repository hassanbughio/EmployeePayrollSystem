import java.sql.*;

public class LeaveDAO {

    // 1. APPLY LEAVE
    public void applyLeave(LeaveRequest leave) {
        String sql = "INSERT INTO leave_requests (emp_id, leave_type, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, leave.getEmpId());
            ps.setString(2, leave.getLeaveType());
            ps.setString(3, leave.getStartDate());
            ps.setString(4, leave.getEndDate());
            ps.setString(5, "PENDING");
            ps.executeUpdate();
            System.out.println("✅ Leave Applied Successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. APPROVE LEAVE
    public void approveLeave(int leaveId) {
        updateStatus(leaveId, "APPROVED");
    }

    // 3. REJECT LEAVE
    public void rejectLeave(int leaveId) {
        updateStatus(leaveId, "REJECTED");
    }

    // STATUS UPDATE HELPER
    private void updateStatus(int leaveId, String status) {
        String sql = "UPDATE leave_requests SET status=? WHERE leave_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, leaveId);
            ps.executeUpdate();
            System.out.println("✅ Leave " + status + " Successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. VIEW ALL LEAVES
    public void viewAllLeaves() {
        String sql = "SELECT * FROM leave_requests";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- All Leave Requests ---");
            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("leave_id") +
                                " | Emp: " + rs.getInt("emp_id") +
                                " | Type: " + rs.getString("leave_type") +
                                " | Status: " + rs.getString("status")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}