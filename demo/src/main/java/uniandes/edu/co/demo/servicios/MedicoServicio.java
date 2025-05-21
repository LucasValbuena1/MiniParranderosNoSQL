package uniandes.edu.co.demo.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniandes.edu.co.demo.modelo.Medico;
import uniandes.edu.co.demo.repository.MedicoRepository;

@Service
public class MedicoServicio {
    @Autowired
    private MedicoRepository repo;

    public List<Medico> darMedicos() {
        return repo.findAll();
    }

    public Medico darMedico(Integer id) {
        return repo.findByNumeroRegistroMedico(id);
    }

    public void insertarMedico(Medico m) {
        repo.save(m);
    }

    public void actualizarMedico(Medico m) {
        repo.save(m);
    }

    public void eliminarMedico(Integer id) {
        repo.deleteById(id);
    }
}
