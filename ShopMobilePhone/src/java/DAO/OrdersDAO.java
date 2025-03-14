/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.Categories;
import model.Favorites;
import model.OrderDetails;
import model.Orders;
import model.ProductAttributes;
import model.Products;
import model.Users;

public class OrdersDAO extends DBContext {

    public boolean addOrder(int userId, BigDecimal totalPrice, String status, String shippingAddress) {
        String sql = "INSERT INTO Orders (user_id, total_price, status, shipping_address, created_at, updated_at) "
                + "VALUES (?, ?, ?, ?, GETDATE(), GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setBigDecimal(2, totalPrice);
            ps.setString(3, status);
            ps.setString(4, shippingAddress);

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Orders getOrderNewByUserId(int userId) {
        String sql = "SELECT TOP 1 order_id, user_id, total_price, status, shipping_address, created_at, updated_at "
                + "FROM Orders WHERE user_id = ? ORDER BY created_at DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserDAO userDao = new UserDAO();
                    Users user = userDao.getUserById(userId);

                    return new Orders(
                            rs.getInt("order_id"),
                            user,
                            rs.getBigDecimal("total_price"),
                            rs.getString("status"),
                            rs.getString("shipping_address"),
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

    public boolean addOrderDetailByUserId(int userId) {
        List<Cart> listCart = getCartItemsByUserId(userId);
        Orders order = getLatestOrderByUserId(userId);

        if (order == null) {
            System.out.println("Không tìm thấy đơn hàng mới nhất của người dùng ID: " + userId);
            return false;
        }

        boolean allInserted = true;

        for (Cart cart : listCart) {
            BigDecimal totalPrice = calculateTotalPrice(cart);

            boolean insertSuccess = processOrderDetail(order, cart, totalPrice);

            if (!insertSuccess) {
                allInserted = false;
                System.out.println("Không thể thêm chi tiết đơn hàng cho sản phẩm ID: "
                        + cart.getProductId().getProductId());
            }
        }
        if (allInserted) {
            CartDAO cartDao = new CartDAO();
            boolean deleteSuccess = cartDao.deleteAllCartByUserId(userId);

            if (!deleteSuccess) {
                System.out.println("Lỗi: Không thể xóa giỏ hàng của người dùng ID: " + userId);
                return false;
            }
        }

        return allInserted;
    }

    private List<Cart> getCartItemsByUserId(int userId) {
        CartDAO cartDao = new CartDAO();
        return cartDao.getAllCart(userId);
    }

    private Orders getLatestOrderByUserId(int userId) {
        return getOrderNewByUserId(userId);
    }

    private BigDecimal calculateTotalPrice(Cart cart) {
        BigDecimal productPrice = cart.getProductId().getPrice();
        BigDecimal attributePrice = cart.getAttributeId().getExtraPrice();
        return productPrice.add(attributePrice);
    }

    private boolean processOrderDetail(Orders order, Cart cart, BigDecimal totalPrice) {

        ProductsDAO productDao = new ProductsDAO();

        boolean updateQuantity = productDao.updateQuanityOrderByProductId(cart.getProductId().getProductId(), cart.getAttributeId().getAttributeId(), cart.getQuantity());
        if (updateQuantity == false) {
            return false;
        }
        return insertOrderDetail(
                order.getOrderId(),
                cart.getProductId().getProductId(),
                cart.getAttributeId().getAttributeId(),
                cart.getQuantity(),
                totalPrice
        );
    }

    public boolean insertOrderDetail(int orderId, int productId, int attributeId, int quantity, BigDecimal price) {
        String sql = "INSERT INTO OrderDetails (order_id, product_id, attribute_id, quantity, price) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, attributeId);
            ps.setInt(4, quantity);
            ps.setBigDecimal(5, price);

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Orders> getAllOrderByUserId(int userId) {
        List<Orders> ordersList = new ArrayList<>();
        String sql = "SELECT order_id, user_id, total_price, status, shipping_address, created_at, updated_at "
                + "FROM Orders WHERE user_id = ? ORDER BY created_at DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                UserDAO userDao = new UserDAO();
                Users user = userDao.getUserById(userId);

                while (rs.next()) {
                    Orders order = new Orders(
                            rs.getInt("order_id"),
                            user,
                            rs.getBigDecimal("total_price"),
                            rs.getString("status"),
                            rs.getString("shipping_address"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    );
                    ordersList.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    public List<OrderDetails> getOrderDetailByOrderId(int orderId) {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        String query = "SELECT order_detail_id, order_id, product_id, attribute_id, quantity, price "
                + "FROM OrderDetails WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderDetails orderDetail = new OrderDetails();

                orderDetail.setOrderDetaiId(rs.getInt("order_detail_id"));

                OrdersDAO orderDao = new OrdersDAO();
                Orders order = orderDao.getOrderById(rs.getInt("order_id"));
                orderDetail.setOrderId(order);

                ProductsDAO productDao = new ProductsDAO();
                Products product = productDao.getProductById(rs.getInt("product_id"));
                orderDetail.setProductId(product);

                ProductAttributes productAttributes = productDao.getProductAttributesById(rs.getInt("attribute_id"));
                orderDetail.setAttributeId(productAttributes);

                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.setPrice(rs.getBigDecimal("price"));

                orderDetailsList.add(orderDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDetailsList;
    }

    public Orders getOrderById(int orderId) {
        String sql = "SELECT order_id, user_id, total_price, status, shipping_address, created_at, updated_at "
                + "FROM Orders WHERE order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserDAO userDao = new UserDAO();
                    Users user = userDao.getUserById(rs.getInt("user_id"));

                    return new Orders(
                            rs.getInt("order_id"),
                            user,
                            rs.getBigDecimal("total_price"),
                            rs.getString("status"),
                            rs.getString("shipping_address"),
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

    public List<Orders> getAllOrder() {
        List<Orders> ordersList = new ArrayList<>();
        String sql = "SELECT order_id, user_id, total_price, status, shipping_address, created_at, updated_at FROM Orders ORDER BY created_at DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            UserDAO userDao = new UserDAO();

            while (rs.next()) {
                Users user = userDao.getUserById(rs.getInt("user_id"));
                Orders order = new Orders(
                        rs.getInt("order_id"),
                        user,
                        rs.getBigDecimal("total_price"),
                        rs.getString("status"),
                        rs.getString("shipping_address"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                ordersList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    public boolean updateStatus(String status, int orderId) {
        String sql = "UPDATE Orders SET status = ?, updated_at = GETDATE() WHERE order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int totalOrder() {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM orders";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public int totalOrderStatus(String status) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM orders WHERE status = ?";
        try (
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public List<Object[]> getProductSalesByMonth() throws SQLException {
        List<Object[]> salesData = new ArrayList<>();

        String query = "SELECT YEAR(o.created_at) AS year, \n"
                + "       MONTH(o.created_at) AS month, \n"
                + "       SUM(od.quantity) AS total_quantity_sold\n"
                + "FROM [PhoneStore].[dbo].[Orders] o\n"
                + "JOIN [PhoneStore].[dbo].[OrderDetails] od \n"
                + "    ON o.order_id = od.order_id\n"
                + "WHERE o.status = 'shipped'\n"
                + "GROUP BY YEAR(o.created_at), MONTH(o.created_at)\n"
                + "ORDER BY year DESC, month ASC;";

        // Execute the query
        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[3];
                row[0] = rs.getInt("year");
                row[1] = rs.getInt("month");
                row[2] = rs.getInt("total_quantity_sold");

                // Add the row to the salesData list
                salesData.add(row);
            }
        }

        // Return the list of sales data
        return salesData;
    }

    public static void main(String[] args) {
        try {
            OrdersDAO orderDao = new OrdersDAO();

            List<Object[]> salesData = orderDao.getProductSalesByMonth();

            // Print the sales data
            System.out.println("Sales Data (Year, Month, Total Quantity Sold):");
            for (Object[] row : salesData) {
                int year = (int) row[0];
                int month = (int) row[1];
                int totalQuantitySold = (int) row[2];

                System.out.println("Year: " + year + ", Month: " + month + ", Total Quantity Sold: " + totalQuantitySold);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
