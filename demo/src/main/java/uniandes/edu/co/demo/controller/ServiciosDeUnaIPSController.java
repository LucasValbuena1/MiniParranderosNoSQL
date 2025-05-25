package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.ServiciosDeUnaIPS;
import uniandes.edu.co.demo.servicios.ServiciosDeUnaIPSServicio;

@Controller
public class ServiciosDeUnaIPSController {
    @Autowired
    private ServiciosDeUnaIPSServicio servicio;

    @GetMapping("/serviciosDeUnaIPS")
    public String show(Model m) {
        m.addAttribute("servicios", servicio.darServicios());
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

    @GetMapping("/serviciosDeUnaIPS/{id}/delete")
    public String del(@PathVariable String id) {
        servicio.eliminarServicio(id);
        return "redirect:/serviciosDeUnaIPS";
    }
}