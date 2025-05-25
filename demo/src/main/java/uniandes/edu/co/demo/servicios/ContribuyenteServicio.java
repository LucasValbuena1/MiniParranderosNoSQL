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

    // Cambiado: busca por los campos correctos, como están en MongoDB
    public Contribuyente darContribuyente(String tipoDeDocumento, String numeroDeDocumento) {
        return repo.findByTipoDeDocumentoAndNumeroDeDocumento(tipoDeDocumento, numeroDeDocumento);
    }

    public void insertarContribuyente(Contribuyente c) {
        c.setId();
        repo.save(c);
    }

    public void actualizarContribuyente(Contribuyente c) {
        c.setId();
        repo.save(c);
    }

    public void eliminarContribuyente(String tipoDeDocumento, String numeroDeDocumento) {
        repo.deleteById(tipoDeDocumento + "-" + numeroDeDocumento);
    }
}
