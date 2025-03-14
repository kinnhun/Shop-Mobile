/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.FavoriteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Favorites;
import model.Users;

@WebServlet(name = "FavoritesController", urlPatterns = {"/favorites"})
public class FavoritesController extends HttpServlet {

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
            out.println("<title>Servlet FavoritesController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FavoritesController at " + request.getContextPath() + "</h1>");
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

        FavoriteDAO favoriteDao = new FavoriteDAO();

        if (request.getParameter("action") != null) {
            String action = request.getParameter("action");
            if (action.equals("favorites")) {
                int productId = Integer.parseInt(request.getParameter("id"));

                boolean checkExist = favoriteDao.checkExistFavorite(user.getUserId(), productId);
                if (!checkExist) {
                    boolean isAdded = favoriteDao.addListFavorites(user.getUserId(), productId);
                    if (isAdded) {
                        request.setAttribute("message", "Thêm thành công!");
                    } else {
                        request.setAttribute("error", "Thêm thất bại!");
                    }
                } else {
                    request.setAttribute("error", "Sản phẫm đã được yêu thích!");
                }

            }
        }

        List<Favorites> listFavoritesByUserId = favoriteDao.getAllFavoriteByUserId(user.getUserId());
        request.setAttribute("listFavorites", listFavoritesByUserId);

        // 
        if (listFavoritesByUserId.isEmpty()) {
            request.setAttribute("error", "Không có sản phẩm nào yêu thích!");
        }
        
        int count =0;
        for (Favorites favorites : listFavoritesByUserId) {
            count ++;
        }
        session.setAttribute("countFavorite", count);

        request.getRequestDispatcher("/user/favorites.jsp").forward(request, response);
        request.removeAttribute("error");
        request.removeAttribute("message");
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

        int favoriteId = Integer.parseInt(request.getParameter("favoriteId"));
        String action = request.getParameter("action");
        FavoriteDAO fdao = new FavoriteDAO();

        if (action.equals("remove")) {
            boolean checkRemove = fdao.removeFavoriteById(favoriteId);
            if (checkRemove) {
                request.setAttribute("message", "Xóa thành công!");
            } else {
                request.setAttribute("error", "Xóa thất bại!");

            }
        }

        doGet(request, response);
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
