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
import model.Categories;
import model.ProductAttributes;
import model.ProductImages;
import model.Products;
import model.Reviews;
import model.Users;

/**
 *
 * @author trung
 */
public class ReviewDAO extends DBContext {

    public List<Reviews> getAllReviewByProductId(int productId) {
        List<Reviews> reviews = new ArrayList<>();
        String query = "SELECT review_id, user_id, product_id, rating, comment, created_at FROM Reviews WHERE product_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reviews review = new Reviews();
                    review.setReviewId(rs.getInt("review_id"));

                    UserDAO udao = new UserDAO();
                    Users user = udao.getUserById(rs.getInt("user_id"));
                    review.setUserId(user);

                    ProductsDAO pdao = new ProductsDAO();
                    Products product = pdao.getProductById(rs.getInt("product_id"));
                    review.setProductId(product);

                    review.setRating(rs.getInt("rating"));
                    review.setComment(rs.getString("comment"));
                    review.setCreatedAt(rs.getTimestamp("created_at"));

                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public boolean addReview(Reviews review) {
        String query = "INSERT INTO Reviews (user_id, product_id, rating, comment, created_at) VALUES (?, ?, ?, ?, GETDATE())";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, review.getUserId().getUserId());
            stmt.setInt(2, review.getProductId().getProductId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getAverageRating(int productId) {
        String query = "SELECT AVG(CAST(rating AS FLOAT)) AS avg_rating FROM Reviews WHERE product_id = ?";
        double avgRating = 0.0;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    avgRating = rs.getDouble("avg_rating");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avgRating;
    }

}
