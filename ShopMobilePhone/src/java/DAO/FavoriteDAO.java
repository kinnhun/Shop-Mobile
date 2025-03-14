/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Categories;
import model.Favorites;
import model.Products;
import model.Users;

public class FavoriteDAO extends DBContext {

    public List<Favorites> getAllFavoriteByUserId(int userId) {
        List<Favorites> favoritesList = new ArrayList<>();

        String sql = "  SELECT * FROM Favorites WHERE [user_id] = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Favorites favorite = new Favorites();
                    favorite.setFavoriteId(rs.getInt("favorite_id"));

                    UserDAO userDao = new UserDAO();
                    Users user = userDao.getUserById(rs.getInt("user_id"));
                    favorite.setUserId(user);

                    ProductsDAO productDao = new ProductsDAO();
                    Products product = productDao.getProductById(rs.getInt("product_id"));
                    favorite.setProductId(product);

                    favorite.setCreateAt(rs.getDate("created_at"));
                    favoritesList.add(favorite);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favoritesList;
    }

    public boolean removeFavoriteById(int favoriteId) {
        String sql = "DELETE FROM Favorites WHERE favorite_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, favoriteId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addListFavorites(int userId, int productId) {
        String sql = "INSERT INTO Favorites (user_id, product_id, created_at) VALUES (?, ?, GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkExistFavorite(int userId, int productId) {
        String sql = "SELECT COUNT(*) FROM Favorites WHERE user_id = ? AND product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countFavoriteByUserId(int userId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM favorites WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1); 
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public static void main(String[] args) {
        FavoriteDAO ff = new FavoriteDAO();
        List<Favorites> list = ff.getAllFavoriteByUserId(1);
        for (Favorites favorites : list) {
            System.out.println(favorites.toString());
        }
    }

}
