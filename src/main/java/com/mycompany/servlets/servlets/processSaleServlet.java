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
 *
 * @author lukas
 */
@WebServlet(name = "processSaleServlet", urlPatterns = {"/processSaleServlet"})
public class processSaleServlet extends HttpServlet {
    
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
                    jpa.saveSale(s); 
                }
            }
            response.setStatus(200);
            response.getWriter().write("{\"status\":\"ok\"}");
        } catch (Exception e) {
            response.setStatus(500);
            e.printStackTrace();
        }
    }   
}