/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.servlets.servlets;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
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
        
        if(request.getParameter("login") == null || request.getParameter("password") == null || request.getParameter("role") == null) {
            response.sendError(403);
            return;
        }
        
        JPAController jpa = new JPAController();
        
        jpa.start();
        
        System.out.println(request.getParameter("login"));
        System.out.println(request.getParameter("password"));
        System.out.println(request.getParameter("role"));
        
        Users u = new Users();
        
        BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

        Hash hash = Password.hash(request.getParameter("password")).addPepper("shared-secret").with(bcrypt);
        
        System.out.println(hash.getResult());
        
        u.setUsername(request.getParameter("login"));
        u.setPassword(hash.getResult());
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
