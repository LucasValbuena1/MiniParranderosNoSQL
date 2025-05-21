package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.Contribuyente;
import uniandes.edu.co.demo.modelo.ContribuyentePK;
import uniandes.edu.co.demo.servicios.ContribuyenteServicio;

@Controller
public class ContribuyenteController {
    @Autowired
    private ContribuyenteServicio servicio;

    @GetMapping("/contribuyente")
    public String show(Model m) {
        m.addAttribute("contribuyentes", servicio.darContribuyentes());
        return "contribuyentes";
    }

    @GetMapping("/contribuyente/new")
    public String formNew(Model m) {
        Contribuyente c = new Contribuyente();
        c.setPk(new ContribuyentePK());
        m.addAttribute("contribuyente", c);
        return "contribuyenteNuevo";
    }

    @PostMapping("/contribuyente/new/save")
    public String save(@ModelAttribute Contribuyente c) {
        servicio.insertarContribuyente(c);
        return "redirect:/contribuyente";
    }

    @GetMapping("/contribuyente/{tipoDocumento}/{numeroDocumento}/edit")
    public String formEdit(@PathVariable String tipoDocumento, @PathVariable String numeroDocumento, Model m) {
        Contribuyente c = servicio.darContribuyente(tipoDocumento, numeroDocumento);
        if (c != null) {
            m.addAttribute("contribuyente", c);
            return "contribuyenteEditar";
        }
        return "redirect:/contribuyente";
    }

    @PostMapping("/contribuyente/{tipoDocumento}/{numeroDocumento}/edit/save")
    public String update(@ModelAttribute Contribuyente c) {
        servicio.actualizarContribuyente(c);
        return "redirect:/contribuyente";
    }

    @GetMapping("/contribuyente/{tipoDocumento}/{numeroDocumento}/delete")
    public String del(@PathVariable String tipoDocumento, @PathVariable String numeroDocumento) {
        servicio.eliminarContribuyente(tipoDocumento, numeroDocumento);
        return "redirect:/contribuyente";
    }
}
