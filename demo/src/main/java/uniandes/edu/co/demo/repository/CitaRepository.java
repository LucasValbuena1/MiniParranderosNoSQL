package uniandes.edu.co.demo.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.demo.modelo.Cita;

public interface CitaRepository extends MongoRepository<Cita, Integer> {
    List<Cita> findAll();

    Cita findByIdCita(Integer id);

    void deleteById(Integer id);

    List<Cita> findByEstadoAndFechaHoraBetween(String estado, Date start, Date end);
}