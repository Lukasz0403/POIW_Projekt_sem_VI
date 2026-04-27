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
@WebServlet(name = "addProductServlet", urlPatterns = {"/addProductServlet"})
public class addProductServlet extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        
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

        String name = request.getParameter("name");
        String brand = request.getParameter("brand");
        String categoryName = request.getParameter("category");
        float price = Float.parseFloat(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        
        Products existing = jpa.findProduct(name, brand, categoryName);

        if (existing != null) {

            
            existing.setQuantity(existing.getQuantity() + quantity);

            if (existing.getPrice() != price) {
                existing.setPrice(price);
            }

            jpa.saveOrUpdateProduct(existing);

        } else {

            
            Products p = new Products();
            p.setName(name);
            p.setBrand(brand);
            p.setPrice(price);
            p.setQuantity(quantity);

            
            Categories cat = jpa.findCategoryByName(categoryName);
            p.setCategoryId(cat);

            jpa.saveOrUpdateProduct(p);
        }

        response.setStatus(202);
    }

}
