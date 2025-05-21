package uniandes.edu.co.demo.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.demo.modelo.Medico;

public interface MedicoRepository extends MongoRepository<Medico, Integer> {
        List<Medico> findAll();

        Medico findByNumeroRegistroMedico(Integer id);

        void deleteById(Integer id);
}