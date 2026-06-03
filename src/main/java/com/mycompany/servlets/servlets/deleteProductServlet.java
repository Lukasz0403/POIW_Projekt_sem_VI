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
 * Servlet obsługujący usuwanie produktów z bazy danych.
 * Przyjmuje żądania HTTP POST zawierające identyfikator produktu
 * do usunięcia jako parametr żądania.
 *
 * <p>Dostęp do tego servletu wymaga aktywnej sesji użytkownika
 * z uprawnieniami co najmniej kierownika (roleId >= 2).</p>
 *
 * @author Mateusz Gojny
 */
@WebServlet(name = "deleteProductServlet", urlPatterns = {"/deleteProductServlet"})
public class deleteProductServlet extends HttpServlet {
 
    /**
     * Obsługuje żądanie HTTP POST usunięcia produktu o podanym identyfikatorze.
     *
     * <p>Oczekiwane parametry żądania:</p>
     * <ul>
     *   <li>{@code id} — unikalny identyfikator produktu do usunięcia (liczba całkowita)</li>
     * </ul>
     *
     * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     *   <li>{@code 202} — produkt został pomyślnie usunięty</li>
     *   <li>{@code 401} — brak aktywnej sesji użytkownika</li>
     *   <li>{@code 403} — użytkownik nie posiada wymaganych uprawnień</li>
     *   <li>{@code 404} — produkt o podanym ID nie istnieje w bazie danych</li>
     *   <li>{@code 500} — wewnętrzny błąd serwera podczas usuwania produktu</li>
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
        try {
            JPAController jpa = new JPAController();
            jpa.start();
            int id = Integer.parseInt(request.getParameter("id"));
            Products p = jpa.getProductById(id);
            if (p == null) {
                response.sendError(404);
                return;
            }
            jpa.deleteProduct(p);
            response.setStatus(202);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
}