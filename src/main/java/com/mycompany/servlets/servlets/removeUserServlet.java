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
 * Servlet usuwający użytkownika z bazy. Przyjmuje żądania HTTP POST.
 * Przyjmuje dane uzytkownika z formularza jako parametry zakodowane w formacie
 * {@code application/x-www-form-urlencoded}.
 * 
 * <p>Dostęp do tego servletu wymaga aktywnej sesji użytkownika.</p>
 * 
 * @author Radosław
 */
@WebServlet(name = "removeUserServlet", urlPatterns = {"/removeUserServlet"})
public class removeUserServlet extends HttpServlet {

    
    /**
     * Obsługuje żądanie HTTP POST usuwania uzytkownika. 
     * Jeśli nowe hasło zostało podane przed aktualizacją użytkownika zostanie wykonane hashowanie hasła.
     *
     * <p>Oczekiwane parametry żądania:</p>
     * <ul>
     *   <li>{@code login} — login uzytkownika</li>
     * </ul>
     *
     * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     *   <li>{@code 202} — użytkownik został pomyślnie usunięty</li>
     *   <li>{@code 401} — brak aktywnej sesji użytkownika</li>
     * </ul>
     *
     * @param request  Obiekt {@link HttpServletRequest} zawierający dane żądania HTTP.
     * @param response Obiekt {@link HttpServletResponse} używany do wysłania odpowiedzi HTTP.
     * @throws IOException jeśli wystąpi błąd wejścia/wyjścia podczas przetwarzania żądania.
     * @throws ServletException jeśli wystąpi błąd po stronie servletu.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(401);
            return;
        }
       
        JPAController jpa = new JPAController();
        
        jpa.start();
        
        System.out.println(request.getParameter("login"));
        
        Users u = jpa.getUserByName(request.getParameter("login"));

        jpa.removeUser(u);
        
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
