package uniandes.edu.co.demo.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniandes.edu.co.demo.modelo.MedicoEnIPS;
import uniandes.edu.co.demo.repository.MedicoEnIPSRepository;

@Service
public class MedicoEnIPSServicio {
    @Autowired
    private MedicoEnIPSRepository repo;

    public List<MedicoEnIPS> darMedicosEnIPS() {
        return repo.findAll();
    }

    public void insertarMedico(MedicoEnIPS m) {
        repo.save(m);
    }

    public void eliminarMedico(String id) {
        repo.deleteById(id);
    }
}