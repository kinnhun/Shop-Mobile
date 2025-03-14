/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import DAO.OrdersDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.OrderDetails;
import model.Orders;
import model.Users;

@WebServlet(name = "ManagerOrderController", urlPatterns = {"/admin/manager-order"})
public class ManagerOrderController extends HttpServlet {

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
            out.println("<title>Servlet ManagerOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerOrderController at " + request.getContextPath() + "</h1>");
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
        
        if (user == null || !"admin".equals(user.getRole())) {
            session.setAttribute("error", "Bạn không có quyền truy cập!");
            response.sendRedirect("../login-register");
            return;
        }
        
        

        OrdersDAO odao = new OrdersDAO();

        if (request.getParameter("action") != null) {
            String action = request.getParameter("action");
            if (action.equals("detail")) {
                int id = Integer.parseInt(request.getParameter("id"));
                List<OrderDetails> listOrderDetail = odao.getOrderDetailByOrderId(id);
                request.setAttribute("listOrderDetail", listOrderDetail);

                Orders order = odao.getOrderById(id);
                request.setAttribute("order", order);

                request.getRequestDispatcher("manager_order_detail.jsp").forward(request, response);
                return;
            }
        }

        List<Orders> listOrder = odao.getAllOrder();

        request.setAttribute("listOrder", listOrder);

        request.getRequestDispatcher("manager_order.jsp").forward(request, response);
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

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String status = request.getParameter("status");
        OrdersDAO odao = new OrdersDAO();
        boolean checkUpdateStatus = odao.updateStatus(status, orderId);
        if (checkUpdateStatus) {
            session.setAttribute("message", "Cập nhật trạng thái thành công!");
            response.sendRedirect("manager-order?action=detail&id=" + orderId);
        } else {
            session.setAttribute("error", "Cập nhật trạng thái thất bại!");
            response.sendRedirect("manager-order?action=detail&id=" + orderId);

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
