package uniandes.edu.co.demo.servicios;

import java.util.*;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniandes.edu.co.demo.modelo.Cita;
import uniandes.edu.co.demo.repository.CitaRepository;

@Service
public class CitaServicio {
    @Autowired
    private CitaRepository repo;

    public List<Cita> darCitas() {
        return repo.findAll();
    }

    public Cita darCita(Integer id) {
        return repo.findByIdCita(id);
    }

    public void insertarCita(Cita c) {
        repo.save(c);
    }

    public void actualizarCita(Cita c) {
        repo.save(c);
    }

    public void eliminarCita(Integer id) {
        repo.deleteById(id);
    }

    public List<Cita> obtenerDisponibilidadCitas() {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MONTH, 1);
        return repo.findByEstadoAndFechaHoraBetween("DISPONIBLE", now, cal.getTime());
    }

    public void agendarCitaSimple(Integer id, Date fechaHora, String estado) {
        Cita c = repo.findByIdCita(id);
        if (c != null) {
            c.setFechaHora(fechaHora);
            c.setEstado(estado);
            repo.save(c);
        }
    }
}
