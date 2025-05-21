package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.OrdenDeServicio;
import uniandes.edu.co.demo.servicios.OrdenDeServicioServicio;

@Controller
public class OrdenDeServicioController {
    @Autowired
    private OrdenDeServicioServicio servicio;

    @GetMapping("/ordenservicio")
    public String show(Model m) {
        m.addAttribute("ordenes", servicio.darOrdenes());
        return "ordenesDeServicio";
    }

    @GetMapping("/ordenservicio/new")
    public String formNew(Model m) {
        m.addAttribute("ordenservicio", new OrdenDeServicio());
        return "ordenDeServicioNuevo";
    }

    @PostMapping("/ordenservicio/new/save")
    public String save(@ModelAttribute OrdenDeServicio o) {
        servicio.insertarOrden(o);
        return "redirect:/ordenservicio";
    }

    @GetMapping("/ordenservicio/{idOrden}/edit")
    public String formEdit(@PathVariable Integer idOrden, Model m) {
        OrdenDeServicio o = servicio.darOrden(idOrden);
        if (o != null) {
            m.addAttribute("ordenservicio", o);
            return "ordenDeServicioEditar";
        }
        return "redirect:/ordenservicio";
    }

    @PostMapping("/ordenservicio/{idOrden}/edit/save")
    public String update(@ModelAttribute OrdenDeServicio o) {
        servicio.actualizarOrden(o);
        return "redirect:/ordenservicio";
    }

    @GetMapping("/ordenservicio/{idOrden}/delete")
    public String del(@PathVariable Integer idOrden) {
        servicio.eliminarOrden(idOrden);
        return "redirect:/ordenservicio";
    }
}
