package com.example.Sorteo_Clientes.View;
import com.vaadin.flow.component.grid.Grid;


import com.example.Sorteo_Clientes.Cliente;
import com.example.Sorteo_Clientes.ClienteService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.Route;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Route("Participantes") // URL: http://localhost:8080/Participantes
public class Vaadin extends VerticalLayout {

    Grid<Cliente> grid = new Grid<>(Cliente.class, false);
    Paragraph ganadorLabel = new Paragraph();

    public Vaadin(ClienteService clienteService) {
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.CENTER);

        H2 titulo = new H2("Panel de Participantes");

        DataProvider<Cliente, Void> dataProvider = DataProvider.fromCallbacks(
            query -> {
                int page = query.getOffset() / query.getLimit();
                int pageSize = query.getLimit();
                PageRequest pageable = PageRequest.of(page, pageSize);
                return clienteService.obtenerClientesPaginados(pageable).stream();
            },
            query -> (int) clienteService.obtenerClientesPaginados(PageRequest.of(0, Integer.MAX_VALUE)).getTotalElements()
        );

        grid.setItems(dataProvider);

        // Estas líneas utilizan la sintaxis correcta para la alineación del texto de la columna.
        grid.addColumn(Cliente::getNombre).setHeader("Nombre y Apellido");
        grid.addColumn(Cliente::getEmail).setHeader("Correo");
        grid.addColumn(Cliente::getTelefono).setHeader("Teléfono");
        grid.addColumn(Cliente::getDni).setHeader("DNI");
        grid.setWidth("80%");
        grid.setAllRowsVisible(true);

        Button sortear = new Button("Sortear Ganador", e -> {
            Cliente ganador = clienteService.obtenerGanadorAleatorio();
            if (ganador != null) {
                Dialog dialogoGanador = new Dialog();
                dialogoGanador.setCloseOnEsc(true);
                dialogoGanador.setCloseOnOutsideClick(true);

                H3 tituloGanador = new H3("🎉 ¡Ganador del Sorteo!");
                Paragraph datosGanador = new Paragraph(
                    "Nombre: " + ganador.getNombre() + "\n" +
                    "DNI: " + ganador.getDni()
                );

                dialogoGanador.add(tituloGanador, datosGanador);
                dialogoGanador.open();
            } else {
                Notification.show("No hay participantes registrados.");
            }
        });

        Div contenedorGanador = new Div(ganadorLabel);
        contenedorGanador.getStyle().set("margin-top", "10px");

        add(titulo, grid, sortear, contenedorGanador);
    }
}
