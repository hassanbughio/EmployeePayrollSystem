import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/getEmployees")
public class GetEmployeesServlet extends HttpServlet {
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
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM employees");
            while (rs.next()) {
                if (!first) json.append(",");

                String name = rs.getString("emp_name");
                if (name == null) name = rs.getString("first_name");
                if (name == null) name = "";

                String email = rs.getString("email");
                if (email == null) email = "";

                String phone = rs.getString("phone");
                if (phone == null) phone = "";

                String photo = rs.getString("photo");        // ← add kiya
                if (photo == null) photo = "";               // ← add kiya

                json.append("{")
                        .append("\"empId\":").append(rs.getInt("emp_id")).append(",")
                        .append("\"empName\":\"").append(name).append("\",")
                        .append("\"email\":\"").append(email).append("\",")
                        .append("\"phone\":\"").append(phone).append("\",")
                        .append("\"salary\":").append(rs.getDouble("salary")).append(",")
                        .append("\"photo\":\"").append(photo).append("\"")  // ← add kiya
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