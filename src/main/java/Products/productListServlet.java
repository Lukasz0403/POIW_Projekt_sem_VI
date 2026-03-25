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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Servlet odpowiedzialny za wyświetlanie listy produktów w aplikacji.
 * 
 * Generuje dynamiczny widok HTML zawierający:
 * 
 *panel filtrów (kategoria, maksymalna cena),
 *tabelę produktów,
 *linki do szczegółów produktu.
 * 
 *
 * 
 * Dane produktów są generowane na podstawie przykładowej listy
 * zwracanej przez metodę {@code createSampleListOfProduct()} klasy {@link Product}.
 *
 * 
 * Servlet obsługuje filtrowanie danych na podstawie parametrów zapytania:
 *
 *{@code kategoria} – filtruje produkty po kategorii (case-insensitive),
 *{@code cena} – filtruje produkty o cenie mniejszej lub równej podanej wartości.
 * 
 *
 * 
 * Wynikowy HTML jest zwracany jako odpowiedź typu {@code text/html}
 * i przeznaczony do dynamicznego wstawienia do elementu DOM po stronie klienta (np. przez fetch API).
 *
 * @author Mateusz Gojny
 */
@WebServlet(name = "productListServlet", urlPatterns = {"/productListServlet"})
public class productListServlet extends HttpServlet {

    /**
     * Obsługuje żądanie HTTP GET.
     * 
     * Pobiera parametry filtrowania z requestu, generuje listę produktów,
     * filtruje ją zgodnie z parametrami oraz buduje dynamiczny HTML zawierający:
     * panel filtrów i tabelę wyników.
     *
     * @param request obiekt zawierający dane żądania HTTP, w tym parametry:
     *                {@code kategoria} oraz {@code cena}
     * @param response obiekt odpowiedzi HTTP, do którego zapisywany jest wygenerowany HTML
     * @throws IOException w przypadku błędu zapisu odpowiedzi
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        String kategoria = request.getParameter("kategoria");
        String cenaParam = request.getParameter("cena");

        response.setContentType("text/html;charset=UTF-8");

        Product p = new Product();
        List<Product> lista = p.createSampleListOfProduct();
        
        Set<String> l1 = new HashSet<>();

        for (Product p1 : lista) {
            l1.add(p1.getCategory());
        }

        var out = response.getWriter();

        out.println("<div style='display:flex;'>");

        
        out.println("<div style='width:250px; background:#e0e0e0; padding:15px;'>");
        out.println("<h3>Filtry</h3>");
        out.println("Kategoria:<br>");
        out.println("<select id='kategoria'>");
        out.println("<option value=''>-- wszystkie --</option>");

        for (String cat : l1) {
            out.println("<option value='" + cat + "'>" + cat + "</option>");
        }

        out.println("</select><br><br>");
        out.println("Cena max:<br><input id='cena' type='number'><br><br>");
        out.println("<button onclick='filtr()'>Filtruj</button>");
        out.println("</div>");

        
        out.println("<div style='flex:1; padding:20px;'>");
        out.println("<h2>Produkty</h2>");

        out.println("<table style='width:100%; border-collapse:collapse;'>");

       
        out.println("<tr>");
        out.println("<th>Kategoria</th>");
        out.println("<th>Nazwa</th>");
        out.println("<th>Cena</th>");
        out.println("<th>Ilość</th>");
        out.println("<th>Szczegóły</th>");
        out.println("</tr>");

        int i =0;
        for (Product prod : lista) {

            boolean show = true;

            if (kategoria != null && !kategoria.isEmpty()) {
                if (!prod.getCategory().toLowerCase().contains(kategoria.toLowerCase())) {
                    show = false;
                }
            }

            if (cenaParam != null && !cenaParam.isEmpty()) {
                double cenaMax = Double.parseDouble(cenaParam);
                if (prod.getPrice() > cenaMax) {
                    show = false;
                }
            }
            
            
            if (show) {
                out.println("<tr>");
                out.println("<td>" + prod.getCategory() + "</td>");
                out.println("<td>" + prod.getName() + "</td>");
                out.println("<td>" + prod.getPrice() + " zł</td>");
                out.println("<td>" + prod.getAmount() + "</td>");
                out.println("<td><a href='#' onclick='productInfo(" + i + ")'>Szczegóły produktu</a></td>");
                i++;
                out.println("</tr>");
            }
        }

        out.println("</table>");
        out.println("</div>");
        out.println("</div>");
    }

}
