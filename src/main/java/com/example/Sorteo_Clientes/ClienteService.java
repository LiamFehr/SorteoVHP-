package com.example.Sorteo_Clientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService 
{

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Cliente guardarCliente(Cliente cliente) {
        Optional<Cliente> existente = clienteRepository.findByDni(cliente.getDni()); // <- corregido
        if (existente.isPresent()) {
            throw new DuplicateKeyException("El DNI ya está registrado.");
        }
        return clienteRepository.save(cliente);
    }
    
    public List<Cliente> obtenerTodos()
    {
    	return clienteRepository.findAll();
    }
    
    public Cliente obtenerGanadorAleatorio() 
    {
    	Aggregation agg = Aggregation.newAggregation(Aggregation.sample(1));
    	AggregationResults<Cliente> resultado = mongoTemplate.aggregate(agg,"Clientedate", Cliente.class);
    	return resultado.getUniqueMappedResult();
    }
}

    
