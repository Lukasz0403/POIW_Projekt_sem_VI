package com.mycompany.myparts;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import com.mycompany.model.JPAController;

/**
 * Klasa konfiguracyjna Jakarta RESTful Web Services dla aplikacji MyParts.
 * Rejestruje aplikację REST pod ścieżką bazową {@code /resources}.
 *
 * <p>
 * Wszystkie zasoby REST (np. {@code JakartaEE10Resource}) dostępne są pod
 * adresem: {@code /MyParts/resources/...}</p>
 *
 * <p>
 * Klasa rozszerza {@link Application} zgodnie ze specyfikacją Jakarta RESTful
 * Web Services — nie wymaga dodatkowej implementacji, serwer aplikacyjny
 * automatycznie skanuje i rejestruje wszystkie klasy oznaczone adnotacją
 * {@code @Path}.</p>
 *
 */
@ApplicationPath("resources")
public class JakartaRestConfiguration extends Application {

    /**
     * Konstruktor bezargumentowy wymagany przez specyfikację Jakarta RESTful
     * Web Services.
     */
    public JakartaRestConfiguration() {
    }
}
