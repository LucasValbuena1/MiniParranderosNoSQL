package uniandes.edu.co.demo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.demo.modelo.ServicioDeSalud;
import uniandes.edu.co.demo.modelo.OrdenDeServicio;
import uniandes.edu.co.demo.repository.ServicioDeSaludRepository;
import uniandes.edu.co.demo.repository.OrdenDeServicioRepository;

@Service
public class AgendarServicioSaludService {

    @Autowired
    private ServicioDeSaludRepository servicioRepo;

    @Autowired
    private OrdenDeServicioRepository ordenRepo;

    @Transactional
    public void agendarServicio(Integer idServicio) {
        // 1) Buscar el servicio
        ServicioDeSalud servicio = servicioRepo.findByIdServicio(idServicio);
        if (servicio == null) {
            throw new RuntimeException("Servicio no existe");
        }
        // 2) Buscar la orden asociada
        OrdenDeServicio orden = ordenRepo.findByIdOrden(servicio.getIdOrden());
        if (orden == null) {
            throw new RuntimeException("No hay orden de servicio asociada");
        }
        // 3) Verificar estado
        if (!"Vigente".equalsIgnoreCase(orden.getEstado())) {
            throw new RuntimeException("El servicio ya no está disponible");
        }
        // 4) Cambiar a completada
        orden.setEstado("Completada");
        ordenRepo.save(orden);
    }
}
