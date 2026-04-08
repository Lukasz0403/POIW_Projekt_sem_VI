/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.servlets.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author mateu
 */
@WebServlet(name = "uploadCSVServlet", urlPatterns = {"/uploadCSVServlet"})
@MultipartConfig
public class uploadCSVServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JPAController jpa = new JPAController();
        jpa.start();

        Part filePart = request.getPart("csv_file");
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(filePart.getInputStream(), "UTF-8")
        );

        String line;
        boolean firstLine = true;

        while ((line = reader.readLine()) != null) {
            // pomijamy nagłówek
            if (firstLine) { firstLine = false; continue; }

            String[] cols = line.split(";");
            if (cols.length < 5) continue;

            String name     = cols[0].trim();
            String brand    = cols[1].trim();
            String catName  = cols[2].trim();
            float price     = Float.parseFloat(cols[3].trim());
            int quantity    = Integer.parseInt(cols[4].trim());

            Categories cat = jpa.findManyCategoriesByName(catName);
            if (cat == null) continue; 

            
            Products existing = jpa.findProduct(name, brand, catName);
            if (existing != null) {
                existing.setPrice(price);
                existing.setQuantity(existing.getQuantity() + quantity);
                jpa.saveOrUpdateProduct(existing);
            } else {
                Products p = new Products();
                p.setName(name);
                p.setBrand(brand);
                p.setCategoryId(cat);
                p.setPrice(price);
                p.setQuantity(quantity);
                jpa.saveProduct(p);
            }
        }

        response.setStatus(202);
    }

}
