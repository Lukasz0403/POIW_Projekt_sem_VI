package com.mycompany.servlets.servlets;

import com.mycompany.model.JPAController;
import com.mycompany.model.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Servlet zwracający listę uzytkowników będących pracownikami z bazy. Przyjmuje żądania HTTP GET.
 * 
 * Zwraca dane w formacie JSON.
 * 
 * <p>Dostęp do tego servletu wymaga aktywnej sesji użytkownika.</p>
 * 
 * @author Radosław Kruczek
 */
@WebServlet(name = "getWorkersServlet", urlPatterns = {"/getWorkersServlet"})
public class getWorkersServlet extends HttpServlet {

    /**
     * Obsługuje żądanie HTTP GET pobrania listy uzytkowników na stanowisku pracownika. 
     *
     * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     *   <li>{@code 202} — użytkownik został pomyślnie dodany lub zaktualizowany</li>
     *   <li>{@code 401} — brak aktywnej sesji użytkownika</li>
     * </ul>
     *
     * @param request  Obiekt {@link HttpServletRequest} zawierający dane żądania HTTP.
     * @param response Obiekt {@link HttpServletResponse} używany do wysłania odpowiedzi HTTP.
     * @throws IOException jeśli wystąpi błąd wejścia/wyjścia podczas przetwarzania żądania.
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
        
        JPAController jpa = new JPAController();
        jpa.start();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        List<Users> u = jpa.getUserWorkers();
        
        String a = "";
        
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            a = mapper.writeValueAsString(u);
        }
        catch (JsonGenerationException | JsonMappingException  e) {
            e.printStackTrace();
        }
        
        response.getWriter().write(a);  
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