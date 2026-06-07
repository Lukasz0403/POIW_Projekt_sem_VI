package com.mycompany.servlets.servlets;

import com.mycompany.model.JPAController;
import com.mycompany.model.Products;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
 
/**
 * Servlet obsługujący pobieranie danych pojedynczego produktu na podstawie
 * jego identyfikatora. Zwraca dane produktu w formacie JSON.
 *
 * <p>Dostęp do tego servletu wymaga aktywnej sesji użytkownika.</p>
 *
 * @author Mateusz Gojny
 */
@WebServlet(name = "getProductServlet", urlPatterns = {"/getProductServlet"})
public class getProductServlet extends HttpServlet {
 
    /**
     * Obsługuje żądanie HTTP GET zwracające dane produktu o podanym identyfikatorze.
     *
     * <p>Oczekiwane parametry żądania:</p>
     * <ul>
     *   <li>{@code id} — unikalny identyfikator produktu (liczba całkowita)</li>
     * </ul>
     *
     * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     *   <li>{@code 200} — dane produktu zwrócone pomyślnie w formacie JSON</li>
     *   <li>{@code 401} — brak aktywnej sesji użytkownika</li>
     * </ul>
     *
     * @param request  Obiekt {@link HttpServletRequest} zawierający dane żądania HTTP.
     * @param response Obiekt {@link HttpServletResponse} używany do wysłania odpowiedzi HTTP.
     * @throws ServletException jeśli wystąpi błąd po stronie servletu.
     * @throws IOException      jeśli wystąpi błąd wejścia/wyjścia podczas przetwarzania żądania.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(401);
            return;
        }
 
        JPAController jpa = new JPAController();
        jpa.start();
 
        int id = Integer.parseInt(request.getParameter("id"));
        Products p = jpa.getProductById(id);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(p));
    }
}