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
import java.util.ArrayList;
import java.util.List;
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
 * @author Mateusz Gojny i Radosław Kruczek
 */
@WebServlet(name = "dashboardServlet", urlPatterns = {"/dashboardServlet"})
public class dashboardServlet extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        
        Users user = (Users) session.getAttribute("user");
        
        LocalDate date = (LocalDate) session.getAttribute("loginDate");
        
        ObjectMapper mapper = new ObjectMapper();
        
        List<Object> l1 = new ArrayList<>();
        l1.add(user);
        l1.add(date);
        
        String a = "";
        
        try {
            a = mapper.writeValueAsString(l1);
        }
        catch (JsonGenerationException | JsonMappingException  e) {
            e.printStackTrace();
        }
        
        response.getWriter().write(a);

    }

}
