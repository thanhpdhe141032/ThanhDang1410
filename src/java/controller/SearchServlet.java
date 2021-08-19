/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Product;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DBContext;
import model.ProductDAO;

/**
 *
 * @author Thanh Dang
 */
public class SearchServlet extends HttpServlet {

    public int visit = 0;
    public void init() {
        visit = 1;
    }
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
        try  {
            visit = Integer.parseInt(request.getParameter("visit"));
            visit++;
            // Begin paging
            String searchStr = request.getParameter("searchStr");
            String rawIndex = request.getParameter("index");
            if (rawIndex == null) {
                rawIndex = "1";
            }
            
            int pageIndex = Integer.parseInt(rawIndex);
            ProductDAO pDao = new ProductDAO();
            
            int total = pDao.countRecord(searchStr);
            int pageSize = 5;
            int maxPage = total/pageSize;
            if (total % pageSize != 0) {
                maxPage++;
            }
            
            List<Product> listSearch = pDao.search(searchStr, pageIndex, pageSize);
            request.setAttribute("searchProduct", listSearch);
            request.setAttribute("maxPage", maxPage);
            request.setAttribute("searchStr", searchStr);
            request.setAttribute("index", pageIndex);
            request.setAttribute("visit", visit);
            // End of paging
            
            // Begin to set image path
            DBContext dbContext = new DBContext();
            String imagePath = dbContext.getImagePath();
            request.setAttribute("imagePath", imagePath);
            // End of setting image path
            
            request.getRequestDispatcher("SearchResult.jsp").forward(request, response);
        }  catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Error.html");
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
        processRequest(request, response);
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
