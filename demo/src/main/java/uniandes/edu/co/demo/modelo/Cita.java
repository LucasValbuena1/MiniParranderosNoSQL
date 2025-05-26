package uniandes.edu.co.demo.modelo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "CITAS")
public class Cita {

    @Id
    private Integer id;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Date fechaHora;

    private String estado;

    // FK a OrdenDeServicio
    private Integer ordenDeServicioId;

    /**
     * El “paciente” puede ser un contribuyente o un beneficiario.
     * PacienteTipo = "CONTRIBUYENTE" o "BENEFICIARIO"
     * pacienteId = su _id respectivo.
     */
    private String pacienteTipo;
    private Integer pacienteId;

    /**
     * Asociación al médico que atiende la cita.
     */
    private Medico medico;

    public Cita() { }

    public Cita(Integer id,
                Date fechaHora,
                String estado,
                Integer ordenDeServicioId,
                String pacienteTipo,
                Integer pacienteId,
                Medico medico) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.ordenDeServicioId = ordenDeServicioId;
        this.pacienteTipo = pacienteTipo;
        this.pacienteId = pacienteId;
        this.medico = medico;
    }

    // ————————————————————————————
    // Getters & Setters
    // ————————————————————————————

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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

    public Integer getOrdenDeServicioId() {
        return ordenDeServicioId;
    }
    public void setOrdenDeServicioId(Integer ordenDeServicioId) {
        this.ordenDeServicioId = ordenDeServicioId;
    }

    public String getPacienteTipo() {
        return pacienteTipo;
    }
    public void setPacienteTipo(String pacienteTipo) {
        this.pacienteTipo = pacienteTipo;
    }

    public Integer getPacienteId() {
        return pacienteId;
    }
    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Medico getMedico() {
        return medico;
    }
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}
