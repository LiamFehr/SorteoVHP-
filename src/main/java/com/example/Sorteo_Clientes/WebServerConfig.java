package com.example.Sorteo_Clientes;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServerConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> {
            String port = System.getenv("PORT"); // Obtiene el puerto de la variable de entorno
            if (port != null && !port.isEmpty()) {
                try {
                    factory.setPort(Integer.parseInt(port));
                    System.out.println("DEBUG: Setting Tomcat port to: " + port); // Línea de depuración
                } catch (NumberFormatException e) {
                    System.err.println("ERROR: Could not parse PORT environment variable: " + port);
                }
            } else {
                System.out.println("DEBUG: PORT environment variable not found or empty. Using default or other config.");
            }
        };
    }
}
