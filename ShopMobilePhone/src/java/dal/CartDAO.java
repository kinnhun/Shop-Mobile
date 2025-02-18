/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Cart;
import model.Categories;
import model.ProductAttributes;
import model.Products;
import model.Users;

public class CartDAO extends DBContext {

    public int countCartByUserId(int userId) {
        int count = 0;
        String query = "SELECT COUNT(*) FROM Cart WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public List<Cart> getAllCart(int userId) {
        List<Cart> cartList = new ArrayList<>();
        String query = "SELECT cart_id, user_id, product_id, attribute_id, quantity, created_at "
                + "FROM Cart WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);  // Set user_id parameter
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cart cart = new Cart();
                cart.setCartId(rs.getInt("cart_id"));

                UserDAO userDao = new UserDAO();
                Users user = userDao.getUserById(rs.getInt("user_id"));
                cart.setUserId(user);

                ProductsDAO productDao = new ProductsDAO();
                Products product = productDao.getProductById(rs.getInt("product_id"));
                cart.setProductId(product);

                ProductAttributes productAttibutes = productDao.getProductAttributesById(rs.getInt("attribute_id"));
                cart.setAttributeId(productAttibutes);

                cart.setQuantity(rs.getInt("quantity"));
                cart.setCreatedAt(rs.getTimestamp("created_at"));
                cartList.add(cart);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartList;
    }

    public boolean updateQuantityByCartId(int cartId, int quantity) {
        boolean isUpdated = false;
        String query = "UPDATE Cart SET quantity = ? WHERE cart_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, cartId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isUpdated = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isUpdated;
    }

    public boolean deleteCartById(int cartId) {
        String query = "DELETE FROM Cart WHERE cart_id = ?";
        boolean isDeleted = false;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            int rowsAffected = stmt.executeUpdate();
            isDeleted = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isDeleted;
    }

    public boolean addToCart(int userId, int productId, int productAttributesId, int quantity) {
        if (isProductInCart(userId, productId, productAttributesId)) {
            return updateCartQuantity(userId, productId, productAttributesId, quantity);
        } else {
            return insertCartItem(userId, productId, productAttributesId, quantity);
        }
    }

    private boolean isProductInCart(int userId, int productId, int productAttributesId) {
        String checkQuery = "SELECT quantity FROM Cart WHERE user_id = ? AND product_id = ? AND attribute_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, productId);
            checkStmt.setInt(3, productAttributesId);
            ResultSet rs = checkStmt.executeQuery();
            return rs.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean updateCartQuantity(int userId, int productId, int productAttributesId, int quantity) {
        String updateQuery = "UPDATE Cart SET quantity = quantity + ? WHERE user_id = ? AND product_id = ? AND attribute_id = ?";
        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
            updateStmt.setInt(1, quantity);
            updateStmt.setInt(2, userId);
            updateStmt.setInt(3, productId);
            updateStmt.setInt(4, productAttributesId);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean insertCartItem(int userId, int productId, int productAttributesId, int quantity) {
        String insertQuery = "INSERT INTO Cart (user_id, product_id, attribute_id, quantity, created_at) VALUES (?, ?, ?, ?, GETDATE())";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, productId);
            insertStmt.setInt(3, productAttributesId);
            insertStmt.setInt(4, quantity);
            return insertStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   
}
