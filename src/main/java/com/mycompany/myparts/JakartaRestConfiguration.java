package com.mycompany.myparts;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import com.mycompany.servlets.servlets.JPAController;

/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */
@ApplicationPath("resources")
public class JakartaRestConfiguration extends Application {
    
}