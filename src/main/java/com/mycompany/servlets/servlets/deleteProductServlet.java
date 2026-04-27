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
@WebServlet(name = "deleteProductServlet", urlPatterns = {"/deleteProductServlet"})
public class deleteProductServlet extends HttpServlet {

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

        try {

            JPAController jpa = new JPAController();
            jpa.start();

            int id = Integer.parseInt(request.getParameter("id"));

            Products p = jpa.getProductById(id);

            if (p == null) {
                response.sendError(404);
                return;
            }

            jpa.deleteProduct(p);

            response.setStatus(202);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
 
    }
}
