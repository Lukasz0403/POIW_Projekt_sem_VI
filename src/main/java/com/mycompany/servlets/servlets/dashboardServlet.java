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
import java.time.LocalDate;
import java.util.Date;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * Servlet odpowiedzialny za wyświetlanie głównego widoku aplikacji (dashboardu)
 * po zalogowaniu użytkownika.
 *
 * 
 * Servlet pobiera dane aktualnie zalogowanego użytkownika z sesji HTTP
 * i generuje dynamiczny widok HTML zawierający:
 * 
 *   powitanie użytkownika,
 *   dane użytkownika (imię, nazwisko, ID, login),
 *   datę logowania,
 *   informację o zakresie uprawnień.
 * 
 *
 *
 * Jeśli w sesji nie znajduje się obiekt użytkownika (brak zalogowania),
 * servlet zwraca komunikat informujący o braku autoryzacji.
 *
 * 
 * Wygenerowany HTML jest przeznaczony do dynamicznego wstawienia
 * w głównym kontenerze aplikacji (np. przy użyciu fetch API).
 *
 * @author Mateusz Gojny
 */
@WebServlet(name = "dashboardServlet", urlPatterns = {"/dashboardServlet"})
public class dashboardServlet extends HttpServlet {

    /**
     * Obsługuje żądanie HTTP GET.
     * 
     * Pobiera obiekt użytkownika z sesji ({@link HttpSession}),
     * a następnie generuje widok dashboardu zawierający jego dane
     * oraz bieżącą datę logowania.
     *
     * @param request obiekt żądania HTTP, zawierający sesję użytkownika
     * @param response obiekt odpowiedzi HTTP, do którego zapisywany jest HTML
     * @throws IOException w przypadku błędu zapisu odpowiedzi
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        
        Users user = (Users) session.getAttribute("user");
        
        
        ObjectMapper mapper = new ObjectMapper();
        
        String u = "";
        String d = "";
        
        try {
            u = mapper.writeValueAsString(user);
        }
        catch (JsonGenerationException | JsonMappingException  e) {
            // catch various errors
            e.printStackTrace();
        }
        
        System.out.println(u);
        
        
        response.getWriter().write(u);

    }

}
