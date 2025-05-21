package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ordenesDeServicio")
public class OrdenDeServicio {
    @Id
    private Integer idOrden;
    private Integer medicoAsociado;
    private String servicioMedico;
    private String estado;
    private String vigencia;
    private String tipoDocContribuyente;
    private Integer numDocContribuyente;
    private String tipoDocBeneficiario;
    private Integer numDocBeneficiario;

    public OrdenDeServicio() {
    }

    public OrdenDeServicio(Integer idOrden, Integer medicoAsociado, String servicioMedico, String estado,
            String vigencia, String tipoDocContribuyente, Integer numDocContribuyente, String tipoDocBeneficiario,
            Integer numDocBeneficiario) {
        this.idOrden = idOrden;
        this.medicoAsociado = medicoAsociado;
        this.servicioMedico = servicioMedico;
        this.estado = estado;
        this.vigencia = vigencia;
        this.tipoDocContribuyente = tipoDocContribuyente;
        this.numDocContribuyente = numDocContribuyente;
        this.tipoDocBeneficiario = tipoDocBeneficiario;
        this.numDocBeneficiario = numDocBeneficiario;
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Integer getMedicoAsociado() {
        return medicoAsociado;
    }

    public void setMedicoAsociado(Integer medicoAsociado) {
        this.medicoAsociado = medicoAsociado;
    }

    public String getServicioMedico() {
        return servicioMedico;
    }

    public void setServicioMedico(String servicioMedico) {
        this.servicioMedico = servicioMedico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getTipoDocContribuyente() {
        return tipoDocContribuyente;
    }

    public void setTipoDocContribuyente(String tipoDocContribuyente) {
        this.tipoDocContribuyente = tipoDocContribuyente;
    }

    public Integer getNumDocContribuyente() {
        return numDocContribuyente;
    }

    public void setNumDocContribuyente(Integer numDocContribuyente) {
        this.numDocContribuyente = numDocContribuyente;
    }

    public String getTipoDocBeneficiario() {
        return tipoDocBeneficiario;
    }

    public void setTipoDocBeneficiario(String tipoDocBeneficiario) {
        this.tipoDocBeneficiario = tipoDocBeneficiario;
    }

    public Integer getNumDocBeneficiario() {
        return numDocBeneficiario;
    }

    public void setNumDocBeneficiario(Integer numDocBeneficiario) {
        this.numDocBeneficiario = numDocBeneficiario;
    }
}