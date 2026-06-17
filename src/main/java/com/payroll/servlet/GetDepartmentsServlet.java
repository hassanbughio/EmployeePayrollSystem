import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/getDepartments")
public class GetDepartmentsServlet extends HttpServlet {
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
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM departments");
            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{")
                        .append("\"deptId\":").append(rs.getInt("dept_id")).append(",")
                        .append("\"deptName\":\"").append(rs.getString("dept_name")).append("\",")
                        .append("\"location\":\"").append(rs.getString("location")).append("\"")
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