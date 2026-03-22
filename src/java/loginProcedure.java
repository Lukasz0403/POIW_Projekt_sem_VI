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
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet odpowiadający na procedurę logowania 
 * Przyjmuje wprowadzone przez użytkownika login i hasło i proównuje je z dostepnymi
 * danymi w bazie pracowników którą jest lista
 * @author Mateusz Gojny
 */


@WebServlet(urlPatterns = {"/loginProcedure"})
public class loginProcedure extends HttpServlet {
    
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        Workers w1 = new Workers(); 
        List<Workers> list = new ArrayList<>();
        boolean find = false;
        
        list = w1.createExampleWorkers();
        
        
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        
        for(Workers w2 : list){
            
            //System.out.println(w2);
            
            if(w2.getLogin().equals(login) && w2.getPassword().equals(password)){
    
                request.getSession().setAttribute("user", w2);
                response.getWriter().write("OK");
                find = true;
                break;
            }
    }
       
        if(find == false){
            response.getWriter().write("ERROR");
        } 

    }

}
