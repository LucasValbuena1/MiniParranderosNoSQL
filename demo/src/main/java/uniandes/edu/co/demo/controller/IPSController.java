package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.IPS;
import uniandes.edu.co.demo.servicios.IPSServicio;

@Controller
public class IPSController {
    @Autowired
    private IPSServicio servicio;

    @GetMapping("/ips")
    public String show(Model m) {
        m.addAttribute("ips", servicio.darIPS());
        return "ips";
    }

    @GetMapping("/ips/new")
    public String formNew(Model m) {
        m.addAttribute("ips", new IPS());
        return "ipsNuevo";
    }

    @PostMapping("/ips/new/save")
    public String save(@ModelAttribute IPS i) {
        servicio.insertarIPS(i);
        return "redirect:/ips";
    }

    @GetMapping("/ips/{nit}/edit")
    public String formEdit(@PathVariable Integer nit, Model m) {
        IPS ps = servicio.darIPS(nit);
        if (ps != null) {
            m.addAttribute("ips", ps);
            return "ipsEditar";
        }
        return "redirect:/ips";
    }

    @PostMapping("/ips/{nit}/edit/save")
    public String update(@ModelAttribute IPS i) {
        servicio.actualizarIPS(i);
        return "redirect:/ips";
    }

    @GetMapping("/ips/{nit}/delete")
    public String del(@PathVariable Integer nit) {
        servicio.eliminarIPS(nit);
        return "redirect:/ips";
    }
}