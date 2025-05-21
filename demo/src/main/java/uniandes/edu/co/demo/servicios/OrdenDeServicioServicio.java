package uniandes.edu.co.demo.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniandes.edu.co.demo.modelo.OrdenDeServicio;
import uniandes.edu.co.demo.repository.OrdenDeServicioRepository;

@Service
public class OrdenDeServicioServicio {
    @Autowired
    private OrdenDeServicioRepository repo;

    public List<OrdenDeServicio> darOrdenes() {
        return repo.findAll();
    }

    public OrdenDeServicio darOrden(Integer id) {
        return repo.findByIdOrden(id);
    }

    public void insertarOrden(OrdenDeServicio o) {
        repo.save(o);
    }

    public void actualizarOrden(OrdenDeServicio o) {
        repo.save(o);
    }

    public void eliminarOrden(Integer id) {
        repo.deleteById(id);
    }
}
