package com.example.Sorteo_Clientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page; // Importar Page para paginación
import org.springframework.data.domain.Pageable; // Importar Pageable para paginación
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Cliente guardarCliente(Cliente cliente) {
        Optional<Cliente> existente = clienteRepository.findByDni(cliente.getDni());
        if (existente.isPresent()) {
            throw new DuplicateKeyException("El DNI ya está registrado.");
        }
        return clienteRepository.save(cliente);
    }

    // Método anterior para obtener todos (ya no lo usaremos si implementamos paginación en la UI)
    // public List<Cliente> obtenerTodos() {
    //     return clienteRepository.findAll();
    // }

    /**
     * Obtiene una página de clientes desde la base de datos.
     * Esta es la forma optimizada para manejar grandes volúmenes de datos
     * ya que solo carga un subconjunto de clientes en memoria.
     * @param pageable Objeto Pageable que contiene información sobre la página solicitada (número de página, tamaño de página, ordenación).
     * @return Un objeto Page que contiene la lista de clientes para la página solicitada y metadatos de paginación.
     */
    public Page<Cliente> obtenerClientesPaginados(Pageable pageable) {
        return clienteRepository.findAll(pageable); // Spring Data MongoDB gestiona la paginación automáticamente
    }

    public Cliente obtenerGanadorAleatorio() {
        Aggregation agg = Aggregation.newAggregation(Aggregation.sample(1));
        AggregationResults<Cliente> resultado = mongoTemplate.aggregate(agg, "Clientedate", Cliente.class);
        return resultado.getUniqueMappedResult();
    }
}
    
