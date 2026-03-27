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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author mateu
 */
@WebServlet(name = "loginProcedureServlet", urlPatterns = {"/loginProcedureServlet"})
public class loginProcedureServlet extends HttpServlet {

        /**
     * Servlet wykonuje metodę doPOST (wykonujemy zmiany więc nie może być GET).
     * Metod doPOST przyjmuje 2 argumenty. Tworzony jest nowy pracownik oraz lista. Wywołana zostaje metoda tworząca
     * przykładowych pracowników. Nastepnie korzystając z paraemtru request odczytywana jest zawartość
     * parametrów login i pass do zmiennych String. W pętli foreach porównywane są te dane z dostepnymi danymi na liście.
     * Jeśli dane będą sobie równe ustawiany jest atrybut dla tej sesji o dowolnej nazwie ale z określonym obiektem, tutaj tym obiektem
     * jest pracownik któremu udało się zalogować. Następnie do parametru respone, dla Writera ustawiana jest wartość "OK"
     * jako potwierdzenie logowania. Pomocnicza zmienna find umożliwia wykrycie błędu i zwrócenie takiej informacji.
     * @param request dane przyjmowane
     * @param response dane zwracane
     * @throws IOException
     */
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        

        JPAController jpa = new JPAController();

        jpa.start();

        String login = request.getParameter("login");
        String password = request.getParameter("pass");

        Users u = jpa.getUserByName(login);

        if(u == null) {
            response.sendError(404);
        }
            
        if(u.getUsername().equals(login) && u.getPassword().equals(password)){

            request.getSession().setAttribute("user", u);
            request.getSession().setAttribute("loginDate", LocalDate.now());
            response.setStatus(202);
        } else {
                response.sendError(401);
            } 

        }

}
