import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/getDeptReport")
public class GetDeptReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        StringBuilder json = new StringBuilder("[");
        boolean first = true;

        try (Connection con = com.payroll.util.DBConnection.getConnection()) {
            String sql = "SELECT d.dept_id, d.dept_name, d.location, " +
                    "COUNT(e.emp_id) as emp_count, " +
                    "COALESCE(SUM(e.salary), 0) as total_salary, " +
                    "COALESCE(AVG(e.salary), 0) as avg_salary " +
                    "FROM departments d " +
                    "LEFT JOIN employees e ON d.dept_id = e.dept_id " +
                    "GROUP BY d.dept_id, d.dept_name, d.location";

            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{")
                        .append("\"deptId\":").append(rs.getInt("dept_id")).append(",")
                        .append("\"deptName\":\"").append(rs.getString("dept_name")).append("\",")
                        .append("\"location\":\"").append(rs.getString("location")).append("\",")
                        .append("\"empCount\":").append(rs.getInt("emp_count")).append(",")
                        .append("\"totalSalary\":").append(rs.getDouble("total_salary")).append(",")
                        .append("\"avgSalary\":").append(rs.getDouble("avg_salary"))
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