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
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Radosław
 */
@WebServlet(name = "getSalesServlet", urlPatterns = {"/getSales"})
public class getSalesServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JPAController jpa = new JPAController();
        jpa.start();

        List<Sales> sales = jpa.getSales();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(sales);

        response.getWriter().write(json);
    
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
