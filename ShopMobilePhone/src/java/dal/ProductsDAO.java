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
import model.Products;

/**
 *
 * @author trung
 */
public class ProductsDAO extends DBContext {

    public List<Products> getListTop8BestSellProduct() {
        List<Products> productList = new ArrayList<>();
        String sql = "SELECT p.product_id, p.category_id, p.name, p.description, p.price, p.stock_quantity, \n"
                + "       p.status, p.created_at, p.updated_at, p.sold_quantity, \n"
                + "       (SELECT TOP 1 pi.image_url \n"
                + "        FROM ProductImages pi \n"
                + "        WHERE pi.product_id = p.product_id \n"
                + "        ORDER BY pi.image_url) AS product_image\n"
                + "FROM Products p\n"
                + "ORDER BY p.sold_quantity DESC, p.created_at DESC\n"
                + "OFFSET 0 ROWS FETCH NEXT 8 ROWS ONLY;";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            CategoriesDAO categoriesDAO = new CategoriesDAO();

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int categoryId = rs.getInt("category_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                java.math.BigDecimal price = rs.getBigDecimal("price");
                int stockQuantity = rs.getInt("stock_quantity");
                int soldQuantity = rs.getInt("sold_quantity");
                String status = rs.getString("status");
                java.util.Date createdAt = rs.getTimestamp("created_at");
                java.util.Date updatedAt = rs.getTimestamp("updated_at");
                String productImage = rs.getString("product_image");

                Categories category = categoriesDAO.getCategoryById(categoryId);

                Products product = new Products(productId, category, name, description, price, stockQuantity, status, createdAt, updatedAt, productImage, soldQuantity);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public List<Products> getListTop8NewProduct() {
        List<Products> productList = new ArrayList<>();
        String sql = "SELECT p.product_id, p.category_id, p.name, p.description, p.price, p.stock_quantity, \n"
                + "                   p.status, p.created_at, p.updated_at, P.sold_quantity, \n"
                + "                   (SELECT TOP 1 pi.image_url FROM ProductImages pi WHERE pi.product_id = p.product_id) AS product_image\n"
                + "            FROM Products p\n"
                + "            ORDER BY p.created_at DESC\n"
                + "            OFFSET 0 ROWS FETCH NEXT 8 ROWS ONLY;";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            CategoriesDAO categoriesDAO = new CategoriesDAO();

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int categoryId = rs.getInt("category_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                java.math.BigDecimal price = rs.getBigDecimal("price");
                int stockQuantity = rs.getInt("stock_quantity");
                int soldQuantity = rs.getInt("sold_quantity");
                String status = rs.getString("status");
                java.util.Date createdAt = rs.getTimestamp("created_at");
                java.util.Date updatedAt = rs.getTimestamp("updated_at");
                String productImage = rs.getString("product_image");

                Categories category = categoriesDAO.getCategoryById(categoryId);

                Products product = new Products(productId, category, name, description, price, stockQuantity, status, createdAt, updatedAt, productImage, soldQuantity);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public static void main(String[] args) {
        ProductsDAO pdao = new ProductsDAO();
        List<Products> listTop8Product = pdao.getListTop8NewProduct();
        for (Products products : listTop8Product) {
            System.out.println(products.toString());
        }
    }
}
