package uniandes.edu.co.demo.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniandes.edu.co.demo.modelo.Cita;
import uniandes.edu.co.demo.repository.CitaRepository;

@Service
public class CitaServicio {

    @Autowired
    private CitaRepository repo;

    /** Lista todas las citas */
    public List<Cita> darCitas() {
        return repo.findAll();  // findAll() viene de MongoRepository
    }

    /** Obtiene una cita por su ID, o null si no existe */
    public Cita darCita(Integer id) {
        return repo.findById(id).orElse(null);  // findById() viene de MongoRepository
    }

    /** Inserta asignando ID incremental si hace falta */
    public void insertarCita(Cita c) {
        if (c.getId() == null) {
            Integer maxId = repo.findAll()
                .stream()
                .map(Cita::getId)
                .filter(i -> i != null)
                .max(Integer::compareTo)
                .orElse(0);
            c.setId(maxId + 1);
        }
        repo.save(c);  // save() viene de MongoRepository
    }

    /** Actualiza (o inserta) una cita */
    public void actualizarCita(Cita c) {
        repo.save(c);
    }

    /** Elimina una cita por su ID */
    public void eliminarCita(Integer id) {
        repo.deleteById(id);  // deleteById() viene de MongoRepository
    }

    /** Busca citas disponibles en el próximo mes */
    public List<Cita> obtenerDisponibilidadCitas() {
        Date now = new Date();
        Date oneMonthLater = new Date(now.getTime() + 30L * 24 * 60 * 60 * 1000);
        return repo.findByEstadoAndFechaHoraBetween("DISPONIBLE", now, oneMonthLater);
    }

    /** Agenda una cita existente cambiando fecha y estado */
    public void agendarCitaSimple(Integer id, Date fechaHora, String estado) {
        Cita c = darCita(id);
        if (c != null) {
            c.setFechaHora(fechaHora);
            c.setEstado(estado);
            repo.save(c);
        }
    }
}
