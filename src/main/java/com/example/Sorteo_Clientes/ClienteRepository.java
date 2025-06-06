package com.example.Sorteo_Clientes;
import com.example.Sorteo_Clientes.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
    Optional<Cliente> findByDni(String dni);  
}


