package uniandes.edu.co.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.Beneficiario;
import uniandes.edu.co.demo.servicios.BeneficiarioServicio;

@Controller
public class BeneficiarioController {
    @Autowired
    private BeneficiarioServicio servicio;

    @GetMapping("/beneficiario")
    public String mostrar(Model m) {
        m.addAttribute("beneficiarios", servicio.darBeneficiarios());
        return "beneficiarios";
    }

    @GetMapping("/beneficiario/new")
    public String formNew(Model m) {
        m.addAttribute("beneficiario", new Beneficiario());
        return "beneficiarioNuevo";
    }

    @PostMapping("/beneficiario/new/save")
    public String save(@ModelAttribute Beneficiario b) {
        servicio.insertarBeneficiario(b);
        return "redirect:/beneficiario";
    }

    @GetMapping("/beneficiario/{tipoDocumento}/{numeroDocumento}/edit")
    public String formEdit(@PathVariable String tipoDocumento, @PathVariable String numeroDocumento, Model m) {
        Beneficiario b = servicio.darBeneficiario(tipoDocumento, numeroDocumento);
        if (b != null) {
            m.addAttribute("beneficiario", b);
            return "beneficiarioEditar";
        }
        return "redirect:/beneficiario";
    }

    @PostMapping("/beneficiario/{tipoDocumento}/{numeroDocumento}/edit/save")
    public String update(@ModelAttribute Beneficiario b) {
        servicio.actualizarBeneficiario(b);
        return "redirect:/beneficiario";
    }

    @GetMapping("/beneficiario/{tipoDocumento}/{numeroDocumento}/delete")
    public String del(@PathVariable String tipoDocumento, @PathVariable String numeroDocumento) {
        servicio.eliminarBeneficiario(tipoDocumento, numeroDocumento);
        return "redirect:/beneficiario";
    }

    @GetMapping("/beneficiario/rfc4")
    @ResponseBody
    public List<Map<String, Object>> rfc4(@RequestParam String tipoDoc, @RequestParam String numDoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fIni,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fFin) {
        return servicio.rfc4(tipoDoc, numDoc, fIni, fFin);
    }

    @GetMapping("/beneficiario/rfc4/form")
    public String formRFC4() {
        return "formularioRFC4";
    }

    @PostMapping("/beneficiario/rfc4/consultar")
    public String procRFC4(@RequestParam String tipoDoc, @RequestParam String numDoc,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fIni,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fFin, Model m) {
        List<Map<String, Object>> res = servicio.rfc4(tipoDoc, numDoc, fIni, fFin);
        m.addAttribute("resultados", res);
        m.addAttribute("tipoDoc", tipoDoc);
        m.addAttribute("numDoc", numDoc);
        m.addAttribute("fIni", fIni);
        m.addAttribute("fFin", fFin);
        return "resultadoRFC4";
    }
}
