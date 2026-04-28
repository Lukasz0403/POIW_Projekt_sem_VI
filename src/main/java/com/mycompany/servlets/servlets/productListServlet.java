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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.codehaus.jackson.map.ObjectMapper;
 
/**
 * Servlet obsługujący pobieranie listy wszystkich produktów dostępnych
 * w systemie. Zwraca dane w formacie JSON zakodowanym w UTF-8.
 *
 * <p>Dostęp do tego servletu wymaga aktywnej sesji użytkownika.</p>
 *
 * @author Mateusz Gojny
 */
@WebServlet(name = "productListServlet", urlPatterns = {"/productListServlet"})
public class productListServlet extends HttpServlet {
 
    /**
     * Obsługuje żądanie HTTP GET zwracające listę wszystkich produktów
     * w formacie JSON.
     *
     * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     *   <li>{@code 200} — lista produktów zwrócona pomyślnie w formacie JSON</li>
     *   <li>{@code 401} — brak aktywnej sesji użytkownika</li>
     * </ul>
     *
     * @param request  Obiekt {@link HttpServletRequest} zawierający dane żądania HTTP.
     * @param response Obiekt {@link HttpServletResponse} używany do wysłania odpowiedzi HTTP.
     * @throws IOException jeśli wystąpi błąd wejścia/wyjścia podczas przetwarzania żądania.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
 
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(401);
            return;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JPAController jpa = new JPAController();
        jpa.start();
        List<Products> products = jpa.getProducts();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(products);
        response.getWriter().write(json);
    }
}