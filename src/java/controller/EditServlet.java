/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Product;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.DBContext;
import model.ProductDAO;

/**
 *
 * @author Thanh Dang
 */
public class EditServlet extends HttpServlet {

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

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        try {
            String idParam = request.getParameter("id");
            int id = Integer.parseInt(idParam);

            // Begin to set image path
            DBContext dbContext = new DBContext();
            String imagePath = dbContext.getImagePath();
            request.setAttribute("imagePath", imagePath);
            // End of setting image path

            // Begin to set product information
            ProductDAO productDao = new ProductDAO();
            Product p = productDao.getProductById(id);
            request.setAttribute("product", p);
            // End of setting product information

            request.getRequestDispatcher("Edit.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("error.html");
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
        response.setContentType("text/html;charset=UTF-8");
        try {
            // Begin to set image path
            DBContext dbContext = new DBContext();
            String imagePath = dbContext.getImagePath();
            request.setAttribute("imagePath", imagePath);
            // End of setting image path

            // Begin to update product
            int id = Integer.parseInt(request.getParameter("id"));
            ProductDAO productDao = new ProductDAO();
            Product p = productDao.getProductById(id);
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));

            // Check if image is uploaded
            Part part = request.getPart("image");
            String filename = part.getSubmittedFileName();

            if (filename == null || filename.equals("")) {
                productDao.updateProduct(new Product(id, name, p.getImage(), price));
            } else {
                // Check if image already exist
                productDao.updateProduct(new Product(id, name, filename, price));
            }
            // End of updating product
            
            response.sendRedirect("Home");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
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
