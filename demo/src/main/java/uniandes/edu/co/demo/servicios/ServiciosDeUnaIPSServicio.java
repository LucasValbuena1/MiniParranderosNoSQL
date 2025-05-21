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

    public void insertarServicio(ServiciosDeUnaIPS s) {
        repo.save(s);
    }

    public void eliminarServicio(String id) {
        repo.deleteById(id);
    }
}