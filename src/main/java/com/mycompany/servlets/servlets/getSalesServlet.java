package com.mycompany.servlets.servlets;

import com.mycompany.model.JPAController;
import com.mycompany.model.Sales;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Servlet realizujący pobieranie listy transakcji sprzedaży z bazy danych.
 * 
 * Przyjmuje żądania HTTP GET i zwraca dane w formacie JSON.
 * 
 * <p>Dostęp do tego servletu wymaga aktywnej sesji użytkownika.</p>
 * @author Ida Wszoła
 */
@WebServlet(name = "getSalesServlet", urlPatterns = {"/getSales"})
public class getSalesServlet extends HttpServlet {

    /**
     * Obsługuje żądanie HTTP GET w celu pobrania listy sprzedaży.
     * * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     * <li>{@code 200} — dane zostały pomyślnie zwrócone</li>
     * <li>{@code 401} — brak aktywnej sesji użytkownika</li>
     * </ul>
     *
     * @param request  Obiekt {@link HttpServletRequest} zawierający dane żądania HTTP.
     * @param response Obiekt {@link HttpServletResponse} używany do wysłania odpowiedzi HTTP.
     * @throws IOException      jeśli wystąpi błąd wejścia/wyjścia podczas przetwarzania żądania.
     * @throws ServletException jeśli wystąpi błąd po stronie servletu.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(401);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JPAController jpa = new JPAController();
        jpa.start();

        List<Sales> sales = jpa.getSales();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(sales);

        response.getWriter().write(json);
    
    }

    /**
     * Zwraca krótki opis servletu.
     *
     * @return a String zawierający opis funkcjonalności servletu
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}