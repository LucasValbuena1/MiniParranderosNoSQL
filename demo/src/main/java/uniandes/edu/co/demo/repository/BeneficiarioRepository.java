package uniandes.edu.co.demo.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uniandes.edu.co.demo.modelo.Beneficiario;

public interface BeneficiarioRepository extends MongoRepository<Beneficiario, String> {
  List<Beneficiario> findAll();

  Beneficiario findByTipoDocumentoAndNumeroDocumento(String tipo, String numero);

  void deleteById(String id);

  List<Beneficiario> findByContribuyente_NumeroDocumento(String numDoc);

  @Query("{ 'contribuyente.tipoDocumento': ?0, 'contribuyente.numeroDocumento': ?1 }")
  List<Beneficiario> buscarPorContribuyente(String tipoDoc, String numDoc);
}