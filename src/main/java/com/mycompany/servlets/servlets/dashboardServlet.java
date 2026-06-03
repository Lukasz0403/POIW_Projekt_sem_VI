package com.mycompany.servlets.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
 * <p>Servlet pobiera dane aktualnie zalogowanego użytkownika z sesji HTTP
 * i zwraca je w formacie JSON jako tablicę zawierającą:</p>
 * <ul>
 *   <li>obiekt zalogowanego użytkownika ({@link Users})</li>
 *   <li>datę logowania ({@link LocalDate})</li>
 * </ul>
 *
 * <p>Jeśli w sesji nie znajduje się obiekt użytkownika (brak zalogowania),
 * servlet zwraca komunikat informujący o braku autoryzacji.</p>
 *
 * <p>Wygenerowany JSON jest przeznaczony do dynamicznego wstawienia
 * w głównym kontenerze aplikacji (np. przy użyciu fetch API).</p>
 *
 * @author Mateusz Gojny i Radosław Kruczek
 */
@WebServlet(name = "dashboardServlet", urlPatterns = {"/dashboardServlet"})
public class dashboardServlet extends HttpServlet {
 
    /**
     * Obsługuje żądanie HTTP GET zwracające dane zalogowanego użytkownika
     * oraz datę jego logowania w formacie JSON.
     *
     * <p>Odpowiedź jest tablicą JSON zawierającą dwa elementy:</p>
     * <ol>
     *   <li>Obiekt użytkownika pobrany z atrybutu sesji {@code "user"}</li>
     *   <li>Data logowania pobrana z atrybutu sesji {@code "loginDate"}</li>
     * </ol>
     *
     * @param request  Obiekt {@link HttpServletRequest} zawierający dane żądania HTTP.
     * @param response Obiekt {@link HttpServletResponse} używany do wysłania odpowiedzi HTTP.
     * @throws IOException jeśli wystąpi błąd wejścia/wyjścia podczas przetwarzania żądania.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(401);
            return;
        } 
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
 
        Users user = (Users) session.getAttribute("user");
        LocalDate date = (LocalDate) session.getAttribute("loginDate");
 
        ObjectMapper mapper = new ObjectMapper();
 
        List<Object> l1 = new ArrayList<>();
        l1.add(user);
        l1.add(date);
 
        String a = "";
 
        try {
            a = mapper.writeValueAsString(l1);
        } catch (JsonGenerationException | JsonMappingException e) {
            e.printStackTrace();
        }
 
        response.getWriter().write(a);
    }
}