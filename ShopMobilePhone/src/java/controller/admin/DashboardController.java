/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.CategoriesDAO;
import dal.OrdersDAO;
import dal.ProductsDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Categories;
import model.Users;

/**
 *
 * @author trung
 */
@WebServlet(name = "DashboardController", urlPatterns = {"/admin/dashboard"})
public class DashboardController extends HttpServlet {

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
            out.println("<title>Servlet DashboardController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DashboardController at " + request.getContextPath() + "</h1>");
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

        try {
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("username");

            if (user == null || !"admin".equals(user.getRole())) {
                session.setAttribute("error", "Bạn không có quyền truy cập!");
                response.sendRedirect("../login-register");
                return;
            }

            ProductsDAO pdao = new ProductsDAO();
            OrdersDAO odao = new OrdersDAO();
            UserDAO udao = new UserDAO();
            CategoriesDAO cdao = new CategoriesDAO();
            // thống kê đơn hàng

            int totalOrder = odao.totalOrder();
            request.setAttribute("totalOrder", totalOrder);

            int totalOrderPending = odao.totalOrderStatus("pending");
            request.setAttribute("totalOrderPending", totalOrderPending);

            int totalOrderDelivered = odao.totalOrderStatus("delivered");
            request.setAttribute("totalOrderDelivered", totalOrderDelivered);

            // người dùng
            int totalUser = udao.totalUser();
            request.setAttribute("totalUser", totalUser);

            int totalNewUser = udao.totalNewUser();
            request.setAttribute("totalNewUser", totalNewUser);

            // hàng
            int totalQuanityInStock = pdao.totalQuanityInStock();
            request.setAttribute("totalQuanityInStock", totalQuanityInStock);

            int totalQuantityToShip = pdao.totalQuantityToShip();
            request.setAttribute("totalQuantityToShip", totalQuantityToShip);

            // số sp trong từng category
            List<Categories> listc = cdao.getAllCategoriesAndQuantity();
            request.setAttribute("listc", listc);

            //số sp bán ra từng tháng
            List<Object[]> productSalesByMonth = odao.getProductSalesByMonth();
            request.setAttribute("productSalesByMonth", productSalesByMonth);

            request.getRequestDispatcher("dashbroad.jsp").forward(request, response);
            session.removeAttribute("error");
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        processRequest(request, response);
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
