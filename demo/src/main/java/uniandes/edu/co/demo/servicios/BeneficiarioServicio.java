package uniandes.edu.co.demo.servicios;

import java.util.*;
import java.util.stream.Collectors;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import uniandes.edu.co.demo.modelo.Beneficiario;
import uniandes.edu.co.demo.repository.BeneficiarioRepository;

@Service
public class BeneficiarioServicio {
    @Autowired
    private BeneficiarioRepository repo;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Beneficiario> darBeneficiarios() {
        return repo.findAll();
    }

    public Beneficiario darBeneficiario(String tipo, String num) {
        return repo.findByTipoDocumentoAndNumeroDocumento(tipo, num);
    }

    public void insertarBeneficiario(Beneficiario b) {
        b.setId(b.getTipoDocumento() + "-" + b.getNumeroDocumento());
        repo.save(b);
    }

    public void actualizarBeneficiario(Beneficiario b) {
        b.setId(b.getTipoDocumento() + "-" + b.getNumeroDocumento());
        repo.save(b);
    }

    public void eliminarBeneficiario(String tipo, String num) {
        repo.deleteById(tipo + "-" + num);
    }

    public List<Map<String, Object>> rfc4(String tipoDoc, String numDoc, Date ini, Date fin) {
        MatchOperation m1 = Aggregation
                .match(Criteria.where("tipoDocumento").is(tipoDoc).and("numeroDocumento").is(numDoc));
        LookupOperation l1 = LookupOperation.newLookup().from("citas").localField("id").foreignField("beneficiarioId")
                .as("citas");
        UnwindOperation u1 = Aggregation.unwind("$citas");
        MatchOperation m2 = Aggregation.match(Criteria.where("citas.fechaHora").gte(ini).lte(fin));
        ProjectionOperation p1 = Aggregation.project().and("nombre").as("beneficiario").and("citas.fechaHora")
                .as("fecha_servicio").and("citas.servicioNombre").as("servicio").and("citas.medicoNombre").as("medico")
                .and("citas.ipsNombre").as("ips");
        Aggregation agg = Aggregation.newAggregation(m1, l1, u1, m2, p1);
        org.springframework.data.mongodb.core.aggregation.AggregationResults<Document> res = mongoTemplate
                .aggregate(agg, "beneficiarios", Document.class);
        return res.getMappedResults().stream()
                .map(d -> d.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .collect(Collectors.toList());
    }
}