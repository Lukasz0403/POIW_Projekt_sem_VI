/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Products;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author mateu
 */
@WebServlet(name = "productInfoServlet", urlPatterns = {"/productInfoServlet"})
public class productInfoServlet extends HttpServlet {
    
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        Product p = new Product();
        List<Product> lista = p.createSampleListOfProduct();

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.getWriter().write("<h2>Błąd: brak ID produktu</h2>");
            return;
        }

        int id = Integer.parseInt(idParam);

        Product prod = lista.get(id);

        var out = response.getWriter();

        out.println("<div style='padding:20px;'>");
        out.println("<h2>Szczegóły produktu</h2>");

        out.println("<p><b>Kategoria:</b> " + prod.getCategory() + "</p>");
        out.println("<p><b>Nazwa:</b> " + prod.getName() + "</p>");
        out.println("<p><b>Cena:</b> " + prod.getPrice() + " zł</p>");
        out.println("<p><b>Ilość:</b> " + prod.getAmount() + "</p>");

        out.println("<br><button onclick=\"go('products')\">Powrót</button>");

        out.println("</div>");
    }

    

}
