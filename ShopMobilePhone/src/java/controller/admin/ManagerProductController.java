/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.CategoriesDAO;
import dal.ProductsDAO;
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
import model.Categories;
import model.ProductAttributes;
import model.Products;

@WebServlet(name = "ManagerProductController", urlPatterns = {"/admin/manager-product"})
public class ManagerProductController extends HttpServlet {

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
            out.println("<title>Servlet ManagerProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerProductController at " + request.getContextPath() + "</h1>");
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
        ProductsDAO pdao = new ProductsDAO();

        if (request.getParameter("action") != null) {
            String action = request.getParameter("action");
            if (action.equals("detail")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Products product = pdao.getProductById(id);
                request.setAttribute("product", product);

                List<ProductAttributes> attributes = pdao.getProductAttributesByProductId(id);
                request.setAttribute("attributes", attributes);

                request.getRequestDispatcher("manager_product_detail.jsp").forward(request, response);
                return;

            }

        }

        // xem all product 
        List<Products> listProduct = pdao.getListAllProduct();
        request.setAttribute("listProduct", listProduct);

        CategoriesDAO cdao = new CategoriesDAO();
        List<Categories> listCategories = cdao.getAllCategories();
        request.setAttribute("listCategories", listCategories);

        request.getRequestDispatcher("manager_product.jsp").forward(request, response);
        session.removeAttribute("message");
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
        HttpSession session = request.getSession();

        String action = request.getParameter("action");
        if ("create".equals(action)) {
            try {
                String name = request.getParameter("name");
                String categoryIdParam = request.getParameter("categoryId");
                String priceParam = request.getParameter("price");
                String description = request.getParameter("description");
                String status = request.getParameter("status");
                String[] productImages = request.getParameterValues("productImages");

                if (name == null || categoryIdParam == null || priceParam == null || status == null) {
                    request.setAttribute("errorMessage", "Vui lòng nhập đầy đủ thông tin.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }

                BigDecimal price = new BigDecimal(priceParam);
                int categoryId = Integer.parseInt(categoryIdParam);

                ProductsDAO productDAO = new ProductsDAO();
                boolean isCreated = productDAO.createProduct(name, categoryId, price, description, status, productImages);

                if (isCreated) {
                    session.setAttribute("message", "Tạo sản phẩm thành công");
                    response.sendRedirect("manager-product");
                } else {
                    request.setAttribute("error", "Tạo sản phẩm thất bại, vui lòng thử lại.");
                    request.getRequestDispatcher("manager-product").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Dữ liệu không hợp lệ. Vui lòng kiểm tra số tiền và số lượng.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("error", "Có lỗi xảy ra. Vui lòng thử lại.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else if (action.equals("edit-product")) {
            try {
                int productId = Integer.parseInt(request.getParameter("productId"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                BigDecimal price = new BigDecimal(request.getParameter("price"));
                String status = request.getParameter("status");

                ProductsDAO productDAO = new ProductsDAO();
                boolean isUpdated = productDAO.updateProduct(productId, name, description, price, status);

                if (isUpdated) {
                    session.setAttribute("message", "Cập nhật thành công!");
                    response.sendRedirect("manager-product?action=detail&id=" + productId);
                } else {
                    // Nếu thất bại, báo lỗi
                    request.setAttribute("error", "Cập nhật sản phẩm thất bại!");
                    response.sendRedirect("manager-product?action=detail&id=" + productId);
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Lỗi xử lý dữ liệu!");
                response.sendRedirect("manager-product?action=detail&id=" + Integer.parseInt(request.getParameter("productId")));
            }
        } else if (action.equals("add-attribute")) {
            try {
                int productId = Integer.parseInt(request.getParameter("productId"));
                String color = request.getParameter("color");
                String storage = request.getParameter("storage");
                BigDecimal extraPrice = new BigDecimal(request.getParameter("extraPrice"));
                int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
                ProductsDAO productDAO = new ProductsDAO();

                List<ProductAttributes> listProductAttributes = productDAO.getProductAttributesByProductId(productId);
                for (ProductAttributes listProductAttribute : listProductAttributes) {
                    if (color.equals(listProductAttribute.getColor()) && storage.equals(listProductAttribute.getStorage())) {
                        session.setAttribute("error", "Đã tồn tai!");

                        response.sendRedirect("manager-product?action=detail&id=" + productId);
                        return;
                    }
                }

                boolean success = productDAO.addProductAttribute(productId, color, storage, extraPrice, stockQuantity);

                if (success) {
                    session.setAttribute("message", "Thêm thành công!");

                    response.sendRedirect("manager-product?action=detail&id=" + productId);
                } else {
                    session.setAttribute("error", "Thêm thất bại!");

                    response.sendRedirect("manager-product?action=detail&id=" + productId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("edit-product?error=invalid-input");
            }
        } else if (action.equals("edit-attribute")) {
            try {
                if (action.equals("edit-attribute")) {
                    // Lấy dữ liệu từ form
                    int attributeId = Integer.parseInt(request.getParameter("attributeId"));
                    String color = request.getParameter("color");
                    String storage = request.getParameter("storage");
                    double extraPrice = Double.parseDouble(request.getParameter("extraPrice"));
                    int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));

                    // Gọi DAO để cập nhật thuộc tính sản phẩm
                    ProductsDAO attributeDAO = new ProductsDAO();
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    boolean isUpdated = attributeDAO.updateAttribute(attributeId, color, storage, extraPrice, stockQuantity,productId);

                    if (isUpdated) {
                        session.setAttribute("message", "Cập nhật thành công!");
                    } else {
                        session.setAttribute("error", "Cập nhật thất bại!");
                    }

                    response.sendRedirect("manager-product?action=detail&id=" + request.getParameter("productId"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Có lỗi xảy ra!");
                request.getRequestDispatcher("manager-product.jsp").forward(request, response);
            }
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
