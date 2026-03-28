/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servlets.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Servlet odpowiedzialny za wyświetlanie listy sprzedanych produktów w aplikacji.
 *
 * @author ida
 */
/*@WebServlet(name = "salesListServlet", urlPatterns = {"/salesListServlet"})
public class salesListServlet {
    
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JPAController jpa = new JPAController();
        jpa.start();

        List<Sales> sales = jpa.getSales();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(sales);

        response.getWriter().write(json);
    }
}*/
