import java.sql.*;

public class UserDAO {

    // LOGIN METHOD
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        User user = null;

        try (Connection con = com.payroll.util.DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
                System.out.println("✅ Login Successful! Role: " + user.getRole());
            } else {
                System.out.println("❌ Invalid Username or Password!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
