package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.demo.modelo.ServiciosDeUnaIPS;

public interface ServiciosDeUnaIPSRepository extends MongoRepository<ServiciosDeUnaIPS, String> {
        java.util.List<ServiciosDeUnaIPS> findAll();

        void deleteById(String id);
}