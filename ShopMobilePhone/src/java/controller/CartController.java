/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CartDAO;
import dal.VouchersDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import model.Cart;
import model.Users;
import model.Vouchers;

@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CartController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("username");
        if (user == null) {
            session.setAttribute("error", "Bạn cần đăng nhập trước!");
            response.sendRedirect("login-register");
            return;

        }

        CartDAO cartDao = new CartDAO();
        List<Cart> listCart = cartDao.getAllCart(user.getUserId());
        request.setAttribute("listCart", listCart);

        // tính tổng tiền
        BigDecimal totalCart = BigDecimal.ZERO;
        for (Cart cart : listCart) {
            BigDecimal productPrice = cart.getProductId().getPrice();
            BigDecimal extraPrice = cart.getAttributeId().getExtraPrice();
            BigDecimal quantity = BigDecimal.valueOf(cart.getQuantity());

            totalCart = totalCart.add((productPrice.add(extraPrice)).multiply(quantity));
        }
        request.setAttribute("totalCart", totalCart);

        request.getRequestDispatcher("cart.jsp").forward(request, response);
        request.removeAttribute("productAttributesId");
        session.removeAttribute("error");
        request.removeAttribute("error");
        return;
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CartDAO cartDao = new CartDAO();
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("error", "Hành động không hợp lệ!");
            doGet(request, response);
            return;
        }
        if (action.equals("quantity")) {

            try {

                int cartId = Integer.parseInt(request.getParameter("cartId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                String operator = request.getParameter("operator");

                // Cập nhật số lượng dựa trên thao tác
                if ("minus".equals(operator)) {

                    // Nếu số lượng = 0 thì xóa giỏ hàng
                    if (quantity == 1) {
                        if (cartDao.deleteCartById(cartId)) {
                            request.setAttribute("message", "Xóa sản phẩm khỏi giỏ hàng thành công!");
                        } else {
                            request.setAttribute("error", "Xóa sản phẩm thất bại!");
                        }
                        doGet(request, response);
                        return;
                    }

                    quantity--;
                } else if ("plus".equals(operator)) {
                    quantity++;
                } else {
                    request.setAttribute("error", "Thao tác không hợp lệ!");
                    doGet(request, response);
                    return;
                }

                if (cartDao.updateQuantityByCartId(cartId, quantity)) {
                    request.setAttribute("message", "Cập nhật số lượng thành công!");
                } else {
                    request.setAttribute("error", "Cập nhật số lượng thất bại!");
                }

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Dữ liệu đầu vào không hợp lệ!");
            }
            doGet(request, response);
            return;
        }

        if ("addCart".equals(action)) {
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("username");
            if (user == null) {
                session.setAttribute("error", "Bạn cần đăng nhập trước!");
                response.sendRedirect("login-register");
                return;

            }
            try {
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                int productId = Integer.parseInt(request.getParameter("productId"));
                int productAttributesId = Integer.parseInt(request.getParameter("productAttributesId"));

                // Kiểm tra điều kiện hợp lệ
                if (quantity <= 0) {
                    session.setAttribute("error", "Số lượng phải lớn hơn 0!");
                    response.sendRedirect("detail?id=" + Integer.parseInt(request.getParameter("productId")));
                    return;
                }

                // Gọi DAO để thêm vào giỏ hàng
                boolean isAdded = cartDao.addToCart(user.getUserId(), productId, productAttributesId, quantity);

                if (isAdded) {
                    request.setAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng!");
                } else {
                    request.setAttribute("error", "Không thể thêm sản phẩm vào giỏ hàng!");
                }
            } catch (NumberFormatException e) {
                session.setAttribute("error", "Bạn phải chọn dung lượng, màu máy!");
                response.sendRedirect("detail?id=" + Integer.parseInt(request.getParameter("productId")));
                return;

            }

            doGet(request, response);
        }

        // Xử lý áp dụng mã giảm giá
        if ("voucher".equals(action)) {
            String code = request.getParameter("code");

            if (code == null || code.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập mã giảm giá!");
                doGet(request, response);
                return;
            }

            VouchersDAO vdao = new VouchersDAO();
            Vouchers voucher = vdao.getVoucherByCode(code);

            // Kiểm tra nếu voucher không tồn tại
            if (voucher == null) {
                request.setAttribute("error", "Mã giảm giá không hợp lệ hoặc không tồn tại!");
                doGet(request, response);
                return;
            }

            BigDecimal totalCart;
            try {
                totalCart = new BigDecimal(request.getParameter("totalCart"));
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Giá trị đơn hàng không hợp lệ!");
                doGet(request, response);
                return;
            }

            if (voucher.getMinOrderValue().compareTo(totalCart) > 0) {
                request.setAttribute("error", "Đơn hàng không đáp ứng đủ điều kiện!");
                doGet(request, response);
                return;
            }

            if (voucher.getMaxUsage() <= 0) {
                request.setAttribute("error", "Mã giảm giá đã được dùng hết!");
                doGet(request, response);
                return;
            }

            if (voucher.getExpiryDate().before(new Date())) {
                request.setAttribute("error", "Voucher đã hết hạn!");
                doGet(request, response);
                return;
            }

            // Nếu voucher hợp lệ, lưu vào session
            HttpSession session = request.getSession();
            request.setAttribute("message", "Đã áp dụng mã giảm giá: " + voucher.getDiscountPercentage() + "%");
            session.setAttribute("appliedVoucher", voucher);

            doGet(request, response);
        }
        if ("remove".equals(action)) {
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            if (cartDao.deleteCartById(cartId)) {
                request.setAttribute("message", "Xóa sản phẩm khỏi giỏ hàng thành công!");
            } else {
                request.setAttribute("error", "Xóa sản phẩm thất bại!");
            }
            doGet(request, response);
            return;
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
