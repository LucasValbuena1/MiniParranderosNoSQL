package uniandes.edu.co.demo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.demo.modelo.OrdenDeServicio;
import uniandes.edu.co.demo.repository.OrdenDeServicioRepository;

@Service
public class ServicioSaludAgendamientoServicio {

    @Autowired
    private OrdenDeServicioRepository ordenRepo;

    @Transactional
    public void agendarServicioSalud(Integer idOrdenServicio) {
        // 1) Buscar la orden
        OrdenDeServicio orden = ordenRepo.findByIdOrden(idOrdenServicio);
        if (orden == null) {
            throw new RuntimeException("Orden de servicio no encontrada");
        }
        // 2) Verificar estado
        if (!"Vigente".equalsIgnoreCase(orden.getEstado())) {
            throw new RuntimeException("La orden de servicio no está disponible para agendar");
        }
        // 3) Cambiar a completada
        orden.setEstado("Completada");
        ordenRepo.save(orden);
    }
}
