package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.MedicoEnIPS;
import uniandes.edu.co.demo.servicios.MedicoEnIPSServicio;

@Controller
public class MedicoEnIPSController {
    @Autowired
    private MedicoEnIPSServicio servicio;

    @GetMapping("/medicoEnIPS")
    public String show(Model m) {
        m.addAttribute("medicosEnIPS", servicio.darMedicosEnIPS());
        return "medicosEnIPS";
    }

    @GetMapping("/medicoEnIPS/new")
    public String formNew(Model m) {
        m.addAttribute("medicoEnIPS", new MedicoEnIPS());
        return "medicoEnIPSNuevo";
    }

    @PostMapping("/medicoEnIPS/new/save")
    public String save(@RequestParam Integer nitIPS, @RequestParam Integer numeroRegistroMedico) {
        servicio.insertarMedico(new MedicoEnIPS(nitIPS, numeroRegistroMedico));
        return "redirect:/medicoEnIPS";
    }

    @GetMapping("/medicoEnIPS/{id}/delete")
    public String del(@PathVariable String id) {
        servicio.eliminarMedico(id);
        return "redirect:/medicoEnIPS";
    }
}
