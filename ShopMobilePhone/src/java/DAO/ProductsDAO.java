/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.beans.Statement;
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

    public ProductAttributes getProductAttributesById(int id) {
        String query = "SELECT [attribute_id], [product_id], [color], [storage], [size], [extra_price], [stock_quantity] "
                + "FROM [ProductAttributes] WHERE [attribute_id] = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int attributeId = rs.getInt("attribute_id");

                Products product = getProductById(rs.getInt("product_id"));

                String color = rs.getString("color");
                String storage = rs.getString("storage");
                String size = rs.getString("size");
                BigDecimal extraPrice = rs.getBigDecimal("extra_price");
                int stockQuantity = rs.getInt("stock_quantity");

                return new ProductAttributes(attributeId, product, color, storage, size, extraPrice, stockQuantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateQuanityOrderByProductId(int productId, int attributeId, int quantity) {
        ProductsDAO productDao = new ProductsDAO();

        boolean checkUpdateQuantityProduct = productDao.updateQuantityProduct(productId, quantity);
        boolean checkUpdateQuanityProductAttribute = productDao.updateQuanityProductAttribute(attributeId, quantity);

        return checkUpdateQuantityProduct && checkUpdateQuanityProductAttribute;
    }

    public boolean updateQuantityProduct(int productId, int quantity) {
        String sql = "UPDATE Products "
                + "SET stock_quantity = stock_quantity - ?, "
                + "    sold_quantity = sold_quantity + ? "
                + "WHERE product_id = ? AND stock_quantity >= ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, quantity);
            stmt.setInt(3, productId);
            stmt.setInt(4, quantity);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateQuanityProductAttribute(int attributeId, int quantity) {
        String sql = "UPDATE ProductAttributes SET stock_quantity = stock_quantity - ? WHERE attribute_id = ? AND stock_quantity >= ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, attributeId);
            stmt.setInt(3, quantity);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createProduct(String name, int categoryId, BigDecimal price, String description, String status, String[] productImages) {
        String insertProductSQL = "INSERT INTO Products (category_id, name, description, price, stock_quantity, status, created_at, updated_at, sold_quantity) "
                + "VALUES (?, ?, ?, ?, 0, ?, GETDATE(), GETDATE(), 0)";
        String insertImageSQL = "INSERT INTO ProductImages (product_id, image_url) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(insertProductSQL)) {
            // Thêm sản phẩm mới
            stmt.setInt(1, categoryId);
            stmt.setString(2, name);
            stmt.setString(3, description);
            stmt.setBigDecimal(4, price);
            stmt.setString(5, status);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {

                ProductsDAO pdao = new ProductsDAO();
                int productId = pdao.getTop1NewProduct();

                // Thêm ảnh sản phẩm
                try (PreparedStatement imgStmt = connection.prepareStatement(insertImageSQL)) {
                    for (String imageUrl : productImages) {
                        imgStmt.setInt(1, productId);
                        imgStmt.setString(2, imageUrl);
                        imgStmt.executeUpdate();
                    }
                }
                return true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getTop1NewProduct() {
        String sql = "SELECT TOP 1 product_id FROM Products ORDER BY created_at DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("product_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateProduct(int productId, String name, String description, BigDecimal price, String status) {
        String sql = "UPDATE Products SET name = ?, description = ?, price = ?, status = ?, updated_at = GETDATE() WHERE product_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setBigDecimal(3, price);
            stmt.setString(4, status);
            stmt.setInt(5, productId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addProductAttribute(int productId, String color, String storage, BigDecimal extraPrice, int stockQuantity) {
        String sql = "INSERT INTO ProductAttributes (product_id, color, storage, extra_price, stock_quantity) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.setString(2, color);
            stmt.setString(3, storage);
            stmt.setBigDecimal(4, extraPrice);
            stmt.setInt(5, stockQuantity);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                // Nếu thêm thành công, cập nhật tổng số lượng sản phẩm
                ProductsDAO productDAO = new ProductsDAO();
                int totalQuantity = 0;
                List<ProductAttributes> listProductAttributes = productDAO.getProductAttributesByProductId(productId);

                for (ProductAttributes attribute : listProductAttributes) {
                    totalQuantity += attribute.getStockQuantity();
                }

                boolean checkUpdateQuantity = productDAO.updateQuantityProduct1(productId, totalQuantity);

                return checkUpdateQuantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean updateQuantityProduct1(int productId, int totalQuantity) {
        String sql = "UPDATE Products SET stock_quantity = ? WHERE product_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, totalQuantity);
            stmt.setInt(2, productId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateAttribute(int attributeId, String color, String storage, double extraPrice, int stockQuantity, int productId) {
        String sql = "UPDATE ProductAttributes SET color = ?, storage = ?, extra_price = ?, stock_quantity = ? WHERE attribute_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, color);
            stmt.setString(2, storage);
            stmt.setBigDecimal(3, BigDecimal.valueOf(extraPrice));
            stmt.setInt(4, stockQuantity);
            stmt.setInt(5, attributeId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                ProductsDAO productDAO = new ProductsDAO();
                int totalQuantity = 0;
                List<ProductAttributes> listProductAttributes = productDAO.getProductAttributesByProductId(productId);

                for (ProductAttributes attribute : listProductAttributes) {
                    totalQuantity += attribute.getStockQuantity();
                }

                boolean checkUpdateQuantity = productDAO.updateQuantityProduct1(productId, totalQuantity);

                return checkUpdateQuantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Products> getProductByName(String searchText) {
        List<Products> productList = new ArrayList<>();
        String sql = "SELECT p.product_id, p.category_id, p.name, p.description, p.price, p.stock_quantity, "
                + "p.status, p.created_at, p.updated_at, p.sold_quantity, "
                + "(SELECT TOP 1 pi.image_url FROM ProductImages pi WHERE pi.product_id = p.product_id) AS product_image "
                + "FROM Products p "
                + "WHERE p.name LIKE ? "
                + "ORDER BY p.created_at DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + searchText + "%"); // Tìm kiếm tương đối

            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public int totalQuanityInStock() {
        String sql = "SELECT SUM(stock_quantity) FROM Products";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int totalQuantityToShip() {
        String sql = "SELECT SUM(od.quantity) "
                + "FROM OrderDetails od "
                + "JOIN Orders o ON od.order_id = o.order_id "
                + "WHERE o.status = 'pending'";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
