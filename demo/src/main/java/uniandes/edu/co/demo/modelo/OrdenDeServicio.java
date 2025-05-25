package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ORDENES_DE_SERVICIO")
public class OrdenDeServicio {
    @Id
    private Integer idOrden;
    private String medicoAsociado;
    private String servicioMedico;
    private String estado;
    private String vigencia;
    private String tipoDocContribuyente;
    private String numDocContribuyente;
    private String tipoDocBeneficiario;
    private String numDocBeneficiario;

    public OrdenDeServicio() {
    }

    public OrdenDeServicio(Integer idOrden, String medicoAsociado, String servicioMedico, String estado,
            String vigencia, String tipoDocContribuyente, String numDocContribuyente, String tipoDocBeneficiario,
            String numDocBeneficiario) {
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

    public String getMedicoAsociado() {
        return medicoAsociado;
    }

    public void setMedicoAsociado(String medicoAsociado) {
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

    public String getNumDocContribuyente() {
        return numDocContribuyente;
    }

    public void setNumDocContribuyente(String numDocContribuyente) {
        this.numDocContribuyente = numDocContribuyente;
    }

    public String getTipoDocBeneficiario() {
        return tipoDocBeneficiario;
    }

    public void setTipoDocBeneficiario(String tipoDocBeneficiario) {
        this.tipoDocBeneficiario = tipoDocBeneficiario;
    }

    public String getNumDocBeneficiario() {
        return numDocBeneficiario;
    }

    public void setNumDocBeneficiario(String numDocBeneficiario) {
        this.numDocBeneficiario = numDocBeneficiario;
    }
}