package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Categories;

public class CategoriesDAO extends DBContext {

    public List<Categories> getAllCategories() {
        List<Categories> list = new ArrayList<>();
        String sql = "SELECT category_id, category_name, status, created_at FROM Categories";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categories category = new Categories(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                );
                list.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Categories getCategoryById(int categoryId) {
        String sql = "SELECT category_id, category_name, status, created_at FROM Categories WHERE category_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Categories(
                            rs.getInt("category_id"),
                            rs.getString("category_name"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Categories> getAllCategoriesAndQuantity() {
        List<Categories> list = new ArrayList<>();
        String sql = "SELECT c.category_id, c.category_name, c.status, c.created_at, "
                + "       COALESCE(SUM(p.stock_quantity), 0) AS total_quantity "
                + "FROM Categories c "
                + "LEFT JOIN Products p ON c.category_id = p.category_id "
                + "GROUP BY c.category_id, c.category_name, c.status, c.created_at";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categories category = new Categories(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("total_quantity")
                );
                list.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
