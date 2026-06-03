package com.mycompany.servlets.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
 
/**
 * Servlet obsługujący dodawanie nowych produktów do bazy danych.
 * Przyjmuje żądania HTTP POST zawierające dane produktu przesłane
 * z formularza jako parametry zakodowane w formacie
 * {@code application/x-www-form-urlencoded}.
 *
 * <p>Dostęp do tego servletu wymaga aktywnej sesji użytkownika
 * z uprawnieniami co najmniej kierownika (roleId >= 2).</p>
 *
 * <p>Jeśli produkt o podanej nazwie, marce i kategorii już istnieje
 * w bazie, jego ilość zostaje zwiększona o podaną wartość, a cena
 * zaktualizowana jeśli uległa zmianie. W przeciwnym razie tworzony
 * jest nowy rekord produktu.</p>
 *
 * @author Mateusz Gojny
 */
@WebServlet(name = "addProductServlet", urlPatterns = {"/addProductServlet"})
public class addProductServlet extends HttpServlet {
 
    /**
     * Obsługuje żądanie HTTP POST dodania lub aktualizacji produktu.
     *
     * <p>Oczekiwane parametry żądania:</p>
     * <ul>
     *   <li>{@code name} — nazwa produktu</li>
     *   <li>{@code brand} — marka produktu</li>
     *   <li>{@code category} — nazwa kategorii produktu</li>
     *   <li>{@code price} — cena produktu (wartość zmiennoprzecinkowa)</li>
     *   <li>{@code quantity} — ilość produktu (liczba całkowita)</li>
     * </ul>
     *
     * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     *   <li>{@code 202} — produkt został pomyślnie dodany lub zaktualizowany</li>
     *   <li>{@code 401} — brak aktywnej sesji użytkownika</li>
     *   <li>{@code 403} — użytkownik nie posiada wymaganych uprawnień</li>
     * </ul>
     *
     * @param request  Obiekt {@link HttpServletRequest} zawierający dane żądania HTTP.
     * @param response Obiekt {@link HttpServletResponse} używany do wysłania odpowiedzi HTTP.
     * @throws IOException jeśli wystąpi błąd wejścia/wyjścia podczas przetwarzania żądania.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
 
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
        String name = request.getParameter("name");
        String brand = request.getParameter("brand");
        String categoryName = request.getParameter("category");
        float price = Float.parseFloat(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
 
        Products existing = jpa.findProduct(name, brand, categoryName);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            if (existing.getPrice() != price) {
                existing.setPrice(price);
            }
            jpa.saveOrUpdateProduct(existing);
        } else {
            Products p = new Products();
            p.setName(name);
            p.setBrand(brand);
            p.setPrice(price);
            p.setQuantity(quantity);
            Categories cat = jpa.findCategoryByName(categoryName);
            p.setCategoryId(cat);
            jpa.saveOrUpdateProduct(p);
        }
        response.setStatus(202);
    }
}