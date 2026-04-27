/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.servlets.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Mateusz Gojny
 */
@WebServlet(name = "updateProductServlet", urlPatterns = {"/updateProductServlet"})
public class updateProductServlet extends HttpServlet {
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(401);
            return;
        }
        Users user = (Users) session.getAttribute("user");
        if (user.getRole().getRoleId() < 2) {
            response.sendError(403);
            return;
        }
        
        
         JPAController jpa = new JPAController();
         jpa.start();
        
         int id = Integer.parseInt(request.getParameter("id"));

         Products p = jpa.getProductById(id);

         p.setName(request.getParameter("name"));
         p.setBrand(request.getParameter("brand"));
         p.setPrice(Float.parseFloat(request.getParameter("price")));
         p.setQuantity(Integer.parseInt(request.getParameter("quantity")));

         Categories cat = jpa.findCategoryByName(request.getParameter("category"));
         p.setCategoryId(cat);

         jpa.saveOrUpdateProduct(p);

         response.setStatus(202);
        
    }

}
