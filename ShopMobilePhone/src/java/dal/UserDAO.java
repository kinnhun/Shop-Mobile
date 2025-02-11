package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Users;

public class UserDAO extends DBContext {

    public Users authenticateUser(String email, String password) {
        String sql = "SELECT user_id, full_name, email, password, phone, address, avatar, role, status, last_login, created_at, updated_at FROM Users WHERE email = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Users(
                            rs.getInt("user_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getString("avatar"),
                            rs.getString("role"),
                            rs.getString("status"),
                            rs.getTimestamp("last_login"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

   public boolean registerUser(String fullName, String password, String phone, String email, String address, String avatarFileName, String role) {
        String checkUserSQL = "SELECT user_id FROM Users WHERE email = ? OR phone = ?";
        String insertUserSQL = "INSERT INTO Users (full_name, email, password, phone, address, avatar, role, status, created_at, updated_at) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, 'active', GETDATE(), GETDATE())";
        
        try (PreparedStatement checkStmt = connection.prepareStatement(checkUserSQL)) {
            checkStmt.setString(1, email);
            checkStmt.setString(2, phone);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Email hoặc số điện thoại đã tồn tại.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement insertStmt = connection.prepareStatement(insertUserSQL)) {
            insertStmt.setString(1, fullName);
            insertStmt.setString(2, email);
            insertStmt.setString(3, password);  // Có thể thêm mã hóa mật khẩu tại đây
            insertStmt.setString(4, phone);
            insertStmt.setString(5, address);
            insertStmt.setString(6, avatarFileName);
            insertStmt.setString(7, role);

            int affectedRows = insertStmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        boolean result = userDAO.registerUser(
            "Nguyễn Văn A",
            "123456",  // Bạn có thể thêm mã hóa mật khẩu trước khi truyền vào đây
            "0062497002",
            "nguyenvana@gmail.com",
            "Hà Nội",
            "avatar.png",
            "customer"
        );

        if (result) {
            System.out.println("Đăng ký người dùng thành công!");
        } else {
            System.out.println("Đăng ký người dùng thất bại!");
        }
    }

}
