import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/updateLeave")
public class UpdateLeaveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int leaveId = Integer.parseInt(request.getParameter("leaveId"));
        String status = request.getParameter("status");

        LeaveDAO dao = new LeaveDAO();
        if (status.equals("APPROVED")) {
            dao.approveLeave(leaveId);
        } else {
            dao.rejectLeave(leaveId);
        }

        response.setContentType("text/plain");
        response.getWriter().print("success");
    }
}