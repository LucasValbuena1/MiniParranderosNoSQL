package uniandes.edu.co.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.ServicioDeSalud;
import uniandes.edu.co.demo.servicios.ServicioDeSaludServicio;

@Controller
@RequestMapping("/servicio")
public class ServicioDeSaludController {

    @Autowired
    private ServicioDeSaludServicio servicio;

    /** Listado de todos los servicios */
    @GetMapping
    public String show(Model m) {
        m.addAttribute("servicios", servicio.darServicios());
        return "serviciosDeSalud";
    }

    /** Formulario para crear uno nuevo */
    @GetMapping("/new")
    public String formNew(Model m) {
        m.addAttribute("servicio", new ServicioDeSalud());
        return "servicioDeSaludNuevo";
    }

    /** Procesar creación */
    @PostMapping("/new/save")
    public String saveNew(@ModelAttribute("servicio") ServicioDeSalud s) {
        servicio.insertarServicio(s);
        return "redirect:/servicio";
    }

    /** Formulario de edición */
    @GetMapping("/{idServicio}/edit")
    public String formEdit(@PathVariable Integer idServicio, Model m) {
        ServicioDeSalud s = servicio.darServicio(idServicio);
        if (s != null) {
            m.addAttribute("servicio", s);
            return "servicioDeSaludEditar";
        }
        return "redirect:/servicio";
    }

    /** Procesar edición */
    @PostMapping("/{idServicio}/edit/save")
    public String saveEdit(@ModelAttribute("servicio") ServicioDeSalud s) {
        servicio.actualizarServicio(s);
        return "redirect:/servicio";
    }

    /** Eliminar */
    @GetMapping("/{idServicio}/delete")
    public String delete(@PathVariable Integer idServicio) {
        servicio.eliminarServicio(idServicio);
        return "redirect:/servicio";
    }

    /** Consultar disponibilidad de un servicio */
    @GetMapping("/{idServicio}/disponibilidad")
    public String disponibilidad(@PathVariable Integer idServicio, Model m) {
        List<Object[]> filas = servicio.consultarDisponibilidad(idServicio);
        List<Map<String, Object>> resultados = filas.stream()
            .map(f -> {
                Map<String, Object> mp = new java.util.LinkedHashMap<>();
                mp.put("servicio",       f[0]);
                mp.put("disponibilidad", f[1]);
                mp.put("ips",            f[2]);
                mp.put("medico",         f[3]);
                return mp;
            })
            .collect(Collectors.toList());

        m.addAttribute("resultados", resultados);
        m.addAttribute("idServicio", idServicio);
        return "disponibilidadServicio";
    }

    /** Mostrar formulario Top 20 Servicios Más Solicitados */
    @GetMapping("/rfc2/form")
    public String mostrarFormTop20() {
        return "formularioRFC2";
    }

    /** Procesar consulta Top 20 Servicios Más Solicitados */
    @PostMapping("/rfc2/consultar")
    public String consultarTop20(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin")    String fechaFin,
            Model model) {

        // Llama al servicio que implementa la agregación en MongoDB
        List<Object[]> resultados = servicio.obtenerTop20Servicios(fechaInicio, fechaFin);
        model.addAttribute("resultados", resultados);
        return "resultadoRFC2";
    }
}
