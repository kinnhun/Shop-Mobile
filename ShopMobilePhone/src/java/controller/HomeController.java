/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CartDAO;
import dal.CategoriesDAO;
import dal.FavoriteDAO;
import dal.OrdersDAO;
import dal.ProductsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Categories;
import model.Products;
import model.Users;

/**
 *
 * @author trung
 */
@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

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
            out.println("<title>Servlet HomeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeController at " + request.getContextPath() + "</h1>");
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
        // xóa mees vs error 
        request.getSession().removeAttribute("message");
        request.getSession().removeAttribute("error");

        CategoriesDAO cdao = new CategoriesDAO();
        List<Categories> listCategories = cdao.getAllCategories();
        HttpSession session = request.getSession();
        session.setAttribute("listCategories", listCategories);

        ProductsDAO pdao = new ProductsDAO();
        List<Products> listTop8Product = pdao.getListTop8BestSellProduct();
        request.setAttribute("listTop8Product", listTop8Product);

        List<Products> listTop8NewProduct = pdao.getListTop8NewProduct();
        request.setAttribute("listTop8NewProduct", listTop8NewProduct);

        // hiển thị ra số lượng tym cart
        Users user = (Users) session.getAttribute("username");
        if (user != null) {
            FavoriteDAO fdao = new FavoriteDAO();
            int countFavorite = fdao.countFavoriteByUserId(user.getUserId());
            session.setAttribute("countFavorite", countFavorite);

            CartDAO cartDao = new CartDAO();
            int countCart = cartDao.countCartByUserId(user.getUserId());
            session.setAttribute("countCart", countCart);
        }

        // search 
        List<Products> listProductSearch = pdao.getListAllProduct();
        session.setAttribute("allProducts", listProductSearch);

        request.getRequestDispatcher("home.jsp").forward(request, response);
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
