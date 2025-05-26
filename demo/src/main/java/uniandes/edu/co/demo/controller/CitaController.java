package uniandes.edu.co.demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    private static final List<String> PACIENTE_TIPOS =
        List.of("CONTRIBUYENTE", "BENEFICIARIO");

    @Autowired
    private CitaServicio citaServicio;

    @Autowired
    private MedicoServicio medicoServicio;

    /**
     * Añade la lista de médicos a todos los modelos.
     */
    @ModelAttribute("medicos")
    public List<Medico> medicos() {
        return medicoServicio.darMedicos();
    }

    /**
     * Añade las opciones de tipo de paciente a todos los modelos.
     */
    @ModelAttribute("pacienteTipos")
    public List<String> pacienteTipos() {
        return PACIENTE_TIPOS;
    }

    /** Lista todas las citas */
    @GetMapping
    public String list(Model m) {
        m.addAttribute("citas", citaServicio.darCitas());
        return "citas";
    }

    /** Formulario para crear una nueva cita */
    @GetMapping("/new")
    public String formNew(Model m) {
        m.addAttribute("cita", new Cita());
        return "citaNueva";
    }

    /** Guarda la nueva cita (se asigna ID automáticamente en el servicio) */
    @PostMapping("/new/save")
    public String save(@ModelAttribute("cita") Cita cita) {
        citaServicio.insertarCita(cita);
        return "redirect:/cita";
    }

    /** Formulario para editar una cita existente */
    @GetMapping("/{id}/edit")
    public String formEdit(@PathVariable("id") Integer id, Model m) {
        Cita c = citaServicio.darCita(id);
        if (c == null) {
            return "redirect:/cita";
        }
        m.addAttribute("cita", c);
        return "citaEditar";
    }

    /** Guarda los cambios de la cita editada */
    @PostMapping("/{id}/edit/save")
    public String update(@ModelAttribute("cita") Cita cita) {
        citaServicio.actualizarCita(cita);
        return "redirect:/cita";
    }

    /** Elimina una cita */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        citaServicio.eliminarCita(id);
        return "redirect:/cita";
    }

    /** Formulario para consultar disponibilidad */
    @GetMapping("/availability")
    public String formAvail() {
        return "citaDisponibilidadForm";
    }

    /** Lista citas disponibles en el próximo mes */
    @GetMapping("/availability/list")
    public String avail(Model m) {
        m.addAttribute("citas", citaServicio.obtenerDisponibilidadCitas());
        return "citasDisponibles";
    }

    /**
     * Agenda una cita existente.
     * Recibe fechaHora en formato ISO (p.ej. "2025-06-01T14:30").
     */
    @PostMapping("/agendar")
    @ResponseBody
    public String agendar(
        @RequestParam Integer id,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Date fechaHora,
        @RequestParam String estado
    ) {
        citaServicio.agendarCitaSimple(id, fechaHora, estado);
        return "Cita agendada correctamente.";
    }
}
