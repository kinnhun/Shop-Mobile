/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.CartDAO;
import DAO.OrdersDAO;
import DAO.VouchersDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import model.Cart;
import model.OrderDetails;
import model.Users;
import model.Vouchers;
import util.SendMail;

@WebServlet(name = "CheckoutController", urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

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
            out.println("<title>Servlet CheckoutController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckoutController at " + request.getContextPath() + "</h1>");
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
        // lấy ra all product 
        CartDAO cartDao = new CartDAO();
        List<Cart> listOrder = cartDao.getAllCart(user.getUserId());
        request.setAttribute("listOrder", listOrder);

        // tính tổng tiền
        BigDecimal totalCart = BigDecimal.ZERO;
        for (Cart cart : listOrder) {
            BigDecimal productPrice = cart.getProductId().getPrice();
            BigDecimal extraPrice = cart.getAttributeId().getExtraPrice();
            BigDecimal quantity = BigDecimal.valueOf(cart.getQuantity());

            totalCart = totalCart.add((productPrice.add(extraPrice)).multiply(quantity));
        }
        request.setAttribute("totalCart", totalCart);

        request.getRequestDispatcher("/user/checkout.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("username");
        if (user == null) {
            session.setAttribute("error", "Bạn cần đăng nhập trước!");
            response.sendRedirect("login-register");
            return;

        }

        // Lấy và xử lý totalPrice
        String totalPriceParam = request.getParameter("totalPrice");
        BigDecimal totalPrice = BigDecimal.ZERO;

        if (totalPriceParam != null && !totalPriceParam.isEmpty()) {
            try {
                totalPrice = new BigDecimal(totalPriceParam);
                System.out.println(totalPrice);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Giá trị tổng đơn hàng không hợp lệ!");
                doGet(request, response);
                return;
            }
        }

        // Kiểm tra địa chỉ giao hàng
        String shippingAddress = request.getParameter("shippingAddress");
        if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập địa chỉ nhận hàng!");
            doGet(request, response);
            return;
        }

        // Thêm đơn hàng vào cơ sở dữ liệu
        OrdersDAO odao = new OrdersDAO();
        boolean checkAddOrder = odao.addOrder(user.getUserId(), totalPrice, "Pending", shippingAddress.trim());

        // trừ đi voucher
        Vouchers appliedVoucher = (Vouchers) session.getAttribute("appliedVoucher");
        System.out.println(appliedVoucher.toString());
        appliedVoucher.setMaxUsage(appliedVoucher.getMaxUsage() - 1);

        
        VouchersDAO vdao = new VouchersDAO();
        boolean updateVoucher = vdao.updateVoucher(appliedVoucher);

        if (checkAddOrder) {

            // thêm vào orderDetal 
            boolean checkAddOrderDetail = odao.addOrderDetailByUserId(user.getUserId());

            List<OrderDetails>listOrderDetail = odao.getLatestOrderDetails(user.getUserId());
            SendMail sm = new SendMail();
            sm.sendMail(user.getEmail(), user.getFullName(),listOrderDetail);
            
            
            session.setAttribute("message", "Đặt hàng thành công!");
            response.sendRedirect("order");
        } else {
            request.setAttribute("error", "Đặt hàng thất bại. Vui lòng thử lại!");
            request.getRequestDispatcher("/user/checkout.jsp").forward(request, response);
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
