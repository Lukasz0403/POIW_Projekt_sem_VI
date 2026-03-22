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
 *
 * @author mateu
 */


@WebServlet(urlPatterns = {"/loginProcedure"})
public class loginProcedure extends HttpServlet {
    
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        Workers w1 = new Workers(); 
        List<Workers> list = new ArrayList<>();
        boolean find = false;
        
        list = w1.createExampleWorkers();
        
        
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        
        for(Workers w2 : list){
            
            System.out.println(w2);
            
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
