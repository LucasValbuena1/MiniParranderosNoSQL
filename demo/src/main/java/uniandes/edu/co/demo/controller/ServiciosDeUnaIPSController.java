package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.ServiciosDeUnaIPS;
import uniandes.edu.co.demo.modelo.IPS;
import uniandes.edu.co.demo.servicios.ServiciosDeUnaIPSServicio;
import uniandes.edu.co.demo.servicios.IPSServicio;

import java.util.*;

@Controller
public class ServiciosDeUnaIPSController {
    @Autowired
    private ServiciosDeUnaIPSServicio servicio;

    @Autowired
    private IPSServicio ipsServicio;

    @GetMapping("/serviciosDeUnaIPS")
    public String show(Model m) {
        // 1. Trae los de la colección intermedia
        List<ServiciosDeUnaIPS> intermedios = servicio.darServicios();

        // 2. Trae todas las IPS y recorre sus servicios embebidos
        List<IPS> ipsList = ipsServicio.darIPS();
        Set<String> yaMostrados = new HashSet<>();
        List<ServiciosDeUnaIPS> total = new ArrayList<>();

        // Agrega los intermedios (colección)
        for (ServiciosDeUnaIPS s : intermedios) {
            total.add(s);
            yaMostrados.add(s.getNitIPS() + "-" + s.getIdServicio());
        }

        // Agrega los embebidos que NO estén ya en la colección intermedia
        for (IPS ips : ipsList) {
            if (ips.getServicios() != null) {
                for (Object idServicioObj : ips.getServicios()) {
                    String idServicio = String.valueOf(idServicioObj);
                    String key = ips.getNit() + "-" + idServicio;
                    if (!yaMostrados.contains(key)) {
                        total.add(new ServiciosDeUnaIPS(ips.getNit(), idServicio));
                        yaMostrados.add(key);
                    }
                }
            }
        }

        m.addAttribute("servicios", total);
        return "serviciosDeUnaIPS";
    }

    @GetMapping("/serviciosDeUnaIPS/new")
    public String formNew(Model m) {
        m.addAttribute("servicio", new ServiciosDeUnaIPS());
        return "serviciosDeUnaIPSNuevo";
    }

    @PostMapping("/serviciosDeUnaIPS/new/save")
    public String save(@RequestParam String nitIPS, @RequestParam String idServicio) {
        servicio.insertarServicio(new ServiciosDeUnaIPS(nitIPS, idServicio));
        return "redirect:/serviciosDeUnaIPS";
    }

    @GetMapping("/serviciosDeUnaIPS/{nitIPS}/{idServicio}/delete")
    public String del(@PathVariable String nitIPS, @PathVariable String idServicio) {
        servicio.eliminarPorNitYServicio(nitIPS, idServicio);
        return "redirect:/serviciosDeUnaIPS";
    }
}
