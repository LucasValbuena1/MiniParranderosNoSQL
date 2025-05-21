package uniandes.edu.co.demo.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.demo.modelo.IPS;

public interface IPSRepository extends MongoRepository<IPS, Integer> {
        List<IPS> findAll();

        IPS findByNit(Integer nit);

        void deleteById(Integer nit);
}