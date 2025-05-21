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
public class ServicioDeSaludController {
    @Autowired
    private ServicioDeSaludServicio servicio;

    @GetMapping("/servicio")
    public String show(Model m) {
        m.addAttribute("servicios", servicio.darServicios());
        return "serviciosDeSalud";
    }

    @GetMapping("/servicio/new")
    public String formNew(Model m) {
        m.addAttribute("servicio", new ServicioDeSalud());
        return "servicioDeSaludNuevo";
    }

    @PostMapping("/servicio/new/save")
    public String save(@ModelAttribute ServicioDeSalud s) {
        servicio.insertarServicio(s);
        return "redirect:/servicio";
    }

    @GetMapping("/servicio/{idServicio}/edit")
    public String formEdit(@PathVariable Integer idServicio, Model m) {
        ServicioDeSalud sd = servicio.darServicio(idServicio);
        if (sd != null) {
            m.addAttribute("servicio", sd);
            return "servicioDeSaludEditar";
        }
        return "redirect:/servicio";
    }

    @PostMapping("/servicio/{idServicio}/edit/save")
    public String update(@ModelAttribute ServicioDeSalud s) {
        servicio.actualizarServicio(s);
        return "redirect:/servicio";
    }

    @GetMapping("/servicio/{idServicio}/delete")
    public String del(@PathVariable Integer idServicio) {
        servicio.eliminarServicio(idServicio);
        return "redirect:/servicio";
    }

    @GetMapping("/servicio/{idServicio}/disponibilidad")
    public String disp(@PathVariable Integer idServicio, Model m) {
        List<Object[]> filas = servicio.consultarDisponibilidad(idServicio);
        List<Map<String, Object>> res = filas.stream().map(f -> {
            Map<String, Object> mp = new java.util.LinkedHashMap<>();
            mp.put("servicio", f[0]);
            mp.put("disponibilidad", f[1]);
            mp.put("ips", f[2]);
            mp.put("medico", f[3]);
            return mp;
        }).collect(Collectors.toList());
        m.addAttribute("resultados", res);
        m.addAttribute("idServicio", idServicio);
        return "disponibilidadServicio";
    }
}