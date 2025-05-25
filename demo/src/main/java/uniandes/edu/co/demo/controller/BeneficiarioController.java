package uniandes.edu.co.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.Beneficiario;
import uniandes.edu.co.demo.modelo.Contribuyente;
import uniandes.edu.co.demo.servicios.BeneficiarioServicio;
import uniandes.edu.co.demo.servicios.ContribuyenteServicio;

@Controller
public class BeneficiarioController {
    @Autowired
    private BeneficiarioServicio servicio;
    @Autowired
    private ContribuyenteServicio contribuyenteServicio;

    @GetMapping("/beneficiario")
    public String mostrar(Model m) {
        List<Contribuyente> contribuyentes = contribuyenteServicio.darContribuyentes();
        List<Beneficiario> todos = new ArrayList<>();
        for (Contribuyente c : contribuyentes) {
            if (c.getBeneficiarios() != null) {
                todos.addAll(c.getBeneficiarios());
            }
        }
        // Debug: Mostrar en consola los beneficiarios
        for (Beneficiario b : todos) {
            System.out.println("Beneficiario: " + b.getNombre() + " | Tipo: " + b.getTipoDocumento() + " | Num: " + b.getNumeroDocumento());
        }
        m.addAttribute("beneficiarios", todos);
        return "beneficiarios";
    }

    @GetMapping("/beneficiario/new")
    public String formNew(Model m) {
        m.addAttribute("beneficiario", new Beneficiario());
        m.addAttribute("contribuyentes", contribuyenteServicio.darContribuyentes());
        return "beneficiarioNuevo";
    }

    @PostMapping("/beneficiario/new/save")
    public String save(@ModelAttribute Beneficiario b, @RequestParam String contribuyenteId) {
        String[] parts = contribuyenteId.split("-");
        String tipoDocumento = parts[0];
        String numeroDocumento = parts[1];

        Contribuyente c = contribuyenteServicio.darContribuyente(tipoDocumento, numeroDocumento);
        if (c.getBeneficiarios() == null)
            c.setBeneficiarios(new ArrayList<>());

        // Asignar ID incremental automáticamente
        int maxId = 0;
        for (Beneficiario ben : c.getBeneficiarios()) {
            try {
                int val = Integer.parseInt(ben.getId());
                if (val > maxId)
                    maxId = val;
            } catch (Exception ex) {
                // Si algún ID no es numérico, lo ignora
            }
        }
        b.setId(String.valueOf(maxId + 1));

        c.getBeneficiarios().add(b);
        contribuyenteServicio.actualizarContribuyente(c);

        return "redirect:/beneficiario";
    }

    @GetMapping("/beneficiario/{tipoDocumento}/{numeroDocumento}/edit")
    public String formEdit(@PathVariable String tipoDocumento, @PathVariable String numeroDocumento, Model m) {
        // Busca el beneficiario dentro de los contribuyentes
        Beneficiario b = null;
        Contribuyente contribuyenteEncontrado = null;
        List<Contribuyente> contribuyentes = contribuyenteServicio.darContribuyentes();
        for (Contribuyente c : contribuyentes) {
            if (c.getBeneficiarios() != null) {
                for (Beneficiario ben : c.getBeneficiarios()) {
                    if (ben.getTipoDocumento().equals(tipoDocumento)
                            && ben.getNumeroDocumento().equals(numeroDocumento)) {
                        b = ben;
                        contribuyenteEncontrado = c;
                        break;
                    }
                }
            }
            if (b != null)
                break;
        }
        if (b != null) {
            m.addAttribute("beneficiario", b);
            m.addAttribute("contribuyentes", contribuyenteServicio.darContribuyentes());
            m.addAttribute("contribuyenteActualId", contribuyenteEncontrado.getTipoDeDocumento() + "-"
                    + contribuyenteEncontrado.getNumeroDeDocumento());
            return "beneficiarioEditar";
        }
        return "redirect:/beneficiario";
    }

    @PostMapping("/beneficiario/{tipoDocumento}/{numeroDocumento}/edit/save")
    public String update(@ModelAttribute Beneficiario b, @RequestParam String contribuyenteId) {
        String[] parts = contribuyenteId.split("-");
        String tipoDocContribuyente = parts[0];
        String numDocContribuyente = parts[1];

        Contribuyente c = contribuyenteServicio.darContribuyente(tipoDocContribuyente, numDocContribuyente);
        if (c.getBeneficiarios() != null) {
            for (int i = 0; i < c.getBeneficiarios().size(); i++) {
                Beneficiario actual = c.getBeneficiarios().get(i);
                if (actual.getTipoDocumento().equals(b.getTipoDocumento())
                        && actual.getNumeroDocumento().equals(b.getNumeroDocumento())) {
                    c.getBeneficiarios().set(i, b);
                    break;
                }
            }
            contribuyenteServicio.actualizarContribuyente(c);
        }
        return "redirect:/beneficiario";
    }

    @GetMapping("/beneficiario/{tipoDocumento}/{numeroDocumento}/delete")
    public String del(@PathVariable String tipoDocumento, @PathVariable String numeroDocumento) {
        // Busca y elimina el beneficiario dentro del contribuyente
        List<Contribuyente> contribuyentes = contribuyenteServicio.darContribuyentes();
        for (Contribuyente c : contribuyentes) {
            if (c.getBeneficiarios() != null) {
                c.getBeneficiarios().removeIf(
                        b -> b.getTipoDocumento().equals(tipoDocumento)
                                && b.getNumeroDocumento().equals(numeroDocumento));
                contribuyenteServicio.actualizarContribuyente(c);
            }
        }
        return "redirect:/beneficiario";
    }

    // RFC4 y formularios relacionados (sin cambios)
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
