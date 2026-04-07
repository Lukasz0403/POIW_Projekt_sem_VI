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
 * @author Radosław
 */
@WebServlet(name = "addUserServlet", urlPatterns = {"/addUserServlet"})
public class addUserServlet extends HttpServlet {





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
        
        JPAController jpa = new JPAController();
        
        jpa.start();
        
        System.out.println(request.getParameter("login"));
        System.out.println(request.getParameter("password"));
        System.out.println(request.getParameter("role"));
        
        Users u = new Users();
        
        u.setUsername(request.getParameter("login"));
        u.setPassword(request.getParameter("password"));
        u.setRole(jpa.getRoleById(Integer.parseInt(request.getParameter("role"))));
        
        jpa.saveUser(u);
        
        response.setStatus(202);
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
