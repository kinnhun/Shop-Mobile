/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

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
import java.sql.Date;
import java.util.List;
import model.Users;
import model.Vouchers;

@WebServlet(name = "ManagerVoucherController", urlPatterns = {"/admin/manager-voucher"})
public class ManagerVoucherController extends HttpServlet {

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
            out.println("<title>Servlet ManagerVoucherController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerVoucherController at " + request.getContextPath() + "</h1>");
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
        VouchersDAO vdao = new VouchersDAO();
        HttpSession session = request.getSession();

        Users user = (Users) session.getAttribute("username");

        if (user == null || !"admin".equals(user.getRole())) {
            session.setAttribute("error", "Bạn không có quyền truy cập!");
            response.sendRedirect("../login-register");
            return;
        }

        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            String idStr = request.getParameter("voucher_id");

            if (idStr == null) {
                session.setAttribute("error", "Vui lòng chọn voucher để xóa!");
                doGet(request, response);
                return;
            }

            int id = Integer.parseInt(idStr);
            boolean checkDelete = vdao.deleteVoucher(id);

            if (checkDelete) {
                session.setAttribute("message", "Xóa voucher thành công!");
            } else {
                session.setAttribute("error", "Lỗi khi xóa voucher!");
            }
        }

        List<Vouchers> listVoucher = vdao.getAllVoucher();
        request.setAttribute("listVoucher", listVoucher);
        request.setAttribute("mes", "elllll");

        request.getRequestDispatcher("manager_voucher.jsp").forward(request, response);
        request.removeAttribute("error");
        request.removeAttribute("message");
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
        String action = request.getParameter("action");
        VouchersDAO vdao = new VouchersDAO();
        HttpSession session = request.getSession();
        try {
            if ("create".equals(action)) {
                String code = request.getParameter("code");
                String discountStr = request.getParameter("discount_percentage");
                String minOrderStr = request.getParameter("min_order_value");
                String maxUsageStr = request.getParameter("max_usage");
                String expiryDateStr = request.getParameter("expiry_date");

                if (code == null || code.isEmpty() || discountStr == null || minOrderStr == null
                        || maxUsageStr == null || expiryDateStr == null) {
                    session.setAttribute("error", "Vui lòng nhập đầy đủ thông tin!");
                    doGet(request, response);
                    return;
                }

                BigDecimal discount = new BigDecimal(discountStr);
                BigDecimal minOrder = new BigDecimal(minOrderStr);
                int maxUsage = Integer.parseInt(maxUsageStr);
                Date expiryDate = Date.valueOf(expiryDateStr);

                Vouchers voucher = new Vouchers(0, code, discount, minOrder, maxUsage, expiryDate, null);
                boolean checkAdd = vdao.addVoucher(voucher);

                if (checkAdd) {
                    session.setAttribute("message", "Thêm voucher thành công!");
                } else {
                    session.setAttribute("error", "Lỗi khi thêm voucher!");
                }
                doGet(request, response);
            } else if ("edit".equals(action)) {
                String idStr = request.getParameter("voucher_id");
                String code = request.getParameter("code");
                String discountStr = request.getParameter("discount_percentage");
                String minOrderStr = request.getParameter("min_order_value");
                String maxUsageStr = request.getParameter("max_usage");
                String expiryDateStr = request.getParameter("expiry_date");

                if (idStr == null || code == null || discountStr == null || minOrderStr == null
                        || maxUsageStr == null || expiryDateStr == null) {
                    doGet(request, response);
                    return;
                }

                int id = Integer.parseInt(idStr);
                BigDecimal discount = new BigDecimal(discountStr);
                BigDecimal minOrder = new BigDecimal(minOrderStr);
                int maxUsage = Integer.parseInt(maxUsageStr);
                Date expiryDate = Date.valueOf(expiryDateStr);

                Vouchers voucher = new Vouchers(id, code, discount, minOrder, maxUsage, expiryDate, null);
                boolean checkUpdate = vdao.updateVoucher(voucher);

                if (checkUpdate) {
                    session.setAttribute("message", "Cập nhật voucher thành công!");
                } else {
                    session.setAttribute("error", "Lỗi khi cập nhật voucher!");
                }
                doGet(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu nhập không hợp lệ! Hãy kiểm tra lại.");
            request.getRequestDispatcher("manager_voucher.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý!");
            request.getRequestDispatcher("manager_voucher.jsp").forward(request, response);
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
