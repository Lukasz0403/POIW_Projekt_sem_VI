/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.servlets.servlets;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet dodający nowego uzytkownika do bazy. Przyjmuje żądania HTTP POST.
 * Przyjmuje dane uzytkownika z formularza jako parametry zakodowane w formacie
 * {@code application/x-www-form-urlencoded}.
 * 
 * <p>Dostęp do tego servletu wymaga aktywnej sesji użytkownika
 * z uprawnieniami co najmniej kierownika (roleId >= 2).</p>
 * 
 * @author Radosław
 */
@WebServlet(name = "addUserServlet", urlPatterns = {"/addUserServlet"})
public class addUserServlet extends HttpServlet {





    /**
     * Obsługuje żądanie HTTP POST dodania użytkownika. 
     * Przed dodaniem uzytkownika wykonuje hashowanie hasła.
     *
     * <p>Oczekiwane parametry żądania:</p>
     * <ul>
     *   <li>{@code login} — login uzytkownika</li>
     *   <li>{@code password} — hasło użytkownika</li>
     *   <li>{@code role} — stanowisko uzytkownika</li>
     * </ul>
     *
     * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     *   <li>{@code 202} — użytkownik został pomyślnie dodany</li>
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
        
        if(request.getParameter("login") == null || request.getParameter("password") == null || request.getParameter("role") == null) {
            response.sendError(403);
            return;
        }
        
        JPAController jpa = new JPAController();
        
        jpa.start();
        
        System.out.println(request.getParameter("login"));
        System.out.println(request.getParameter("password"));
        System.out.println(request.getParameter("role"));
        
        Users u = new Users();
        
        BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

        Hash hash = Password.hash(request.getParameter("password")).addPepper("shared-secret").with(bcrypt);
        
        System.out.println(hash.getResult());
        
        u.setUsername(request.getParameter("login"));
        u.setPassword(hash.getResult());
        u.setRole(jpa.getRoleById(Integer.parseInt(request.getParameter("role"))));
        
        jpa.saveUser(u);
        
        response.setStatus(202);
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
