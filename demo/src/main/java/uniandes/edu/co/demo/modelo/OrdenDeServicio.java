package uniandes.edu.co.demo.modelo;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ORDENES_DE_SERVICIO")
public class OrdenDeServicio {

    @Id
    private Integer idOrden; // Ahora el ID es forzado manualmente

    private List<Integer> serviciosMedicos;
    private String estado;
    private String vigencia;

    private Integer medicoAsociado;
    private Paciente paciente;
    private List<Integer> IPS;

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public List<Integer> getServiciosMedicos() {
        return serviciosMedicos;
    }

    public void setServiciosMedicos(List<Integer> serviciosMedicos) {
        this.serviciosMedicos = serviciosMedicos;
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

    public Integer getMedicoAsociado() {
        return medicoAsociado;
    }

    public void setMedicoAsociado(Integer medicoAsociado) {
        this.medicoAsociado = medicoAsociado;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Integer> getIPS() {
        return IPS;
    }

    public void setIPS(List<Integer> IPS) {
        this.IPS = IPS;
    }

    // Clase anidada embebida para el paciente
    public static class Paciente {
        private Integer contribuyenteId;
        private Integer beneficiarioId;

        public Integer getContribuyenteId() {
            return contribuyenteId;
        }

        public void setContribuyenteId(Integer contribuyenteId) {
            this.contribuyenteId = contribuyenteId;
        }

        public Integer getBeneficiarioId() {
            return beneficiarioId;
        }

        public void setBeneficiarioId(Integer beneficiarioId) {
            this.beneficiarioId = beneficiarioId;
        }

        // Métodos auxiliares para adaptarse al schema
        public Integer getId() {
            return contribuyenteId != null ? contribuyenteId : beneficiarioId;
        }

        public String getTipo() {
            return contribuyenteId != null ? "contribuyente" : "beneficiario";
        }
    }
}
