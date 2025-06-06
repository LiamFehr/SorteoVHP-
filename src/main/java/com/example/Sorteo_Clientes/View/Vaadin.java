package com.example.Sorteo_Clientes.View;

import java.util.List;

import com.example.Sorteo_Clientes.Cliente;
import com.example.Sorteo_Clientes.ClienteService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;

@Route("Participantes")  // URL: http://localhost:8080/admin
public class Vaadin extends VerticalLayout
{
	Grid<Cliente> grid = new Grid<>(Cliente.class, false);
	Paragraph ganadorLabel = new Paragraph();
	
	public Vaadin(ClienteService clienteService) 
	{
		setPadding(true);
		setSpacing(true);
		
		H2 titulo = new H2("Panel de Participantes");
		
		configurarGrid(clienteService.obtenerTodos());
		
		Button sortear = new Button("Sortear Ganador", e -> {
		    Cliente ganador = clienteService.obtenerGanadorAleatorio();
		    if (ganador != null) {
		        // Crear el diálogo flotante
		        Dialog dialogoGanador = new Dialog();
		        dialogoGanador.setCloseOnEsc(true);
		        dialogoGanador.setCloseOnOutsideClick(true);

		        H3 titulo2 = new H3("🎉 ¡Ganador del Sorteo!");
		        Paragraph datos = new Paragraph(
		            "Nombre: " + ganador.getNombre() + "\n" + 
		            "DNI: " + ganador.getDni()
		        );

		        dialogoGanador.add(titulo2, datos);
		        dialogoGanador.open();
		    } else {
		        Notification.show("No hay participantes registrados.");
		    }
		});

		
		 // Contenedor de resultado //
        Div contenedorGanador = new Div(ganadorLabel);
        contenedorGanador.getStyle().set("margin-top", "10px");

        // Agregar elementos a la vista//
        add(titulo, grid, sortear, contenedorGanador);
        
       
		
	}
	private void configurarGrid(List<Cliente> clientes) 
	{
    grid.setItems(clientes);
	grid.addColumn(Cliente::getNombre).setHeader("Nombre y Apellido");
	grid.addColumn(Cliente::getEmail).setHeader("Correo");
	grid.addColumn(Cliente::getTelefono).setHeader("Teléfono");
    grid.addColumn(Cliente::getDni).setHeader("DNI");
	}


}
