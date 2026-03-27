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

/**
 *
 * @author mateu
 */
@WebServlet(name = "addProductServlet", urlPatterns = {"/addProductServlet"})
public class addProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        JPAController jpa = new JPAController();
        jpa.start();

        String name = request.getParameter("name");
        String brand = request.getParameter("brand");
        String categoryName = request.getParameter("category");
        float price;
        int quantity;
        try {
             price = Float.parseFloat(request.getParameter("price"));
             quantity = Integer.parseInt(request.getParameter("quantity"));

            if (price < 0 || quantity <= 0) {
                response.sendError(400);
                return;
            }

        } catch (Exception e) {
            response.sendError(400);
            return;
        }

        try {

            Categories category = jpa.getCategoryByName(categoryName);

            Products product = new Products();
            product.setName(name);
            product.setBrand(brand);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setCategoryId(category);

            jpa.saveProduct(product);

            response.setStatus(202);

        } catch(Exception e){
            e.printStackTrace();
            response.sendError(500);
        }
    }

}
