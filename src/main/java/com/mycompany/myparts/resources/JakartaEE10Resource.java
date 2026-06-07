package com.mycompany.myparts.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Zasób REST udostępniający endpoint diagnostyczny aplikacji MyParts. Mapuje
 * ścieżkę {@code /jakartaee10} i służy do weryfikacji czy serwer Jakarta EE
 * działa poprawnie.
 *
 * <p>
 * Endpoint dostępny jest pod adresem:
 * {@code GET /MyParts/resources/jakartaee10}</p>
 *
 */
@Path("jakartaee10")
public class JakartaEE10Resource {

    /**
     * Konstruktor bezargumentowy wymagany przez specyfikację Jakarta RESTful
     * Web Services.
     */
    public JakartaEE10Resource() {
    }

    /**
     * Obsługuje żądanie HTTP GET zwracające odpowiedź diagnostyczną. Służy do
     * sprawdzenia czy aplikacja i serwer Jakarta EE działają poprawnie.
     *
     * <p>
     * Ścieżka: {@code GET /MyParts/resources/jakartaee10}</p>
     *
     * @return Odpowiedź HTTP 200 OK z tekstem {@code "ping Jakarta EE"}.
     */
    @GET
    public Response ping() {
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
}
