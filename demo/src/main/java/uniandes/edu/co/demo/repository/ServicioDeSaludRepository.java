package uniandes.edu.co.demo.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.demo.modelo.ServicioDeSalud;

public interface ServicioDeSaludRepository extends MongoRepository<ServicioDeSalud, Integer> {
  List<ServicioDeSalud> findAll();

  ServicioDeSalud findByIdServicio(Integer id);

  void deleteById(Integer id);
}