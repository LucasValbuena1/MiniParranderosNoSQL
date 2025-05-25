package uniandes.edu.co.demo.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.demo.modelo.ServiciosDeUnaIPS;

public interface ServiciosDeUnaIPSRepository extends MongoRepository<ServiciosDeUnaIPS, String> {
    List<ServiciosDeUnaIPS> findAll();

    void deleteById(String id);

    // Método adicional para eliminar por nit e idServicio (útil para el controlador)
    void deleteByNitIPSAndIdServicio(String nitIPS, String idServicio);

    // También puedes agregar un método para buscar por nitIPS e idServicio si lo necesitas
    ServiciosDeUnaIPS findByNitIPSAndIdServicio(String nitIPS, String idServicio);
}
