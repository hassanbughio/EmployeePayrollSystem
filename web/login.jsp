<%@ page contentType="application/json" %>
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    
    if ("admin".equals(username) && "admin123".equals(password)) {
        out.print("{\"success\":true,\"message\":\"Login successful\",\"role\":\"ADMIN\"}");
    } else if ("hassan".equals(username) && "user123".equals(password)) {
        out.print("{\"success\":true,\"message\":\"Login successful\",\"role\":\"USER\"}");
    } else {
        out.print("{\"success\":false,\"message\":\"Invalid credentials\"}");
    }
%>
