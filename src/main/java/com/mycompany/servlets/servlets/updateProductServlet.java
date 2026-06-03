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
 * Servlet obsługujący aktualizację danych istniejącego produktu w bazie danych.
 * Przyjmuje żądania HTTP POST zawierające zaktualizowane dane produktu
 * jako parametry zakodowane w formacie {@code application/x-www-form-urlencoded}.
 *
 * <p>Dostęp do tego servletu wymaga aktywnej sesji użytkownika
 * z uprawnieniami co najmniej kierownika (roleId >= 2).</p>
 *
 * @author Mateusz Gojny
 */
@WebServlet(name = "updateProductServlet", urlPatterns = {"/updateProductServlet"})
public class updateProductServlet extends HttpServlet {
 
    /**
     * Obsługuje żądanie HTTP POST aktualizacji danych produktu o podanym identyfikatorze.
     *
     * <p>Oczekiwane parametry żądania:</p>
     * <ul>
     *   <li>{@code id}       — unikalny identyfikator produktu (liczba całkowita)</li>
     *   <li>{@code name}     — nowa nazwa produktu</li>
     *   <li>{@code brand}    — nowa marka produktu</li>
     *   <li>{@code category} — nazwa nowej kategorii produktu</li>
     *   <li>{@code price}    — nowa cena produktu (wartość zmiennoprzecinkowa)</li>
     *   <li>{@code quantity} — nowa ilość produktu (liczba całkowita)</li>
     * </ul>
     *
     * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     *   <li>{@code 202} — produkt został pomyślnie zaktualizowany</li>
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
 
        int id = Integer.parseInt(request.getParameter("id"));
        Products p = jpa.getProductById(id);
        p.setName(request.getParameter("name"));
        p.setBrand(request.getParameter("brand"));
        p.setPrice(Float.parseFloat(request.getParameter("price")));
        p.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        Categories cat = jpa.findCategoryByName(request.getParameter("category"));
        p.setCategoryId(cat);
        jpa.saveOrUpdateProduct(p);
        response.setStatus(202);
    }
}