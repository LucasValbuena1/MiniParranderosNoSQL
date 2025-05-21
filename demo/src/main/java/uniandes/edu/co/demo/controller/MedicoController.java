package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.Medico;
import uniandes.edu.co.demo.servicios.MedicoServicio;

@Controller
public class MedicoController {
    @Autowired
    private MedicoServicio servicio;

    @GetMapping("/medico")
    public String show(Model m) {
        m.addAttribute("medicos", servicio.darMedicos());
        return "medicos";
    }

    @GetMapping("/medico/new")
    public String formNew(Model m) {
        m.addAttribute("medico", new Medico());
        return "medicoNuevo";
    }

    @PostMapping("/medico/new/save")
    public String save(@ModelAttribute Medico m) {
        servicio.insertarMedico(m);
        return "redirect:/medico";
    }

    @GetMapping("/medico/{numeroRegistroMedico}/edit")
    public String formEdit(@PathVariable Integer numeroRegistroMedico, Model m) {
        Medico dc = servicio.darMedico(numeroRegistroMedico);
        if (dc != null) {
            m.addAttribute("medico", dc);
            return "medicoEditar";
        }
        return "redirect:/medico";
    }

    @PostMapping("/medico/{numeroRegistroMedico}/edit/save")
    public String update(@ModelAttribute Medico m) {
        servicio.actualizarMedico(m);
        return "redirect:/medico";
    }

    @GetMapping("/medico/{numeroRegistroMedico}/delete")
    public String del(@PathVariable Integer numeroRegistroMedico) {
        servicio.eliminarMedico(numeroRegistroMedico);
        return "redirect:/medico";
    }
}