package uniandes.edu.co.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
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
    public String save(HttpServletRequest request) {
        Medico m = new Medico();

        try {
            m.setId(Integer.parseInt(request.getParameter("id")));
            m.setNombre(request.getParameter("nombre"));
            m.setTipoDeDocumento(request.getParameter("tipoDeDocumento"));
            m.setNumeroDeDocumento(request.getParameter("numeroDeDocumento"));
            m.setNumeroRegistroMedico(request.getParameter("numeroRegistroMedico"));

            // Especialidades
            String especialidadesStr = request.getParameter("especialidadesInput");
            List<String> especialidadesList = Arrays.stream(especialidadesStr.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
            m.setEspecialidades(especialidadesList);

            // IPSAsociada
            String ipsStr = request.getParameter("IPSAsociadaInput");
            List<Integer> ipsList = new ArrayList<>();
            if (ipsStr != null && !ipsStr.isEmpty()) {
                ipsList.add(Integer.parseInt(ipsStr));
            }
            m.setIPSAsociada(ipsList);

            // Validación mínima
            if (m.getId() == null || m.getNombre() == null || m.getTipoDeDocumento() == null ||
                    m.getNumeroDeDocumento() == null || m.getNumeroRegistroMedico() == null ||
                    m.getEspecialidades().isEmpty() || m.getIPSAsociada().isEmpty()) {
                return "redirect:/medico/new?error=faltan_campos";
            }

            servicio.insertarMedico(m);
            return "redirect:/medico";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/medico/new?error=error_interno";
        }
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