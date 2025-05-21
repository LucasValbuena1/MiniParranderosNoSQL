package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.demo.modelo.MedicoEnIPS;

public interface MedicoEnIPSRepository extends MongoRepository<MedicoEnIPS, String> {
    java.util.List<MedicoEnIPS> findAll();

    void deleteById(String id);
}