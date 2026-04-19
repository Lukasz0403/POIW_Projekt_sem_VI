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

/**
 *
 * @author Radosław
 */
@WebServlet(name = "updateUserServlet", urlPatterns = {"/updateUserServlet"})
public class updateUserServlet extends HttpServlet {




    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        JPAController jpa = new JPAController();
        
        jpa.start();
        
        System.out.println(request.getParameter("newlogin"));
        System.out.println(request.getParameter("oldlogin"));
        System.out.println(request.getParameter("password"));
        System.out.println(request.getParameter("role"));
        
        Users u = jpa.getUserByName(request.getParameter("oldlogin"));
        
        if(u == null) {
            response.sendError(402);
        }
        else {
            u.setUsername(request.getParameter("newlogin"));
            u.setRole(jpa.getRoleById(Integer.parseInt(request.getParameter("role"))));

            if(!"".equals(request.getParameter("password"))) {

                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

                Hash hash = Password.hash(request.getParameter("password")).addPepper("shared-secret").with(bcrypt);

                u.setPassword(hash.getResult());
            }

            jpa.updateUser(u);

            response.setStatus(202);
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
