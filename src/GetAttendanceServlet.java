import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/getAttendance")
public class GetAttendanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String date = request.getParameter("date");
        String empId = request.getParameter("empId");

        StringBuilder json = new StringBuilder("[");
        boolean first = true;

        try (Connection con = com.payroll.util.DBConnection.getConnection()) {
            String sql = "SELECT a.*, e.emp_name FROM attendance a " +
                    "JOIN employees e ON a.emp_id = e.emp_id WHERE 1=1";

            if (date != null && !date.isEmpty()) sql += " AND a.attendance_date='" + date + "'";
            if (empId != null && !empId.isEmpty()) sql += " AND a.emp_id=" + empId;
            sql += " ORDER BY a.attendance_date DESC";

            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (!first) json.append(",");

                String checkIn = rs.getString("check_in");
                String checkOut = rs.getString("check_out");
                if (checkIn == null) checkIn = "";
                if (checkOut == null) checkOut = "";

                json.append("{")
                        .append("\"attendanceId\":").append(rs.getInt("attendance_id")).append(",")
                        .append("\"empId\":").append(rs.getInt("emp_id")).append(",")
                        .append("\"empName\":\"").append(rs.getString("emp_name")).append("\",")
                        .append("\"date\":\"").append(rs.getString("attendance_date")).append("\",")
                        .append("\"checkIn\":\"").append(checkIn).append("\",")
                        .append("\"checkOut\":\"").append(checkOut).append("\",")
                        .append("\"status\":\"").append(rs.getString("status")).append("\"")
                        .append("}");
                first = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        json.append("]");
        out.print(json);
        out.flush();
    }
}