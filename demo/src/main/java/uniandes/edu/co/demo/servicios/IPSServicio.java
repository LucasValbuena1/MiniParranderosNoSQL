package uniandes.edu.co.demo.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniandes.edu.co.demo.modelo.IPS;
import uniandes.edu.co.demo.repository.IPSRepository;

@Service
public class IPSServicio {
    @Autowired
    private IPSRepository repo;

    public List<IPS> darIPS() {
        return repo.findAll();
    }

    public IPS darIPS(Integer nit) {
        return repo.findByNit(nit);
    }

     public void insertarIPS(IPS ips) {
    if (ips.getId() == null) {
        // Busca el máximo ID actual y suma 1
        Integer maxId = repo.findAll()
            .stream()
            .map(IPS::getId)
            .max(Integer::compare)
            .orElse(0);
        ips.setId(maxId + 1);
    }
    repo.save(ips);
}

    public void actualizarIPS(IPS i) {
        repo.save(i);
    }

    public void eliminarIPS(Integer nit) {
        repo.deleteById(nit);
    }
}