/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import CompanyWorkers.Workers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;

/**
 *
 * @author mateu
 */
@WebServlet(urlPatterns = {"/mainWindowServlet"})
public class mainWindowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        var out = response.getWriter();

       
        HttpSession session = request.getSession();
        Workers user = (Workers) session.getAttribute("user");

       
        if (user == null) {
            out.println("<h2>Brak zalogowanego użytkownika</h2>");
            return;
        }

        String today = LocalDate.now().toString();

  
        out.println("<div style='padding:30px;'>");

        out.println("<h1 style='margin-bottom:20px;'>Witamy w aplikacji 👋</h1>");

        out.println("<div style='background:white; padding:20px; border-radius:15px; box-shadow:0 5px 15px rgba(0,0,0,0.1); max-width:600px;'>");

        out.println("<h3>Dane użytkownika:</h3>");

        out.println("<p><b>Imię:</b> " + user.getName() + "</p>");
        out.println("<p><b>Nazwisko:</b> " + user.getSurname() + "</p>");
        out.println("<p><b>ID:</b> " + user.getId() + "</p>");
        out.println("<p><b>Login:</b> " + user.getLogin() + "</p>");

        out.println("<hr>");

        out.println("<p><b>Data logowania:</b> " + today + "</p>");
        out.println("<p><b>Zakres uprawnień:</b> test</p>");

        out.println("</div>");
        out.println("</div>");
    }

}
