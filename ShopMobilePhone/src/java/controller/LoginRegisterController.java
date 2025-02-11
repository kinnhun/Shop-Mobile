/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import model.Users;

/**
 *
 * @author trung
 */
@MultipartConfig()

@WebServlet(name = "LoginRegisterController", urlPatterns = {"/login-register"})
public class LoginRegisterController extends HttpServlet {

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
            out.println("<title>Servlet LoginRegisterController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginRegisterController at " + request.getContextPath() + "</h1>");
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

        if (request.getParameter("register") != null) {
            if (request.getParameter("register").equals("true")) {
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

        }
        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("logout")) {
                request.getSession().invalidate();
                request.setAttribute("message", "Successfully logged!");
                request.getRequestDispatcher("home").forward(request, response);
                request.removeAttribute("message");

                return;

            }
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
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

        if ("login".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            UserDAO userDAO = new UserDAO();
            Users user = userDAO.authenticateUser(username, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);

                if (user.getRole().equals("customer")) {
                    response.sendRedirect("home");

                } else if (user.getRole().equals("admin")) {
                    response.sendRedirect("dashboard");

                }

            } else {
                request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
                doGet(request, response);
                request.removeAttribute("error");
                return;
            }
        } else if ("register".equals(action)) {
            String fullName = request.getParameter("full_name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String password = request.getParameter("password");
            Part avatarPart = request.getPart("avatar");

            UserDAO userDAO = new UserDAO();

            // Xử lý upload ảnh đại diện
            String avatarFileName = null;
            if (avatarPart != null && avatarPart.getSize() > 0) {
                String uploadDir = getServletContext().getRealPath("/uploads");
                File uploadFolder = new File(uploadDir);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdir();
                }

                avatarFileName = System.currentTimeMillis() + "_" + Paths.get(avatarPart.getSubmittedFileName()).getFileName();
                File avatarFile = new File(uploadDir, avatarFileName);
                Files.copy(avatarPart.getInputStream(), avatarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            // Mã hóa mật khẩu (tuỳ vào cách bạn lưu trữ)
            String hashedPassword = password; // Thay thế bằng phương thức mã hóa nếu cần

            // Tạo đối tượng User và lưu vào DB
            boolean isRegistered = userDAO.registerUser(fullName, password, phone, email, address, avatarFileName, "customer");

            if (isRegistered) {
                request.setAttribute("message", "Đăng ký thành công! Hãy đăng nhập.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Đăng ký thất bại, vui lòng thử lại!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
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
