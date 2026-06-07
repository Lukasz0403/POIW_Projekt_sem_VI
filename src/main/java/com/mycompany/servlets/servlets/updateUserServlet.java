package com.mycompany.servlets.servlets;

import com.mycompany.model.JPAController;
import com.mycompany.model.Users;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;

/**
 * Servlet aktualizujący dane istniejącego użytkownika w bazie. Przyjmuje żądania HTTP POST.
 * Przyjmuje dane uzytkownika z formularza jako parametry zakodowane w formacie
 * {@code application/x-www-form-urlencoded}.
 * 
 * <p>Dostęp do tego servletu wymaga aktywnej sesji użytkownika
 * z uprawnieniami co najmniej kierownika (roleId >= 2).</p>
 * 
 * @author Radosław Kruczek
 */
@WebServlet(name = "updateUserServlet", urlPatterns = {"/updateUserServlet"})
public class updateUserServlet extends HttpServlet {

    /**
     * Obsługuje żądanie HTTP POST aktualizacji uzytkownika. 
     * Jeśli nowe hasło zostało podane przed aktualizacją użytkownika zostanie wykonane hashowanie hasła.
     *
     * <p>Oczekiwane parametry żądania:</p>
     * <ul>
     *   <li>{@code newlogin} - nowy login uzytkownika</li>
     *   <li>{@code oldlogin} - stary login uzytkownika</li>
     *   <li>{@code password} - nowe hasło użytkownika</li>
     *   <li>{@code role} — stanowisko uzytkownika</li>
     * </ul>
     *
     * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     *   <li>{@code 202} - użytkownik został pomyślnie zaktualizowany</li>
     *   <li>{@code 401} - brak aktywnej sesji użytkownika</li>
     *   <li>{@code 403} - użytkownik nie posiada wymaganych uprawnień</li>
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
        
        if(request.getParameter("newlogin") == null || request.getParameter("oldlogin") == null || request.getParameter("password") == null || request.getParameter("role") == null) {
            response.sendError(403);
            return;
        }
        
        JPAController jpa = new JPAController();
        
        jpa.start();
        
        System.out.println(request.getParameter("newlogin"));
        System.out.println(request.getParameter("oldlogin"));
        System.out.println(request.getParameter("password"));
        System.out.println(request.getParameter("role"));
        
        Users u = jpa.getUserByName(request.getParameter("oldlogin"));
        
        if(u == null) {
            response.sendError(402);
        }
        else {
            u.setUsername(request.getParameter("newlogin"));
            u.setRole(jpa.getRoleById(Integer.parseInt(request.getParameter("role"))));

            if(!"".equals(request.getParameter("password"))) {

                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

                Hash hash = Password.hash(request.getParameter("password")).addPepper("shared-secret").with(bcrypt);

                u.setPassword(hash.getResult());
            }

            jpa.updateUser(u);

            response.setStatus(202);
        }
    }

    /**
     * Zwraca opis serwletu
     *
     * @return Zwraca Stringa z opisem
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}