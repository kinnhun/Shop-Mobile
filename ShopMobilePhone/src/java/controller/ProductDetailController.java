/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ProductsDAO;
import dal.ReviewDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.ProductAttributes;
import model.ProductImages;
import model.Products;
import model.Reviews;

/**
 *
 * @author trung
 */
@WebServlet(name = "ProductDetailController", urlPatterns = {"/detail"})
public class ProductDetailController extends HttpServlet {

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
            out.println("<title>Servlet ProductDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductDetailController at " + request.getContextPath() + "</h1>");
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
        int id = Integer.parseInt(request.getParameter("id"));
        Products product = pdao.getProductById(id);
        request.setAttribute("product", product);

        List<ProductImages> listImageProduct = pdao.getAllImagesByProductId(id);
        request.setAttribute("listImageProduct", listImageProduct);

        List<ProductAttributes> listProductAttributes = pdao.getProductAttributesByProductId(id);
        request.setAttribute("listProductAttributes", listProductAttributes);

        String storage = request.getParameter("storage");
        String color = request.getParameter("color");

        if (storage != null) {
            List<ProductAttributes> listProductAttributesByStorage = pdao.listProductAttributesByStorage(id, storage);
            request.setAttribute("listProductAttributesByStorage", listProductAttributesByStorage);
            request.setAttribute("storage", storage);
        }

        if (color != null) {
            List<ProductAttributes> listProductAttributesByColor = pdao.getProductAttributesByColor(id, color);
            request.setAttribute("listProductAttributesByColor", listProductAttributesByColor);
            request.setAttribute("color", color);
        }

        if (storage != null && color != null) {
            ProductAttributes productAttributesByStorageColor = pdao.getProductAttributesByStorageColor(id, storage, color);
            request.setAttribute("extraPrice", productAttributesByStorageColor.getExtraPrice());
            
        }
         List<Products> listTop8NewProduct = pdao.getListTop8NewProduct();
        request.setAttribute("listTop8NewProduct", listTop8NewProduct);
        
        
        // đánh giá 
        ReviewDAO rdao = new ReviewDAO();
        List<Reviews> listReviewByProductId = rdao.getAllReviewByProductId(id);
        request.setAttribute("listReview", listReviewByProductId);
        
        double averageRating  = rdao.getAverageRating(id);
        request.setAttribute("averageRating", averageRating);
        

        request.getRequestDispatcher("detail.jsp").forward(request, response);
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
