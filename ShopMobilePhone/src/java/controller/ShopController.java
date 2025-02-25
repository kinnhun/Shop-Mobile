/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoriesDAO;
import dal.ProductsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Categories;
import model.Products;

/**
 *
 * @author trung
 */
@WebServlet(name = "ShopController", urlPatterns = {"/shop"})
public class ShopController extends HttpServlet {

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
            out.println("<title>Servlet ShopController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShopController at " + request.getContextPath() + "</h1>");
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

        ProductsDAO pdao = new ProductsDAO();
        List<Products> listProduct = new ArrayList<>();
        int categoryId = 0;
        BigDecimal minPrice = new BigDecimal("0");
        BigDecimal maxPrice = new BigDecimal("0");

        if (request.getParameter("categoryId") != null) {
            categoryId = Integer.parseInt(request.getParameter("categoryId"));

        }

        if (request.getParameter("min") != null && request.getParameter("max") != null) {
            minPrice = new BigDecimal(request.getParameter("min"));
            maxPrice = new BigDecimal(request.getParameter("max"));

        }

        listProduct = pdao.getListProduct(categoryId, minPrice, maxPrice);

        if (listProduct.isEmpty()) {
            request.setAttribute("error", "Không có sản phẩm nào.");
        }
        
        
        

        if (request.getParameter("sortPrice") != null) {
            String sortPrice = request.getParameter("sortPrice");

            if ("desc".equals(sortPrice)) {
                Collections.sort(listProduct, new Comparator<Products>() {
                    @Override
                    public int compare(Products p1, Products p2) {
                        return p2.getPrice().compareTo(p1.getPrice());
                    }
                });
            } else if ("asc".equals(sortPrice)) {
                Collections.sort(listProduct, new Comparator<Products>() {
                    @Override
                    public int compare(Products p1, Products p2) {
                        return p1.getPrice().compareTo(p2.getPrice());
                    }
                });
            }
        }

        // min max giá
        int min = pdao.getMinPrice();
        int max = pdao.getMaxPrice();
        request.setAttribute("minPrice", min);
        request.setAttribute("maxPrice", max);

        // selected
        request.setAttribute("min", minPrice);
        request.setAttribute("max", maxPrice);
        request.setAttribute("categoryId", categoryId);

        request.setAttribute("listProduct", listProduct);

        // filter
        CategoriesDAO cdao = new CategoriesDAO();
        List<Categories> listCategories = cdao.getAllCategories();
        request.setAttribute("listCategories", listCategories);

        request.getRequestDispatcher("shop.jsp").forward(request, response);
        request.removeAttribute("error");

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
