package uniandes.edu.co.demo.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniandes.edu.co.demo.modelo.Contribuyente;
import uniandes.edu.co.demo.repository.ContribuyenteRepository;

@Service
public class ContribuyenteServicio {
    @Autowired
    private ContribuyenteRepository repo;

    public List<Contribuyente> darContribuyentes() {
        return repo.findAll();
    }

    public Contribuyente darContribuyente(String t, String n) {
        return repo.findByPkTipoDocumentoAndPkNumeroDocumento(t, n);
    }

    public void insertarContribuyente(Contribuyente c) {
        c.setId();
        repo.save(c);
    }

    public void actualizarContribuyente(Contribuyente c) {
        c.setId();
        repo.save(c);
    }

    public void eliminarContribuyente(String t, String n) {
        repo.deleteById(t + "-" + n);
    }
}