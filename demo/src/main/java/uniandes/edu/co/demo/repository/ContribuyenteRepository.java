package uniandes.edu.co.demo.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.demo.modelo.Contribuyente;

public interface ContribuyenteRepository extends MongoRepository<Contribuyente, String> {

    // Obtiene todos los contribuyentes
    List<Contribuyente> findAll();

    // Busca por tipoDeDocumento y numeroDeDocumento, igual que en MongoDB
    Contribuyente findByTipoDeDocumentoAndNumeroDeDocumento(String tipoDeDocumento, String numeroDeDocumento);

    // Elimina por ID
    void deleteById(String id);

    // Busca todos los contribuyentes por tipoDeDocumento
    List<Contribuyente> findByTipoDeDocumento(String tipoDeDocumento);
}
