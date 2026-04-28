/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.servlets.servlets;
import com.password4j.BcryptFunction;
import com.password4j.Password;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 
/**
 * Servlet obsługujący procedurę logowania użytkownika do systemu.
 * Weryfikuje podane dane uwierzytelniające względem bazy danych,
 * a w przypadku powodzenia tworzy sesję HTTP i zapisuje w niej
 * dane zalogowanego użytkownika oraz datę logowania.
 *
 * <p>Hasła są przechowywane w bazie jako skróty BCrypt z dodatkowym
 * zabezpieczeniem w postaci współdzielonego "pepper".</p>
 *
 * @author Mateusz Gojny i Radosław Kruczek
 */
@WebServlet(name = "loginProcedureServlet", urlPatterns = {"/loginProcedureServlet"})
public class loginProcedureServlet extends HttpServlet {
 
    /**
     * Obsługuje żądanie HTTP POST weryfikujące dane logowania użytkownika.
     *
     * <p>Oczekiwane parametry żądania:</p>
     * <ul>
     *   <li>{@code login} — nazwa użytkownika</li>
     *   <li>{@code pass} — hasło użytkownika w postaci jawnej (weryfikowane względem skrótu BCrypt)</li>
     * </ul>
     *
     * <p>W przypadku pomyślnego logowania w sesji HTTP zapisywane są atrybuty:</p>
     * <ul>
     *   <li>{@code "user"} — obiekt {@link Users} reprezentujący zalogowanego użytkownika</li>
     *   <li>{@code "loginDate"} — data logowania jako {@link LocalDate}</li>
     * </ul>
     *
     * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     *   <li>{@code 202} — logowanie zakończone sukcesem, sesja została utworzona</li>
     *   <li>{@code 401} — nieprawidłowe dane logowania</li>
     *   <li>{@code 404} — użytkownik o podanej nazwie nie istnieje w bazie danych</li>
     * </ul>
     *
     * @param request  Obiekt {@link HttpServletRequest} zawierający dane żądania HTTP.
     * @param response Obiekt {@link HttpServletResponse} używany do wysłania odpowiedzi HTTP.
     * @throws IOException jeśli wystąpi błąd wejścia/wyjścia podczas przetwarzania żądania.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
 
        JPAController jpa = new JPAController();
        jpa.start();
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        Users u = jpa.getUserByName(login);
        if (u == null) {
            response.sendError(404);
        }
 
        String hash = u.getPassword();
        BcryptFunction bcrypt = BcryptFunction.getInstanceFromHash(hash);
        boolean verified = Password.check(password, hash)
                           .addPepper("shared-secret")
                           .with(bcrypt);
 
        if (u.getUsername().equals(login) && verified) {
            request.getSession().setAttribute("user", u);
            request.getSession().setAttribute("loginDate", LocalDate.now());
            response.setStatus(202);
        } else {
            response.sendError(401);
        }
    }
}