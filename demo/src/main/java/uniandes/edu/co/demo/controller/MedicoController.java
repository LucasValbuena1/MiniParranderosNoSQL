package uniandes.edu.co.demo.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Medico;
import uniandes.edu.co.demo.servicios.MedicoServicio;

@Controller
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoServicio servicio;

    @GetMapping
    public String show(Model model) {
        model.addAttribute("medicos", servicio.darMedicos());
        return "medicos";
    }

    @GetMapping("/new")
    public String formNew(Model model) {
        model.addAttribute("medico", new Medico());
        return "medicoNuevo";
    }

    @PostMapping("/new/save")
    public String save(
            @ModelAttribute Medico medico,
            @RequestParam("especialidadesInput") String especialidadesInput,
            @RequestParam("IPSAsociadaInput") Integer ipsId
    ) {
        // convierto la cadena "A, B, C" en lista
        List<String> listaEsp = Arrays.stream(especialidadesInput.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        medico.setEspecialidades(listaEsp);

        // si sólo es un ID de IPS
        medico.setIPSAsociada(Collections.singletonList(ipsId));

        // aquí no toco el ID: lo genera MedicoServicio
        servicio.insertarMedico(medico);

        return "redirect:/medico";
    }

    @GetMapping("/{numeroRegistroMedico}/edit")
    public String formEdit(@PathVariable Integer numeroRegistroMedico, Model model) {
        Medico m = servicio.darMedico(numeroRegistroMedico);
        if (m == null) {
            return "redirect:/medico";
        }
        model.addAttribute("medico", m);
        return "medicoEditar";
    }

    @PostMapping("/{numeroRegistroMedico}/edit/save")
    public String update(@ModelAttribute Medico medico) {
        servicio.actualizarMedico(medico);
        return "redirect:/medico";
    }

    @GetMapping("/{numeroRegistroMedico}/delete")
    public String delete(@PathVariable Integer numeroRegistroMedico) {
        servicio.eliminarMedico(numeroRegistroMedico);
        return "redirect:/medico";
    }
}
