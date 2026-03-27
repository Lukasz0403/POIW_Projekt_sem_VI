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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Servlet odpowiedzialny za wyświetlanie listy produktów w aplikacji.
 * 
 * Generuje dynamiczny widok HTML zawierający:
 * 
 *panel filtrów (kategoria, maksymalna cena),
 *tabelę produktów,
 *linki do szczegółów produktu.
 * 
 *
 * 
 * Dane produktów są generowane na podstawie przykładowej listy
 * zwracanej przez metodę {@code createSampleListOfProduct()} klasy {@link Product}.
 *
 * 
 * Servlet obsługuje filtrowanie danych na podstawie parametrów zapytania:
 *
 *{@code kategoria} – filtruje produkty po kategorii (case-insensitive),
 *{@code cena} – filtruje produkty o cenie mniejszej lub równej podanej wartości.
 * 
 *
 * 
 * Wynikowy HTML jest zwracany jako odpowiedź typu {@code text/html}
 * i przeznaczony do dynamicznego wstawienia do elementu DOM po stronie klienta (np. przez fetch API).
 *
 * @author Mateusz Gojny
 */
@WebServlet(name = "productListServlet", urlPatterns = {"/productListServlet"})
public class productListServlet extends HttpServlet {

    /**
     * Obsługuje żądanie HTTP GET.
     * 
     * Pobiera parametry filtrowania z requestu, generuje listę produktów,
     * filtruje ją zgodnie z parametrami oraz buduje dynamiczny HTML zawierający:
     * panel filtrów i tabelę wyników.
     *
     * @param request obiekt zawierający dane żądania HTTP, w tym parametry:
     *                {@code kategoria} oraz {@code cena}
     * @param response obiekt odpowiedzi HTTP, do którego zapisywany jest wygenerowany HTML
     * @throws IOException w przypadku błędu zapisu odpowiedzi
     */
   @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

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
