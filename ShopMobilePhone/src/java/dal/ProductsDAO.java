/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.math.BigDecimal;
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

    public List<Products> getListAllProduct() {
        List<Products> productList = new ArrayList<>();
        String sql = "SELECT p.product_id, p.category_id, p.name, p.description, p.price, p.stock_quantity, "
                + "p.status, p.created_at, p.updated_at, p.sold_quantity, "
                + "(SELECT TOP 1 pi.image_url FROM ProductImages pi WHERE pi.product_id = p.product_id) AS product_image "
                + "FROM Products p ORDER BY p.created_at DESC";

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

    public List<Products> getListProductByCategoryId(int categoryId) {
        List<Products> productList = new ArrayList<>();
        String sql = "SELECT p.product_id, p.category_id, p.name, p.description, p.price, p.stock_quantity, "
                + "p.status, p.created_at, p.updated_at, p.sold_quantity, "
                + "(SELECT TOP 1 pi.image_url FROM ProductImages pi WHERE pi.product_id = p.product_id) AS product_image "
                + "FROM Products p WHERE p.category_id = ? ORDER BY p.created_at DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                CategoriesDAO categoriesDAO = new CategoriesDAO();

                while (rs.next()) {
                    int productId = rs.getInt("product_id");
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

                    Products product = new Products(productId, category, name, description, price, stockQuantity,
                            status, createdAt, updatedAt, productImage, soldQuantity);
                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public Products getProductById(int id) {
        String sql = "SELECT p.product_id, p.category_id, p.name, p.description, p.price, p.stock_quantity, "
                + "p.status, p.created_at, p.updated_at, p.sold_quantity, "
                + "(SELECT TOP 1 pi.image_url FROM ProductImages pi WHERE pi.product_id = p.product_id) AS product_image "
                + "FROM Products p WHERE p.product_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            CategoriesDAO categoriesDAO = new CategoriesDAO();

            if (rs.next()) {
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

                return new Products(productId, category, name, description, price, stockQuantity, status, createdAt, updatedAt, productImage, soldQuantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy sản phẩm
    }

    public List<ProductImages> getAllImagesByProductId(int id) {
        List<ProductImages> imageList = new ArrayList<>();
        String sql = "SELECT image_id, product_id, image_url FROM ProductImages WHERE product_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int imageId = rs.getInt("image_id");
                int productId = rs.getInt("product_id");
                ProductsDAO pdao = new ProductsDAO();
                Products product = pdao.getProductById(1);

                String imageUrl = rs.getString("image_url");

                ProductImages image = new ProductImages(imageId, product, imageUrl);
                imageList.add(image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imageList;
    }

    public List<ProductAttributes> getProductAttributesByProductId(int id) {
        List<ProductAttributes> attributes = new ArrayList<>();
        String query = "SELECT attribute_id, product_id, color, storage, size, extra_price, stock_quantity "
                + "FROM ProductAttributes WHERE product_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProductAttributes attr = new ProductAttributes();
                attr.setAttributeId(rs.getInt("attribute_id"));

                ProductsDAO pdao = new ProductsDAO();
                Products product = pdao.getProductById(rs.getInt("product_id"));

                attr.setProductId(product);
                attr.setColor(rs.getString("color"));
                attr.setStorage(rs.getString("storage"));
                attr.setSize(rs.getString("size"));
                attr.setExtraPrice(rs.getBigDecimal("extra_price"));
                attr.setStockQuantity(rs.getInt("stock_quantity"));
                attributes.add(attr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attributes;
    }

    public List<ProductAttributes> listProductAttributesByStorage(int id, String storage) {
        List<ProductAttributes> attributes = new ArrayList<>();
        String query = "SELECT attribute_id, product_id, color, storage, size, extra_price, stock_quantity "
                + "FROM ProductAttributes WHERE product_id = ? AND storage = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, storage);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductAttributes attr = new ProductAttributes();
                    attr.setAttributeId(rs.getInt("attribute_id"));

                    Products product = new Products();
                    product.setProductId(rs.getInt("product_id"));

                    attr.setProductId(product);
                    attr.setColor(rs.getString("color"));
                    attr.setStorage(rs.getString("storage"));
                    attr.setSize(rs.getString("size"));
                    attr.setExtraPrice(rs.getBigDecimal("extra_price"));
                    attr.setStockQuantity(rs.getInt("stock_quantity"));

                    attributes.add(attr);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attributes;
    }

    public List<ProductAttributes> getProductAttributesByColor(int id, String color) {
        List<ProductAttributes> attributes = new ArrayList<>();
        String query = "SELECT attribute_id, product_id, color, storage, size, extra_price, stock_quantity "
                + "FROM ProductAttributes WHERE color = ? AND product_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, color);
            stmt.setInt(2, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductAttributes attr = new ProductAttributes();
                    attr.setAttributeId(rs.getInt("attribute_id"));
                    Products product = new Products();
                    product.setProductId(rs.getInt("product_id"));
                    attr.setProductId(product);
                    attr.setColor(rs.getString("color"));
                    attr.setStorage(rs.getString("storage"));
                    attr.setSize(rs.getString("size"));
                    attr.setExtraPrice(rs.getBigDecimal("extra_price"));
                    attr.setStockQuantity(rs.getInt("stock_quantity"));

                    attributes.add(attr);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thuộc tính sản phẩm theo màu: " + e.getMessage());
        }
        return attributes;
    }

    public ProductAttributes getProductAttributesByStorageColor(int productId, String storage, String color) {
        ProductAttributes attr = null;
        String query = "SELECT attribute_id, product_id, color, storage, size, extra_price, stock_quantity "
                + "FROM ProductAttributes WHERE product_id = ?";

        // Nếu storage không rỗng, thêm điều kiện lọc theo storage
        if (storage != null && !storage.isEmpty()) {
            query += " AND storage = ?";
        }

        // Nếu color không rỗng, thêm điều kiện lọc theo color
        if (color != null && !color.isEmpty()) {
            query += " AND color = ?";
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            int paramIndex = 2; // Vị trí tham số tiếp theo

            if (storage != null && !storage.isEmpty()) {
                stmt.setString(paramIndex++, storage);
            }
            if (color != null && !color.isEmpty()) {
                stmt.setString(paramIndex++, color);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    attr = new ProductAttributes();
                    attr.setAttributeId(rs.getInt("attribute_id"));

                    Products product = new Products();
                    product.setProductId(rs.getInt("product_id"));
                    attr.setProductId(product);

                    attr.setColor(rs.getString("color"));
                    attr.setStorage(rs.getString("storage"));
                    attr.setSize(rs.getString("size"));
                    attr.setExtraPrice(rs.getBigDecimal("extra_price"));
                    attr.setStockQuantity(rs.getInt("stock_quantity"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thuộc tính sản phẩm theo dung lượng và màu: " + e.getMessage());
        }
        return attr;
    }

    public int getMinPrice() {
        String sql = "SELECT MIN(p.price + COALESCE(pa.extra_price, 0)) AS min_price "
                + "FROM Products p "
                + "LEFT JOIN ProductAttributes pa ON p.product_id = pa.product_id";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("min_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu có lỗi
    }

    // Lấy giá cao nhất
    public int getMaxPrice() {
        String sql = "SELECT MAX(p.price + COALESCE(pa.extra_price, 0)) AS max_price "
                + "FROM Products p "
                + "LEFT JOIN ProductAttributes pa ON p.product_id = pa.product_id";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("max_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu có lỗi
    }

    public List<Products> getListProductByMinMaxPrice(int minPrice, int maxPrice) {
        List<Products> productList = new ArrayList<>();
        String sql = "SELECT p.product_id, p.category_id, p.name, p.description, p.price, p.stock_quantity, \n"
                + "       p.status, p.created_at, p.updated_at, p.sold_quantity, \n"
                + "       (SELECT TOP 1 pi.image_url FROM ProductImages pi WHERE pi.product_id = p.product_id) AS product_image\n"
                + "FROM Products p\n"
                + "WHERE p.price BETWEEN ? AND ? \n"
                + "ORDER BY p.created_at DESC;";
        CategoriesDAO categoriesDAO = new CategoriesDAO();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, minPrice);
            ps.setInt(2, maxPrice);

            ResultSet rs = ps.executeQuery();
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

                // Lấy thông tin category từ categoriesDAO
                Categories category = categoriesDAO.getCategoryById(categoryId);

                Products product = new Products(productId, category, name, description, price, stockQuantity, status, createdAt, updatedAt, productImage, soldQuantity);
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }

public List<Products> getListProduct(int categoryId, BigDecimal minPrice, BigDecimal maxPrice) {
    List<Products> filteredProducts = new ArrayList<>();

    List<Products> allProducts = getListAllProduct();

    // Nếu không có bất kỳ bộ lọc nào, trả về tất cả sản phẩm
    if (categoryId == 0 && minPrice.compareTo(BigDecimal.ZERO) == 0 && maxPrice.compareTo(BigDecimal.ZERO) == 0) {
        return allProducts;
    }

    for (Products product : allProducts) {
        int productCategoryId = product.getCategoryId().getCategoryId();
        boolean matchesCategory = (categoryId == 0 || productCategoryId == categoryId);
        boolean matchesPrice = (product.getPrice().compareTo(minPrice) >= 0 && product.getPrice().compareTo(maxPrice) <= 0);

        // Thêm sản phẩm nếu thỏa mãn ít nhất một điều kiện
        if ((categoryId == 0 || matchesCategory) && (minPrice.compareTo(BigDecimal.ZERO) == 0 && maxPrice.compareTo(BigDecimal.ZERO) == 0 || matchesPrice)) {
            filteredProducts.add(product);
        }
    }

    return filteredProducts;
}

   public static void main(String[] args) {
    ProductsDAO pdao = new ProductsDAO();
    int categoryId = 2;  // Ví dụ, categoryId = 1
    BigDecimal minPrice = new BigDecimal("0");  
    BigDecimal maxPrice = new BigDecimal("0"); 

    List<Products> productList = pdao.getListProduct(categoryId, minPrice, maxPrice);
    for (Products products : productList) {
        System.out.println(products.toString());
    }
}


}
