package uniandes.edu.co.demo.servicios;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.domain.Sort;
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
            Integer maxId = repo.findAll().stream()
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
     * Consulta la disponibilidad de un servicio de salud (RFC-1).
     * Si el servicio tiene idOrden, filtra citas solo para esa orden.
     * Devuelve List<Object[]> = [nombreServicio, fechaHora, ips, medico]
     */
    public List<Object[]> consultarDisponibilidad(Integer idServicio) {
        ServicioDeSalud serv = darServicio(idServicio);

        // Caso A: servicio con orden específica
        if (serv.getIdOrden() != null) {
            Integer ordenId = serv.getIdOrden();

            MatchOperation matchCitas = Aggregation.match(
                Criteria.where("ordenDeServicio").is(ordenId)
            );

            LookupOperation lookupOrden = LookupOperation.newLookup()
                .from("ORDENES_DE_SERVICIO")
                .localField("ordenDeServicio")
                .foreignField("_id")
                .as("orden");
            UnwindOperation uOrden = Aggregation.unwind("orden");
            UnwindOperation uIPS   = Aggregation.unwind("orden.IPS");
            LookupOperation lookupIPS = LookupOperation.newLookup()
                .from("IPS")
                .localField("orden.IPS")
                .foreignField("_id")
                .as("ips");
            UnwindOperation uIpsDoc = Aggregation.unwind("ips");

            ProjectionOperation projA = Aggregation.project()
                .and("fechaHora").as("disponibilidad")
                .and("ips.nombre").as("ips")
                .and("medico.nombre").as("medico");

            Aggregation aggA = Aggregation.newAggregation(
                matchCitas,
                lookupOrden, uOrden,
                uIPS, lookupIPS, uIpsDoc,
                projA
            );

            List<Document> docs = mongoTemplate.aggregate(aggA, "CITAS", Document.class)
                                              .getMappedResults();

            return docs.stream()
                .map(d -> new Object[]{
                    serv.getNombre(),
                    d.getDate("disponibilidad"),
                    d.getString("ips"),
                    d.getString("medico")
                })
                .collect(Collectors.toList());
        }

        // Caso B: servicio sin orden específica -> agregación original
        MatchOperation m0 = Aggregation.match(
            Criteria.where("_id").is(idServicio)
        );
        LookupOperation l1 = LookupOperation.newLookup()
            .from("ORDENES_DE_SERVICIO")
            .localField("_id")
            .foreignField("serviciosMedicos")
            .as("ordenes");
        UnwindOperation u1 = Aggregation.unwind("ordenes");
        LookupOperation l2 = LookupOperation.newLookup()
            .from("CITAS")
            .localField("ordenes._id")
            .foreignField("ordenDeServicio")
            .as("citas");
        UnwindOperation u2 = Aggregation.unwind("citas");
        UnwindOperation u3 = Aggregation.unwind("ordenes.IPS");
        LookupOperation l4 = LookupOperation.newLookup()
            .from("IPS")
            .localField("ordenes.IPS")
            .foreignField("_id")
            .as("ips");
        UnwindOperation u4 = Aggregation.unwind("ips");
        ProjectionOperation p = Aggregation.project()
            .and("nombre").as("servicio")
            .and("citas.fechaHora").as("disponibilidad")
            .and("ips.nombre").as("ips")
            .and("citas.medico.nombre").as("medico");

        Aggregation aggB = Aggregation.newAggregation(
            m0, l1, u1,
            l2, u2,
            u3, l4, u4,
            p
        );

        List<Document> docs = mongoTemplate.aggregate(aggB, "SERVICIOS_DE_SALUD", Document.class)
                                          .getMappedResults();

        return docs.stream()
            .map(d -> new Object[]{
                d.getString("servicio"),
                d.getDate("disponibilidad"),
                d.getString("ips"),
                d.getString("medico")
            })
            .collect(Collectors.toList());
    }

    /**
     * Obtiene el Top 20 de servicios más solicitados entre dos fechas (RFC-2).
     * Devuelve List<Object[]> = [nombreServicio, conteoSolicitudes]
     */
    public List<Object[]> obtenerTop20Servicios(String fechaInicio, String fechaFin) {
        LocalDate li = LocalDate.parse(fechaInicio);
        LocalDate lf = LocalDate.parse(fechaFin);
        Date start = Date.from(li.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end   = Date.from(lf.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        MatchOperation match = Aggregation.match(
            Criteria.where("fechaHora").gte(start).lt(end)
        );
        LookupOperation lookupOrden = LookupOperation.newLookup()
            .from("ORDENES_DE_SERVICIO")
            .localField("ordenDeServicio")
            .foreignField("_id")
            .as("orden");
        UnwindOperation uOrden = Aggregation.unwind("orden");
        UnwindOperation uServ  = Aggregation.unwind("orden.serviciosMedicos");
        GroupOperation group = Aggregation.group("orden.serviciosMedicos")
            .count().as("solicitudes");
        SortOperation sort  = Aggregation.sort(
            Sort.by(Sort.Direction.DESC, "solicitudes")
        );
        LimitOperation lim = Aggregation.limit(20);
        LookupOperation lookupSrv = LookupOperation.newLookup()
            .from("SERVICIOS_DE_SALUD")
            .localField("_id")
            .foreignField("_id")
            .as("srv");
        UnwindOperation uSrv = Aggregation.unwind("srv");
        ProjectionOperation proj = Aggregation.project()
            .and("srv.nombre").as("servicio")
            .and("solicitudes").as("solicitudes");

        Aggregation agg = Aggregation.newAggregation(
            match, lookupOrden, uOrden, uServ,
            group, sort, lim,
            lookupSrv, uSrv, proj
        );

        AggregationResults<Document> result = mongoTemplate.aggregate(
            agg, "CITAS", Document.class
        );

        return result.getMappedResults().stream()
            .map(d -> new Object[]{
                d.getString("servicio"),
                d.getInteger("solicitudes")
            })
            .collect(Collectors.toList());
    }
}
