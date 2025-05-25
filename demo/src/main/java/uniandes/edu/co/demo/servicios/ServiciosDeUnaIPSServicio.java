package uniandes.edu.co.demo.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniandes.edu.co.demo.modelo.ServiciosDeUnaIPS;
import uniandes.edu.co.demo.repository.ServiciosDeUnaIPSRepository;

@Service
public class ServiciosDeUnaIPSServicio {
    @Autowired
    private ServiciosDeUnaIPSRepository repo;

    public List<ServiciosDeUnaIPS> darServicios() {
        return repo.findAll();
    }

    public void insertarServicio(ServiciosDeUnaIPS servicio) {
        repo.save(servicio);
    }

    // Eliminar por id compuesto (nitIPS-idServicio)
    public void eliminarServicio(String id) {
        repo.deleteById(id);
    }

    // Eliminar por nit y servicio
    public void eliminarPorNitYServicio(String nitIPS, String idServicio) {
        repo.deleteByNitIPSAndIdServicio(nitIPS, idServicio);
    }
}
