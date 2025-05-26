package uniandes.edu.co.demo.controller;

import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Cita;
import uniandes.edu.co.demo.modelo.Medico;
import uniandes.edu.co.demo.servicios.CitaServicio;
import uniandes.edu.co.demo.servicios.MedicoServicio;

@Controller
@RequestMapping("/cita")
public class CitaController {

    private static final List<String> PACIENTE_TIPOS = List.of("CONTRIBUYENTE", "BENEFICIARIO");

    @Autowired
    private CitaServicio citaServicio;

    @Autowired
    private MedicoServicio medicoServicio;

    @Autowired
    private MongoTemplate mongoTemplate;

    @ModelAttribute("medicos")
    public List<Medico> medicos() {
        return medicoServicio.darMedicos();
    }

    @ModelAttribute("pacienteTipos")
    public List<String> pacienteTipos() {
        return PACIENTE_TIPOS;
    }

    @GetMapping
    public String list(Model m) {
        m.addAttribute("citas", citaServicio.darCitas());
        return "citas";
    }

    @GetMapping("/new")
    public String formNew(Model m) {
        m.addAttribute("cita", new Cita());
        return "citaNueva";
    }

    @PostMapping("/new/save")
    public String save(@ModelAttribute("cita") Cita cita, Model model) {
        if (cita.getMedico() == null || cita.getMedico().getId() == null) {
            model.addAttribute("error", "Debes seleccionar un médico.");
            model.addAttribute("cita", cita);
            return "citaNueva";
        }

        Medico m = medicoServicio.darMedico(cita.getMedico().getId());
        if (m == null) {
            model.addAttribute("error", "El médico seleccionado no existe.");
            model.addAttribute("cita", cita);
            return "citaNueva";
        }

        cita.setId((int) (Math.random() * 100000));

        Document pacienteDoc = new Document();
        pacienteDoc.put("_id", cita.getPacienteId());
        pacienteDoc.put("tipo", cita.getPacienteTipo().toLowerCase());

        Document medicoDoc = new Document();
        medicoDoc.put("_id", m.getId());
        medicoDoc.put("nombre", m.getNombre());
        medicoDoc.put("numeroRegistroMedico", m.getNumeroRegistroMedico());

        Document citaDoc = new Document();
        citaDoc.put("_id", cita.getId());
        citaDoc.put("fechaHora", cita.getFechaHora());
        citaDoc.put("estado", cita.getEstado());
        citaDoc.put("ordenDeServicio", cita.getOrdenDeServicioId());
        citaDoc.put("medicoAsociado", String.valueOf(m.getId())); // <- CORREGIDO
        citaDoc.put("paciente", pacienteDoc);
        citaDoc.put("medico", medicoDoc);

        mongoTemplate.insert(citaDoc, "CITAS");

        return "redirect:/cita";
    }

    @GetMapping("/{id}/edit")
    public String formEdit(@PathVariable("id") Integer id, Model m) {
        Cita c = citaServicio.darCita(id);
        if (c == null) {
            return "redirect:/cita";
        }
        m.addAttribute("cita", c);
        return "citaEditar";
    }

    @PostMapping("/{id}/edit/save")
    public String update(@ModelAttribute("cita") Cita cita) {
        citaServicio.actualizarCita(cita);
        return "redirect:/cita";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        citaServicio.eliminarCita(id);
        return "redirect:/cita";
    }

    @GetMapping("/availability")
    public String formAvail() {
        return "citaDisponibilidadForm";
    }

    @GetMapping("/availability/list")
    public String avail(Model m) {
        m.addAttribute("citas", citaServicio.obtenerDisponibilidadCitas());
        return "citasDisponibles";
    }

    @PostMapping("/agendar")
    @ResponseBody
    public String agendar(
            @RequestParam Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fechaHora,
            @RequestParam String estado) {
        citaServicio.agendarCitaSimple(id, fechaHora, estado);
        return "Cita agendada correctamente.";
    }
}
