package uniandes.edu.co.demo.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.OrdenDeServicio;
import uniandes.edu.co.demo.modelo.OrdenDeServicio.Paciente;
import uniandes.edu.co.demo.servicios.OrdenDeServicioServicio;

@Controller
@RequestMapping("/ordenservicio")
public class OrdenDeServicioController {

    @Autowired
    private OrdenDeServicioServicio ordenServicio;

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping
    public String show(Model model) {
        model.addAttribute("ordenes", ordenServicio.darOrdenes());
        return "ordenesDeServicio";
    }

    @GetMapping("/new")
    public String formNew(Model model) {
        OrdenDeServicio o = new OrdenDeServicio();
        o.setPaciente(new Paciente());
        model.addAttribute("ordenservicio", o);
        return "ordenDeServicioNuevo";
    }

    @PostMapping("/new/save")
    public String save(
            @ModelAttribute OrdenDeServicio o,
            @RequestParam("serviciosMedicosInput") String serviciosInput,
            @RequestParam("estado") String estado,
            @RequestParam("vigencia") String vigencia,
            @RequestParam("medicoAsociado") Integer medicoId,
            @RequestParam("pacienteId") Integer pacienteId,
            @RequestParam("pacienteTipo") String pacienteTipo,
            @RequestParam("IPSInput") Integer ipsId) {

        // Generar ID numérico manual (puedes reemplazar por secuencia real)
        o.setIdOrden((int) (Math.random() * 100000));

        // Servicios médicos
        List<Integer> servs = Arrays.stream(serviciosInput.split(","))
                .map(String::trim)
                .map(Integer::valueOf)
                .toList();
        o.setServiciosMedicos(servs);

        o.setEstado(estado);
        o.setVigencia(vigencia);
        o.setMedicoAsociado(medicoId);
        o.setIPS(Collections.singletonList(ipsId));

        Paciente p = new Paciente();
        if ("contribuyente".equalsIgnoreCase(pacienteTipo)) {
            p.setContribuyenteId(pacienteId);
        } else {
            p.setBeneficiarioId(pacienteId);
        }
        o.setPaciente(p);

        Document pacienteDoc = new Document();
        pacienteDoc.put("_id", p.getId());
        pacienteDoc.put("tipo", p.getTipo());

        Document ordenDoc = new Document();
        mongoTemplate.getConverter().write(o, ordenDoc);
        ordenDoc.put("paciente", pacienteDoc);
        ordenDoc.put("_id", o.getIdOrden());

        mongoTemplate.insert(ordenDoc, "ORDENES_DE_SERVICIO");
        return "redirect:/ordenservicio";
    }

    @GetMapping("/{idOrden}/edit")
    public String formEdit(@PathVariable Integer idOrden, Model model) {
        OrdenDeServicio o = ordenServicio.darOrden(idOrden);
        if (o == null)
            return "redirect:/ordenservicio";
        if (o.getPaciente() == null)
            o.setPaciente(new Paciente());
        model.addAttribute("ordenservicio", o);
        return "ordenDeServicioEditar";
    }

    @PostMapping("/{idOrden}/edit/save")
    public String update(
            @ModelAttribute OrdenDeServicio o,
            @RequestParam("serviciosMedicosInput") String serviciosInput,
            @RequestParam("estado") String estado,
            @RequestParam("vigencia") String vigencia,
            @RequestParam("medicoAsociado") Integer medicoId,
            @RequestParam("pacienteId") Integer pacienteId,
            @RequestParam("pacienteTipo") String pacienteTipo,
            @RequestParam("IPSInput") Integer ipsId) {

        // Servicios médicos
        List<Integer> servs = Arrays.stream(serviciosInput.split(","))
                .map(String::trim)
                .map(Integer::valueOf)
                .toList();
        o.setServiciosMedicos(servs);

        o.setEstado(estado);
        o.setVigencia(vigencia);
        o.setMedicoAsociado(medicoId);
        o.setIPS(Collections.singletonList(ipsId));

        Paciente p = new Paciente();
        if ("contribuyente".equalsIgnoreCase(pacienteTipo)) {
            p.setContribuyenteId(pacienteId);
        } else {
            p.setBeneficiarioId(pacienteId);
        }
        o.setPaciente(p);

        Document pacienteDoc = new Document();
        pacienteDoc.put("_id", p.getId());
        pacienteDoc.put("tipo", p.getTipo());

        Document ordenDoc = new Document();
        mongoTemplate.getConverter().write(o, ordenDoc);
        ordenDoc.put("paciente", pacienteDoc);
        ordenDoc.put("_id", o.getIdOrden());

        mongoTemplate.save(ordenDoc, "ORDENES_DE_SERVICIO");
        return "redirect:/ordenservicio";
    }

    @GetMapping("/{idOrden}/delete")
    public String delete(@PathVariable Integer idOrden) {
        ordenServicio.eliminarOrden(idOrden);
        return "redirect:/ordenservicio";
    }
}
