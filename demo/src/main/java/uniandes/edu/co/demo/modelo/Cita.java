package uniandes.edu.co.demo.modelo;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "CITAS")
public class Cita {
    @Id
    private Integer idCita;
    private Integer idOrdenDeServicio;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date fechaHora;
    private String estado;
    private String tipoDocContribuyente;
    private Integer numDocContribuyente;
    private String tipoDocBeneficiario;
    private Integer numDocBeneficiario;

    public Cita() {
    }

    public Cita(Integer idCita, Date fechaHora, String estado, Integer idOrdenDeServicio, String tipoDocContribuyente,
            Integer numDocContribuyente, String tipoDocBeneficiario, Integer numDocBeneficiario) {
        this.idCita = idCita;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.idOrdenDeServicio = idOrdenDeServicio;
        this.tipoDocContribuyente = tipoDocContribuyente;
        this.numDocContribuyente = numDocContribuyente;
        this.tipoDocBeneficiario = tipoDocBeneficiario;
        this.numDocBeneficiario = numDocBeneficiario;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdOrdenDeServicio() {
        return idOrdenDeServicio;
    }

    public void setIdOrdenDeServicio(Integer idOrdenDeServicio) {
        this.idOrdenDeServicio = idOrdenDeServicio;
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