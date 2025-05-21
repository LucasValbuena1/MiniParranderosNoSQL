package uniandes.edu.co.demo.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uniandes.edu.co.demo.modelo.Contribuyente;

public interface ContribuyenteRepository extends MongoRepository<Contribuyente, String> {
        List<Contribuyente> findAll();

        Contribuyente findByPkTipoDocumentoAndPkNumeroDocumento(String tipoDoc, String numDoc);

        void deleteById(String id);

        @Query("{ 'pk.tipoDocumento': ?0 }")
        List<Contribuyente> findByPkTipoDocumento(String tipoDoc);
}