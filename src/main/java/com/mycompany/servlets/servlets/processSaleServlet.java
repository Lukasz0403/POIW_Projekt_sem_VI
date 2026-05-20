package com.mycompany.servlets.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * Servlet obsługujący proces rejestracji nowej sprzedaży w systemie.
 * Przyjmuje żądania HTTP POST zawierające listę zakupionych produktów wraz z ich ilościami,
 * przesłaną w formacie JSON bezpośrednio w ciele żądania (Request Body).
 * 
 * <p>Dostęp do tego servletu wymaga aktywnej sesji użytkownika.</p>
 *
 * @author Łukasz Motyka
 */
@WebServlet(name = "processSaleServlet", urlPatterns = {"/processSaleServlet"})
public class processSaleServlet extends HttpServlet {
    
    
    
/**
     * Obsługuje żądanie HTTP POST rejestrujące sprzedaż, tworzące powiązaną transakcję,
     * aktualizujące stany magazynowe oraz zapisujące pojedyncze pozycje sprzedaży.
     *
     * <p>Oczekiwana struktura danych JSON w ciele żądania (kolekcja map):</p>
     * <ul>
     *   <li>{@code productId} — unikalny identyfikator zakupionego produktu (liczba całkowita)</li>
     *   <li>{@code quantity}  — zakupiona ilość produktu (liczba całkowita)</li>
     * </ul>
     *
     * <p>Kody odpowiedzi HTTP:</p>
     * <ul>
     *   <li>{@code 200} — sprzedaż i transakcja zostały pomyślnie przetworzone i zapisane w bazie danych</li>
     *   <li>{@code 401} — brak aktywnej sesji użytkownika</li>
     *   <li>{@code 500} — wewnętrzny błąd serwera podczas przetwarzania transakcji lub błąd parsowania danych</li>
     * </ul>
     *
     * @param request  Obiekt {@link HttpServletRequest} zawierający dane żądania HTTP oraz strumień JSON.
     * @param response Obiekt {@link HttpServletResponse} używany do wysłania odpowiedzi HTTP w formacie JSON.
     * @throws IOException jeśli wystąpi błąd wejścia/wyjścia podczas odczytu danych wejściowych lub zapisu odpowiedzi.
     */    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(401);
            return;
        }

        JPAController jpa = new JPAController();
        jpa.start();

        Users user = (Users) request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> items = mapper.readValue(request.getReader(), new TypeReference<List<Map<String, Object>>>(){});

        try {
            int totalSum = 0;
            for (Map<String, Object> item : items) {
                int productID = (Integer) item.get("productId");
                int quantity = (Integer) item.get("quantity");

                Products p = jpa.getProductById(productID);
                if (p != null) {
                    totalSum += (int) (p.getPrice() * quantity); 
                }
            }

            Transactions transaction = new Transactions();
            transaction.setDate(new Date());
            transaction.setTransactionSum(totalSum);

            jpa.saveTransaction(transaction);


            for (Map<String, Object> item : items) {
                int productID = (Integer) item.get("productId");
                int quantity = (Integer) item.get("quantity");

                Products p = jpa.getProductById(productID);
                if (p != null) {
                    p.setQuantity(p.getQuantity() - quantity);
                    jpa.saveOrUpdateProduct(p); 

                    Sales s = new Sales();
                    s.setProductId(p);
                    s.setUserId(user);
                    s.setQuantity(quantity);
                    s.setSaleDate(new Date());
                    s.setTransactionId(transaction);

                    jpa.saveSale(s); 
                }
            }

            response.setStatus(200);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"ok\"}");

        }catch (Exception e) {
            response.setStatus(500);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    } 
}