package uniandes.edu.co.demo.servicios;

import java.util.*;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import uniandes.edu.co.demo.modelo.ServicioDeSalud;
import uniandes.edu.co.demo.repository.ServicioDeSaludRepository;

@Service
public class ServicioDeSaludServicio {

    @Autowired
    private ServicioDeSaludRepository repo;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Obtiene todos los servicios de salud.
     */
    public List<ServicioDeSalud> darServicios() {
        return repo.findAll();
    }

    /**
     * Busca un servicio por su ID.
     */
    public ServicioDeSalud darServicio(Integer idServicio) {
        return repo.findByIdServicio(idServicio);
    }

    /**
     * Inserta un nuevo servicio.
     */
     public void insertarServicio(ServicioDeSalud s) {
        if (s.getIdServicio() == null) {
            // calcula el máximo id actual
            Integer maxId = repo.findAll()
                                .stream()
                                .map(ServicioDeSalud::getIdServicio)
                                .max(Integer::compareTo)
                                .orElse(0);
            s.setIdServicio(maxId + 1);
        }
        repo.save(s);
    }

    /**
     * Actualiza un servicio existente.
     */
    public void actualizarServicio(ServicioDeSalud s) {
        repo.save(s);
    }

    /**
     * Elimina un servicio por ID.
     */
    public void eliminarServicio(Integer idServicio) {
        repo.deleteById(idServicio);
    }

    /**
     * Consulta la disponibilidad de un servicio de salud (RFC-1), emulando la
     * consulta SQL original.
     * Devuelve una lista de Object[] = [servicio, disponibilidad, ips, medico]
     */
    public List<Object[]> consultarDisponibilidad(Integer idServicio) {
        // 1) Filtrar por el servicio
        MatchOperation m0 = Aggregation.match(Criteria.where("idServicio").is(idServicio));
        // 2) Lookup con ordenesDeServicio
        LookupOperation l1 = LookupOperation.newLookup()
                .from("ordenesDeServicio")
                .localField("idServicio")
                .foreignField("servicioMedico")
                .as("ordenes");
        UnwindOperation u1 = Aggregation.unwind("$ordenes");
        // 3) Lookup con citas
        LookupOperation l2 = LookupOperation.newLookup()
                .from("citas")
                .localField("ordenes.idOrden")
                .foreignField("idOrdenDeServicio")
                .as("citas");
        UnwindOperation u2 = Aggregation.unwind("$citas");
        // 4) Lookup con medicoEnIPS
        LookupOperation l3 = LookupOperation.newLookup()
                .from("medicoEnIPS")
                .localField("ordenes.medicoAsociado")
                .foreignField("numeroRegistroMedico")
                .as("meIPS");
        UnwindOperation u3 = Aggregation.unwind("$meIPS", true);
        // 5) Lookup con medicos
        LookupOperation l4 = LookupOperation.newLookup()
                .from("medicos")
                .localField("ordenes.medicoAsociado")
                .foreignField("numeroRegistroMedico")
                .as("medico");
        UnwindOperation u4 = Aggregation.unwind("$medico", true);
        // 6) Lookup con ips
        LookupOperation l5 = LookupOperation.newLookup()
                .from("ips")
                .localField("meIPS.nitIPS")
                .foreignField("nit")
                .as("ips");
        UnwindOperation u5 = Aggregation.unwind("$ips", true);
        // 7) Proyección final
        ProjectionOperation p = Aggregation.project()
                .and("nombre").as("servicio")
                .and("citas.fechaHora").as("disponibilidad")
                .and("ips.nombre").as("ips")
                .and("medico.nombre").as("medico");

        Aggregation agg = Aggregation.newAggregation(
                m0, l1, u1,
                l2, u2,
                l3, u3,
                l4, u4,
                l5, u5,
                p);

        List<Document> docs = mongoTemplate
                .aggregate(agg, "serviciosDeSalud", Document.class)
                .getMappedResults();

        // Convertir Document a Object[]
        return docs.stream()
                .map(d -> new Object[] {
                        d.getString("servicio"),
                        d.getDate("disponibilidad"),
                        d.getString("ips"),
                        d.getString("medico")
                })
                .collect(Collectors.toList());
    }
}
