import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/updateEmployee")
@MultipartConfig
public class UpdateEmployeeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int empId = Integer.parseInt(request.getParameter("empId"));
        String empName = request.getParameter("empName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        double salary = Double.parseDouble(request.getParameter("salary"));

        // Photo handle karo
        String photoFileName = null;
        Part photoPart = request.getPart("photo");
        if (photoPart != null && photoPart.getSize() > 0) {
            String originalName = photoPart.getSubmittedFileName();
            photoFileName = "emp_" + empId + "_" + originalName;
            String savePath = getServletContext().getRealPath("/photos/") + photoFileName;
            photoPart.write(savePath);
        }

        // Database update karo
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps;

            if (photoFileName != null) {
                ps = con.prepareStatement(
                        "UPDATE employees SET emp_name=?, email=?, phone=?, salary=?, photo=? WHERE emp_id=?");
                ps.setString(1, empName);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.setDouble(4, salary);
                ps.setString(5, photoFileName);
                ps.setInt(6, empId);
            } else {
                ps = con.prepareStatement(
                        "UPDATE employees SET emp_name=?, email=?, phone=?, salary=? WHERE emp_id=?");
                ps.setString(1, empName);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.setDouble(4, salary);
                ps.setInt(5, empId);
            }
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setContentType("text/plain");
        response.getWriter().print("success");
    }
}