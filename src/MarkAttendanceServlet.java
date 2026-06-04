import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/markAttendance")
public class MarkAttendanceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int empId = Integer.parseInt(request.getParameter("empId"));
        String checkOut = request.getParameter("checkOut");
        String date = LocalDate.now().toString();
        LocalTime now = LocalTime.now();

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement check = con.prepareStatement(
                    "SELECT * FROM attendance WHERE emp_id=? AND attendance_date=?");
            check.setInt(1, empId);
            check.setString(2, date);
            ResultSet rs = check.executeQuery();

            if (checkOut != null && !checkOut.isEmpty()) {
                // ===== CHECKOUT =====
                if (rs.next()) {
                    PreparedStatement ps = con.prepareStatement(
                            "UPDATE attendance SET check_out=? WHERE emp_id=? AND attendance_date=?");
                    ps.setString(1, checkOut);
                    ps.setInt(2, empId);
                    ps.setString(3, date);
                    ps.executeUpdate();
                    out.print("checkout");
                } else {
                    out.print("not_checked_in");
                }
            } else {
                // ===== CHECK IN =====
                if (rs.next()) {
                    out.print("already_in");
                } else {
                    LocalTime officeTime = LocalTime.of(9, 0);
                    String status = now.isAfter(officeTime) ? "LATE" : "PRESENT";
                    String checkIn = now.toString().substring(0, 8);

                    PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO attendance (emp_id, attendance_date, check_in, status) VALUES (?,?,?,?)");
                    ps.setInt(1, empId);
                    ps.setString(2, date);
                    ps.setString(3, checkIn);
                    ps.setString(4, status);
                    ps.executeUpdate();
                    out.print(status.toLowerCase());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.print("error");
        }
    }
}