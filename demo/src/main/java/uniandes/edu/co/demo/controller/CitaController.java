package uniandes.edu.co.demo.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.Cita;
import uniandes.edu.co.demo.servicios.CitaServicio;

@Controller
public class CitaController {
    @Autowired
    private CitaServicio servicio;

    @GetMapping("/cita")
    public String show(Model m) {
        m.addAttribute("citas", servicio.darCitas());
        return "citas";
    }

    @GetMapping("/cita/new")
    public String formNew(Model m) {
        m.addAttribute("cita", new Cita());
        return "citaNueva";
    }

    @PostMapping("/cita/new/save")
    public String save(@ModelAttribute Cita c) {
        servicio.insertarCita(c);
        return "redirect:/cita";
    }

    @GetMapping("/cita/{idCita}/edit")
    public String formEdit(@PathVariable Integer idCita, Model m) {
        Cita c = servicio.darCita(idCita);
        if (c != null) {
            m.addAttribute("cita", c);
            return "citaEditar";
        }
        return "redirect:/cita";
    }

    @PostMapping("/cita/{idCita}/edit/save")
    public String update(@ModelAttribute Cita c) {
        servicio.actualizarCita(c);
        return "redirect:/cita";
    }

    @GetMapping("/cita/{idCita}/delete")
    public String del(@PathVariable Integer idCita) {
        servicio.eliminarCita(idCita);
        return "redirect:/cita";
    }

    @GetMapping("/cita/availability")
    public String formAvail() {
        return "citaDisponibilidadForm";
    }

    @GetMapping("/cita/availability/list")
    public String avail(Model m) {
        m.addAttribute("citas", servicio.obtenerDisponibilidadCitas());
        return "citasDisponibles";
    }

    @PostMapping("/cita/agendar")
    @ResponseBody
    public String agendar(@RequestParam Integer idCita,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fechaHora,
            @RequestParam String estado) {
        servicio.agendarCitaSimple(idCita, fechaHora, estado);
        return "Cita agendada correctamente.";
    }
}