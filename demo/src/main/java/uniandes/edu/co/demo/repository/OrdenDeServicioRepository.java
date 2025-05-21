package uniandes.edu.co.demo.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.demo.modelo.OrdenDeServicio;

public interface OrdenDeServicioRepository extends MongoRepository<OrdenDeServicio, Integer> {
        List<OrdenDeServicio> findAll();

        OrdenDeServicio findByIdOrden(Integer id);

        void deleteById(Integer id);
}