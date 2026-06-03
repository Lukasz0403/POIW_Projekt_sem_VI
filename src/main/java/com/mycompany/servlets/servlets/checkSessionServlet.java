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
 * Servlet sprawdzający istnienie sesji po wejściu na stronę przez użytkownika.
 * 
 * @author Radosław
 */
@WebServlet(name = "checkSessionServlet", urlPatterns = {"/checkSession"})
public class checkSessionServlet extends HttpServlet {

    /**
     * Obsługuje żądanie HTTP POST weryfikacji sesji użytkownika. 
     *
     *
     * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     *   <li>{@code 202} — Sesja istnieje więc uzytkownik jest zalogowany.</li>
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
        
        if(session == null) {
            
            response.sendError(401);
                
        } else {
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