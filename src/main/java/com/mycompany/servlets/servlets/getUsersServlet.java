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
import java.util.List;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Servlet zwracający listę uzytkowników z bazy. Przyjmuje żądania HTTP GET.
 * 
 * Zwraca dane w formacie JSON.
 * 
 * <p>Dostęp do tego servletu wymaga aktywnej sesji użytkownika
 * z uprawnieniami co najmniej kierownika (roleId >= 2).</p>
 * 
 * @author Radosław
 */
@WebServlet(name = "getUsersServlet", urlPatterns = {"/getUsersServlet"})
public class getUsersServlet extends HttpServlet {



    /**
     * Obsługuje żądanie HTTP GET pobrania listy uzytkowników. 
     *
     * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     *   <li>{@code 202} — użytkownik został pomyślnie dodany lub zaktualizowany</li>
     *   <li>{@code 401} — brak aktywnej sesji użytkownika</li>
     *   <li>{@code 403} — użytkownik nie posiada wymaganych uprawnień</li>
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
        
        Users user = (Users) session.getAttribute("user");
        if (user.getRole().getRoleId() < 2) {
            response.sendError(403);
            return;
        }
        
        JPAController jpa = new JPAController();
        jpa.start();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        List<Users> u = jpa.getUsers();
        
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
