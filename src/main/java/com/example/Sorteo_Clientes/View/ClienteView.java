package com.example.Sorteo_Clientes.View;

import com.example.Sorteo_Clientes.Cliente;
import com.example.Sorteo_Clientes.ClienteService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;

@Route("")  // Página de inicio: http://localhost:8080/
public class ClienteView extends VerticalLayout {

    public ClienteView(ClienteService clienteService) 
    {
    	setSizeFull(); // Ocupar toda la pantalla
    	setAlignItems(Alignment.CENTER); // Centrar horizontalmente
    	setJustifyContentMode(JustifyContentMode.CENTER); // Centrar verticalmente
    	Image logo = new Image("img/logo.jpg", "Logo del negocio");
    	logo.setHeight("200px"); // Podés ajustar el tamaño
    	add(logo);
        H2 titulo = new H2("Formulario de Participación en el Sorteo");
        
        TextField nombreField = new TextField("Nombre y Apellido");
        EmailField emailField = new EmailField("Correo Electrónico");
        TextField telefonoField = new TextField("Telefono");
        TextField dniField = new TextField("DNI");
        
        Button botonGuardar = new Button("Participar", e ->{
        	
        	String nombre = nombreField.getValue();
        	String email = emailField.getValue();
            String telefono = telefonoField.getValue();
            String dni = dniField.getValue();
            
            if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty() || dni.isEmpty()) {
                Notification.show("Todos los campos son obligatorios.", 3000, Notification.Position.MIDDLE);
                return;
            }

            Cliente cliente = new Cliente();
            cliente.setNombre(nombre);
            cliente.setEmail(email);
            cliente.setTelefono(telefono);
            cliente.setDni(dni);

            try {
                clienteService.guardarCliente(cliente);
                Notification.show("✅ ¡Te registraste correctamente!", 3000, Notification.Position.MIDDLE);
                // Limpiar campos
                nombreField.clear();
                emailField.clear();
                telefonoField.clear();
                dniField.clear();
                UI.getCurrent().getPage().executeJs( "setTimeout(function() { window.location.href = 'http://victorpetrucciosh.mitiendanube.com'; }, 3000);");
            } catch (Exception ex) {
                Notification.show("❌ Error: " + ex.getMessage(), 4000, Notification.Position.MIDDLE);
            }
        
        });
        add(titulo, nombreField, emailField, telefonoField, dniField, botonGuardar);
        
        
        Anchor adminLink = new Anchor("/Participantes", "Ir al Panel del Sorteo 🎲");
        adminLink.getStyle()
            .set("margin-top", "20px")
            .set("font-size", "small")
            .set("color", "gray");

        add(adminLink);
    }
    
}    
