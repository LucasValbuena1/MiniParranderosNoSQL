package uniandes.edu.co.demo.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    /** Lista todas las órdenes */
    @GetMapping
    public String show(Model model) {
        model.addAttribute("ordenes", ordenServicio.darOrdenes());
        return "ordenesDeServicio";
    }

    /** Formulario de creación */
    @GetMapping("/new")
    public String formNew(Model model) {
        OrdenDeServicio o = new OrdenDeServicio();
        // Inicializar paciente para evitar null en la plantilla
        o.setPaciente(new Paciente());
        model.addAttribute("ordenservicio", o);
        return "ordenDeServicioNuevo";
    }

    /** Procesa creación incluyendo el embebido Paciente */
    @PostMapping("/new/save")
    public String save(
        @ModelAttribute OrdenDeServicio o,
        @RequestParam("serviciosMedicosInput") String serviciosInput,
        @RequestParam("estado")              String estado,
        @RequestParam("vigencia")            String vigencia,
        @RequestParam("medicoAsociado")      Integer medicoId,
        @RequestParam("pacienteId")          Integer pacienteId,
        @RequestParam("pacienteTipo")        String pacienteTipo,
        @RequestParam("IPSInput")            Integer ipsId
    ) {
        // 1. Servicios médicos
        List<Integer> servs = Arrays.stream(serviciosInput.split(","))
                                    .map(String::trim)
                                    .map(Integer::valueOf)
                                    .toList();
        o.setServiciosMedicos(servs);

        // 2. Atributos simples
        o.setEstado(estado);
        o.setVigencia(vigencia);
        o.setMedicoAsociado(medicoId);

        // 3. Paciente embebido
        Paciente p = new Paciente();
        if ("contribuyente".equalsIgnoreCase(pacienteTipo)) {
            p.setContribuyenteId(pacienteId);
        } else {
            p.setBeneficiarioId(pacienteId);
        }
        o.setPaciente(p);

        // 4. IPS asociadas
        o.setIPS(Collections.singletonList(ipsId));

        ordenServicio.insertarOrden(o);
        return "redirect:/ordenservicio";
    }

    /** Formulario de edición */
    @GetMapping("/{idOrden}/edit")
    public String formEdit(@PathVariable Integer idOrden, Model model) {
        OrdenDeServicio o = ordenServicio.darOrden(idOrden);
        if (o == null) {
            return "redirect:/ordenservicio";
        }
        // Asegurar que paciente no sea null
        if (o.getPaciente() == null) {
            o.setPaciente(new Paciente());
        }
        model.addAttribute("ordenservicio", o);
        return "ordenDeServicioEditar";
    }

    /** Procesa edición */
    @PostMapping("/{idOrden}/edit/save")
    public String update(
        @ModelAttribute OrdenDeServicio o,
        @RequestParam("serviciosMedicosInput") String serviciosInput,
        @RequestParam("estado")              String estado,
        @RequestParam("vigencia")            String vigencia,
        @RequestParam("medicoAsociado")      Integer medicoId,
        @RequestParam("pacienteId")          Integer pacienteId,
        @RequestParam("pacienteTipo")        String pacienteTipo,
        @RequestParam("IPSInput")            Integer ipsId
    ) {
        // Servicios médicos
        List<Integer> servs = Arrays.stream(serviciosInput.split(","))
                                    .map(String::trim)
                                    .map(Integer::valueOf)
                                    .toList();
        o.setServiciosMedicos(servs);

        // Atributos simples
        o.setEstado(estado);
        o.setVigencia(vigencia);
        o.setMedicoAsociado(medicoId);

        // Paciente embebido
        Paciente p = new Paciente();
        if ("contribuyente".equalsIgnoreCase(pacienteTipo)) {
            p.setContribuyenteId(pacienteId);
        } else {
            p.setBeneficiarioId(pacienteId);
        }
        o.setPaciente(p);

        // IPS asociadas
        o.setIPS(Collections.singletonList(ipsId));

        ordenServicio.actualizarOrden(o);
        return "redirect:/ordenservicio";
    }

    /** Elimina una orden */
    @GetMapping("/{idOrden}/delete")
    public String delete(@PathVariable Integer idOrden) {
        ordenServicio.eliminarOrden(idOrden);
        return "redirect:/ordenservicio";
    }
}
