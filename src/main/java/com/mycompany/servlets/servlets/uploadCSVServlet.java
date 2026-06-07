package com.mycompany.servlets.servlets;

import com.mycompany.model.JPAController;
import com.mycompany.model.Users;
import com.mycompany.model.Products;
import com.mycompany.model.Categories;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;


/**
 * Servlet obsługujący import produktów z pliku CSV do bazy danych.
 * Przyjmuje żądania HTTP POST z plikiem przesłanym jako dane wieloczęściowe
 * ({@code multipart/form-data}).
 *
 * <p>Oczekiwany format pliku CSV (separator: średnik {@code ;}):</p>
 * <pre>
 *   Filtr oleju;Bosch;Filtry;45.50;30
 * </pre>
 *
 * <p>Plik nie powinien zawierać wiersza nagłówkowego — każda linia
 * traktowana jest jako dane produktu.
 * Jeśli produkt o podanej nazwie, marce i kategorii już istnieje,
 * jego ilość zostaje zwiększona, a cena zaktualizowana.
 * Wiersze z kategorią nieistniejącą w bazie są pomijane.</p>
 *
 * <p>Dostęp do tego servletu wymaga aktywnej sesji użytkownika
 * z uprawnieniami co najmniej kierownika (roleId >= 2).</p>
 *
 * @author Mateusz Gojny
 */
@WebServlet(name = "uploadCSVServlet", urlPatterns = {"/uploadCSVServlet"})
@MultipartConfig
public class uploadCSVServlet extends HttpServlet {

    /**
     * Obsługuje żądanie HTTP POST importu produktów z przesłanego pliku CSV.
     *
     * <p>Oczekiwane dane żądania:</p>
     * <ul>
     *   <li>{@code csv_file} — plik CSV przesłany jako część wieloczęściowego żądania</li>
     * </ul>
     *
     * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     *   <li>{@code 202} — import zakończony pomyślnie</li>
     *   <li>{@code 401} — brak aktywnej sesji użytkownika</li>
     *   <li>{@code 403} — użytkownik nie posiada wymaganych uprawnień</li>
     * </ul>
     *
     * @param request  Obiekt {@link HttpServletRequest} zawierający dane żądania HTTP.
     * @param response Obiekt {@link HttpServletResponse} używany do wysłania odpowiedzi HTTP.
     * @throws ServletException jeśli wystąpi błąd po stronie servletu.
     * @throws IOException      jeśli wystąpi błąd wejścia/wyjścia podczas przetwarzania żądania.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
        Part filePart = request.getPart("csv_file");
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(filePart.getInputStream(), "UTF-8")
        );
        String line;
        while ((line = reader.readLine()) != null) {
            String[] cols = line.split(";");
            if (cols.length < 5) continue;
            String name     = cols[0].trim();
            String brand    = cols[1].trim();
            String catName  = cols[2].trim();
            float price     = Float.parseFloat(cols[3].trim());
            int quantity    = Integer.parseInt(cols[4].trim());
            Categories cat = jpa.findManyCategoriesByName(catName);
            if (cat == null) continue;

            Products existing = jpa.findProduct(name, brand, catName);
            if (existing != null) {
                existing.setPrice(price);
                existing.setQuantity(existing.getQuantity() + quantity);
                jpa.saveOrUpdateProduct(existing);
            } else {
                Products p = new Products();
                p.setName(name);
                p.setBrand(brand);
                p.setCategoryId(cat);
                p.setPrice(price);
                p.setQuantity(quantity);
                jpa.saveProduct(p);
            }
        }
        response.setStatus(202);
    }
}